package consult.violet.java2.cs.server;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import app.ClientDriver;
import consult.violet.java2.cs.server.Server;

public class ServerTest
{
    @Test
    public void test()
    {
        Server server = new Server(4885);
        //        ServerRunTest st = new ServerRunTest();
        //        Thread thread1 = new Thread(st);
        //        thread1.start();
        //               
        //        ClientRun ct = new ClientRun();
        //        Thread thread2 = new Thread(ct);
        //        thread2.start();
        //     //  Server.main(null);
        //    }
        //    
        //    public class ServerRunTest implements Runnable
        //    {
        //
        //        @Override
        //        public void run()
        //        {
        //            Server.main(null);
        //        }
        //        
        //    }
        //    
        //    public class ClientRun implements Runnable
        //    {
        //
        //        @Override
        //        public void run()
        //        {
        //           ClientDriver.main(null); 
        //        }
        //        
    }
}
