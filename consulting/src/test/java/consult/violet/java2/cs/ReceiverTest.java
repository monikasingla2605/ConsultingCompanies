package consult.violet.java2.cs;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import consult.violet.java2.cs.AddCommand;
import consult.violet.java2.cs.DivCommand;
import consult.violet.java2.cs.MulCommand;
import consult.violet.java2.cs.Receiver;
import consult.violet.java2.cs.SubCommand;

public class ReceiverTest
{

    @Test
    public void addTest()
    {
        BigDecimal op1 = new BigDecimal("0.5");
        BigDecimal op2 = new BigDecimal("0.7");
        int min = 200;
        int max = 500;
        AddCommand addcmd = new AddCommand(op1,op2, min, max);
        Receiver rec = new Receiver();
        addcmd.setReceiver(rec);
        rec.action(addcmd);
        BigDecimal result = addcmd.getResult();
        BigDecimal res = new BigDecimal("1.2");
        assertEquals(result,res );
    }

    @Test
    public void subTest()
    {
        BigDecimal op1 = new BigDecimal("0.5");
        BigDecimal op2 = new BigDecimal("0.7");
        int min = 200;
        int max = 500;
        SubCommand subcmd = new SubCommand(op1,op2, min, max);
        Receiver rec = new Receiver();
        subcmd.setReceiver(rec);
        rec.action(subcmd);
        BigDecimal result = subcmd.getResult();
        BigDecimal res = new BigDecimal("-0.2");
        assertEquals(result,res );
    }

    @Test
    public void mulTest()
    {
        BigDecimal op1 = new BigDecimal("0.5");
        BigDecimal op2 = new BigDecimal("0.7");
        int min = 200;
        int max = 500;
        MulCommand mulcmd = new MulCommand(op1,op2, min, max);
        Receiver rec = new Receiver();
        mulcmd.setReceiver(rec);
        rec.action(mulcmd);
        BigDecimal result = mulcmd.getResult();
        BigDecimal res = new BigDecimal("0.35");
        assertEquals(result,res );
    }

    @Test
    public void divTest()
    {
        BigDecimal op1 = new BigDecimal("0.5");
        BigDecimal op2 = new BigDecimal("0.7");
        int min = 200;
        int max = 500;
        DivCommand divcmd = new DivCommand(op1,op2, min, max);
        Receiver rec = new Receiver();
        divcmd.setReceiver(rec);
        rec.action(divcmd);
        BigDecimal result = divcmd.getResult();
        BigDecimal res = new BigDecimal("0.715");
        assertEquals(result,res );
    }

    @Test
    public void shutDownTest()
    {
        ShutdownCommand cmd = new ShutdownCommand();
        Receiver rec = new Receiver();
        cmd.setReceiver(rec);
        rec.action(cmd);
        
    }

    @Test
    public void nakCommandTest()
    {
        NAKCommand cmd = new NAKCommand();
        Receiver rec = new Receiver();
        cmd.setReceiver(rec);
        rec.action(cmd);
        
    }

}
