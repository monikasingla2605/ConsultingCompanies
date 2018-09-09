package consult.violet.java2.cs;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShutdownCommandTest
{

    @Test
    public void test()
    {
        ShutdownCommand cmd = new ShutdownCommand();
        Receiver rec = new Receiver();
        cmd.setReceiver(rec);
        cmd.execute();
       }

}
