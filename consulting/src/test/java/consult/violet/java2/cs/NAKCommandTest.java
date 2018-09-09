package consult.violet.java2.cs;

import static org.junit.Assert.*;

import org.junit.Test;

import consult.violet.java2.cs.NAKCommand;
import consult.violet.java2.cs.Receiver;

public class NAKCommandTest
{

    @Test
    public void test()
    {
       NAKCommand cmd = new NAKCommand();
       Receiver rec = new Receiver();
       cmd.setReceiver(rec);
       cmd.execute();
       String str = "NAKCommand";
       assertEquals(cmd.toString(),str);
    }

}
