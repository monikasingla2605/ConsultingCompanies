package consult.violet.java2.cs;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import consult.violet.java2.cs.AbstractCommand;
import consult.violet.java2.cs.Receiver;
import consult.violet.java2.cs.SubCommand;

public class SubCommandTest
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
        cmd.setOperand1(op1);
        cmd.setOperand2(op2);
        assertEquals(cmd.getOperand1(), op1);
        assertEquals(cmd.getOperand2(), op2);
        
        double op11 = 10;
        double op22 = 5;
        AbstractCommand cmd1 = new SubCommand(op11, op22, min, max);
        BigDecimal op01 = new BigDecimal(op11);
        BigDecimal op02 = new BigDecimal(op22);
        assertEquals(cmd1.getOperand1(), op01);
        assertEquals(cmd1.getOperand2(), op02);
        
        command.execute();
        BigDecimal result = command.getResult();
        BigDecimal res = new BigDecimal("-0.2");
        assertEquals(result,res );
        
        String sr = "0.5 - 0.7 = -0.2";
        assertEquals(sr,command.toString());
      
       }

}
