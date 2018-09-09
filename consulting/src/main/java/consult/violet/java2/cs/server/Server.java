package consult.violet.java2.cs.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import consult.violet.java2.cs.AbstractCommand;
import consult.violet.java2.cs.NAKCommand;
import consult.violet.java2.cs.Receiver;
import consult.violet.java2.cs.ShutdownCommand;


/**
 * The server will (in a single thread):
 * Wait for a client to connect on port 4885;
 * Wait for the client to send a command;
 * Set a receiver on the command as described by the Command Pattern;
 * Execute the command as described by the Command Pattern;
 * Return the command object to the client; and
 * Loop (wait for the next client to connect).
 * @author Monika
 *
 */
public class Server implements Serializable
{
    private final InternalReceiver receiver;
    private int port;
    private static final Logger log  = 
            (Logger) LoggerFactory.getLogger(Server.class.getName());
    private volatile boolean isActive;

    /**
     * Instantiates a new class
     * Establishes the port to use to listen for connections.
     * @param port port number for the server.
     */
    public Server( int port )
    {
        this.port = port;
        this.isActive = true;
        this.receiver = null;
    }


    /**
     * returns isActive status
     * @return isActive status
     */
    public boolean isActive()
    {
        return isActive;
    }

    /**
     * Sets isActive status
     * @param isActive sets isActive status
     */
    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    /**
     * execute method to create server socket
     * @throws IOException if could not create server socket
     * @throws ClassNotFoundException if could not create server socket
     */
    public void execute()
            throws IOException, ClassNotFoundException
    {
        log.info( "opening server socket on " + port );
        try ( ServerSocket listener = new ServerSocket( port ) )
        {
            execute( listener );
        }
    }
    
   private void execute( ServerSocket listener )
            throws IOException, ClassNotFoundException
    {
        listener.setSoTimeout(2000);
        while ( isActive() )
        {
            log.info( "Listening for clients" );
            try
            {
                Socket  client  = listener.accept();
                log.info( "connection accepted" );
                ClientProcessor cProc   = new ClientProcessor( client );
                Thread t = new Thread( cProc );
                t.start();
            }
            catch(IOException e)
            {
                System.out.println("exception "+e);
            }
        }
    }
    
    private class ClientProcessor implements Runnable
    {
        private final Socket    client;

        public ClientProcessor( Socket socket )
        {
            this.client = socket;
        }

        public void run()
        {
            try ( Socket temp = client )
            {
                process();
            }
            catch ( Exception exc )
            {
                log.error( "premature client exit", exc );
            }
        }

        private void process()
                throws IOException, ClassNotFoundException
        {

            InputStream         iStream     = client.getInputStream();
            ObjectInputStream   reader   = new ObjectInputStream( iStream );
            OutputStream        oStream     = client.getOutputStream();
            ObjectOutputStream  writer      = new ObjectOutputStream( oStream );
            log.info( "reading command" );
            InternalReceiver    receiver    =       new InternalReceiver( );
            Object obj     = reader.readObject();
            if ( !(obj instanceof AbstractCommand) )
            {
                log.error( "Unrecognized command", obj );
                writer.writeObject( new NAKCommand() );
            }
            else
            {
                AbstractCommand     command     = 
                        (AbstractCommand)obj;
                command.setReceiver( receiver );
                command.execute();
                log.info( command.toString() );
                pause(command.getWorkMillisMin(),command.getWorkMillisMax());
                //command.setReceiver( receiver );
                writer.writeObject( command );
            }
        }
        
        private void pause(long min, long max)
        {
            long time = ThreadLocalRandom.current().nextLong(min,max+1);
            System.out.println("going to pause "+time+" :"+min+" : "+max); 
            try
            {
                Thread.sleep(time);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
                 
        }
    }

    private class InternalReceiver extends Receiver implements Serializable
    {
        /**
         * default serial version id
         */
        private static final long serialVersionUID = 1L;


        /**
         * Instantiates a new class
         */
        public InternalReceiver()
        {
            super();
        }


        /* (non-Javadoc)
         * @see edu.uweo.java2.assignment9.Receiver#action(edu.uweo.java2.assignment9.ShutdownCommand)
         */
        public void action( ShutdownCommand cmd )
        {
            isActive = false;
            log.info( "shutdown command detected" );
        }
    }
    
   
    /**
     * Main method 
     * @param args Main method args
     */
    public static void main(String[] args)
    {
        
        Server server = new Server(4885);
        try
        {
            server.execute();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
