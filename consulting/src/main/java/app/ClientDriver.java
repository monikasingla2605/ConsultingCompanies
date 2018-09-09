package app;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;

import javax.swing.SwingUtilities;

import consult.violet.java2.cs.AbstractCommand;
import consult.violet.java2.cs.AddCommand;
import consult.violet.java2.cs.DivCommand;
import consult.violet.java2.cs.MulCommand;
import consult.violet.java2.cs.Receiver;
import consult.violet.java2.cs.ShutdownCommand;
import consult.violet.java2.cs.SubCommand;
import consult.violet.java2.cs.client.Client;

/**
 * This application will use the Client class to send four commands to the server
 * @author Monika
 *
 */
public class ClientDriver
{
    private final static int port = 4885;

   
    /**
     * main method
     * @param args main method arguments
     */
    public static void main(String args[])
    {
        ClientDriver cd = new ClientDriver();
        cd.threadClient();
    }

    private void threadClient()
    {
        BigDecimal op1 = new BigDecimal("0.5");
        BigDecimal op2 = new BigDecimal("0.7");
        long min = 200;
        long max = 500;
        AbstractCommand addCommand = new AddCommand(op1,op2, min,max);
        AbstractCommand subCommand = new SubCommand(op1,op2, min,max);
        AbstractCommand mulCommand = new MulCommand(op1,op2, min,max);
        AbstractCommand divCommand = new DivCommand(op1,op2, min,max);

        ClientThread ac = new ClientThread(addCommand);
        ClientThread sc = new ClientThread(subCommand);
        ClientThread mc = new ClientThread(mulCommand);
        ClientThread dc = new ClientThread(divCommand);

        Thread  addThread  = new Thread( ac );
        Thread  subThread  = new Thread( sc );
        Thread  mulThread  = new Thread( mc );
        Thread  divThread  = new Thread( dc );

        addThread.start();
        subThread.start();
        mulThread.start();
        divThread.start();

        try
        {
            addThread.join();
            subThread.join();
            mulThread.join();
            divThread.join();
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        AbstractCommand shutDown = new ShutdownCommand();
        ClientThread sdc = new ClientThread(shutDown);
        Thread  shutdown  = new Thread( sdc );
        shutdown.start();


    }

    private class ClientThread implements Runnable
    {
        AbstractCommand cmd;
        ClientThread(AbstractCommand cmd)
        {
            this.cmd = cmd;

        }

        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run()
        {
            Client client = new Client(port);
            try
            {
                client.execute(cmd);
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
  }
