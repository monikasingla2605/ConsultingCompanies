package consult.violet.java2.cs.client;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import consult.violet.java2.cs.AbstractCommand;
import consult.violet.java2.cs.AddCommand;
import consult.violet.java2.cs.client.Client;

public class ClientTest
{

    @Test
    public void divtest()
    {
        Client client      = new Client(4885);
        try
        {
            BigDecimal op1 = new BigDecimal("0.5");
            BigDecimal op2 = new BigDecimal("0.7");
            long min = 100;
            long max  =200;
            AbstractCommand command = new AddCommand(op1,op2, min, max);
            System.out.println("going to call execute method");
            client.execute(command);
            
        }
        catch ( Exception exc )
        {
           System.out.println("Exception");
        }
   
       
    }

//    @Test
//    public void naktest()
//    {
//        Client client  = new Client(4885);
//        try
//        {
//            System.out.println("going to send null obj");
//            client.execute(null);
//            
//        }
//        catch (Exception exc )
//        {
//            System.out.println("Exception caught in client test");
//        }
//   
//       
//    }

   

}
