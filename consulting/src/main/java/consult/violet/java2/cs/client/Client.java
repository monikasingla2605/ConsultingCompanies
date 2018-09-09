package consult.violet.java2.cs.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import consult.violet.java2.cs.AbstractCommand;
import consult.violet.java2.cs.NAKCommand;
import consult.violet.java2.cs.server.Server;



/**
 * The client will send commands to the server. 
 * All commands will be implemented using the command pattern.
 * @author Monika
 *
 */
public class Client
{
    private int port;
    private static final Logger log  = 
            (Logger) LoggerFactory.getLogger(Client.class.getName());

    /**
     * Instantiates a new class.
     * Determines the port on which to connect to the server.
     * @param port port number to listen to.
     */
    public Client(int port)
    {
        this.port = port;
    }

    /**
     * This method forwards a given command to the server for execution. It will:
     * Connect to the server on the port established by the constructor;
     * Send a single command object to the server implemented as required by the Command Pattern;
     * Wait for the server's response;
     * Log the server's response; and
     * Disconnect from the server
     * @param command command to be executed
     * @throws IOException if could not create connection
     * @throws ClassNotFoundException if could not create connection
     */
    public void execute( AbstractCommand command )
            throws IOException, ClassNotFoundException
        {
            InetAddress addr    = InetAddress.getLoopbackAddress();
            log.info( "connecting to server on " + port );
            try ( Socket socket = new Socket( addr, port ) )
            {
                execute( socket , command);
            }
        }
        
        private void execute( Socket socket, AbstractCommand command )
            throws IOException, ClassNotFoundException
        {
            OutputStream        oStream     = socket.getOutputStream();
            ObjectOutputStream  writer      = new ObjectOutputStream( oStream );
            log.info( "Sending command", command );
            writer.writeObject( command );
            log.info( "Waiting for response to " + command );
            InputStream         iStream     = socket.getInputStream();
            ObjectInputStream   objStream   = new ObjectInputStream( iStream );
            Object  response    = objStream.readObject();
            if ( response instanceof NAKCommand )
                log.error( "invalid response from server: " + response );
            else
            {
                BigDecimal  result  = ((AbstractCommand)response).getResult();
                command.setResult( result );
                log.info( "Response received: " + response );
            }
           // socket.close();
        }
}
