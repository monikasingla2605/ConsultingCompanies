package consult.violet.java2.cs;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import consult.violet.java2.cs.AbstractCommand;
import consult.violet.java2.cs.Receiver;
import consult.violet.java2.cs.SubCommand;

public class AbstractCommandTest
{

    @Test
    public void test()
    {
        BigDecimal op1 = new BigDecimal("0.5");
        BigDecimal op2 = new BigDecimal("0.7");
        long min = 200;
        long max = 500;
        AbstractCommand command = new SubCommand(op1,op2, min, max);
        Receiver rec = new Receiver();
        command.setReceiver(rec);
        assertEquals(command.getOperand1(), op1);
        assertEquals(command.getOperand2(), op2);

        AbstractCommand cmd = new SubCommand();
        try 
        {
            cmd.setOperand1(null);   
        }
        catch(NullPointerException exc)
        {
            System.out.println("Exception caught");
        }

        try 
        {
            cmd.setOperand2(null);   
        }
        catch(NullPointerException exc)
        {
            System.out.println("Exception caught");
        }

        cmd.setOperand1(op1);
        cmd.setOperand2(op2);
        assertEquals(cmd.getOperand1(), op1);
        assertEquals(cmd.getOperand2(), op2);


        command.execute();
        BigDecimal result = command.getResult();
        BigDecimal res = new BigDecimal("-0.2");
        assertEquals(result,res );
    }
    
    @Test
    public void millisTest()
    {
        BigDecimal op1 = new BigDecimal("0.5");
      BigDecimal op2 = new BigDecimal("0.7");
    long min = 200;
    long max = 500;
    AbstractCommand command = new SubCommand(op1,op2, min, max);
    command.setWorkMillisMin(min);
    command.setWorkMillisMax(max);
    Receiver rec = new Receiver();
    command.setReceiver(rec);
    assertEquals(command.getWorkMillisMax(), max);
    assertEquals(command.getWorkMillisMin(), min);

        
    }

}
