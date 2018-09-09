package consult.violet.java2.cs;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import consult.violet.java2.cs.AbstractCommand;
import consult.violet.java2.cs.MulCommand;
import consult.violet.java2.cs.Receiver;

public class MulCommandTest
{

    @Test
    public void test()
    {
        BigDecimal op1 = new BigDecimal("0.5");
        BigDecimal op2 = new BigDecimal("0.7");
        long min = 200;
        long max = 500;
        AbstractCommand command = new MulCommand(op1,op2, min, max);
        Receiver rec = new Receiver();
        command.setReceiver(rec);
        assertEquals(command.getOperand1(), op1);
        assertEquals(command.getOperand2(), op2);
        
        AbstractCommand cmd = new MulCommand();
        cmd.setOperand1(op1);
        cmd.setOperand2(op2);
        assertEquals(cmd.getOperand1(), op1);
        assertEquals(cmd.getOperand2(), op2);
        
        double op11 = 10;
        double op22 = 5;
        AbstractCommand cmd1 = new MulCommand(op11, op22, min, max);
        BigDecimal op01 = new BigDecimal(op11);
        BigDecimal op02 = new BigDecimal(op22);
        assertEquals(cmd1.getOperand1(), op01);
        assertEquals(cmd1.getOperand2(), op02);
        
        command.execute();
        BigDecimal result = command.getResult();
        System.out.println(result);
        BigDecimal res = new BigDecimal("0.35");
        assertEquals(result,res );
        
        String sr = "0.5 * 0.7 = 0.35";
        assertEquals(sr,command.toString());
      
       }

}
