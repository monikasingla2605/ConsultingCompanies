package consult.violet.java2.cs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class will encapsulate a receiver object, as described by the command pattern.
 * @author Monika
 *
 */
public class Receiver implements Serializable
{

    /**
     * serial id
     */
    private static final long serialVersionUID = 1502769733294885293L;

    /**
     * to add two numbers
     * @param cmd command to add two numbers
     */
    public void action( AddCommand cmd )
    {
        BigDecimal op1 = cmd.getOperand1();
        BigDecimal op2 = cmd.getOperand2();
        BigDecimal result;
        MathContext mc = new MathContext(4);
        result = op1.add(op2,mc);
        cmd.setResult(result);

    }

    /**
     * to subtract two numbers
     * @param cmd command to subtract two numbers
     */
    public void action( SubCommand cmd )
    {
        BigDecimal op1 = cmd.getOperand1();
        BigDecimal op2 = cmd.getOperand2();
        BigDecimal result;
        MathContext mc = new MathContext(4);
        result = op1.subtract(op2,mc);
        cmd.setResult(result);
    }

    /**
     * to multiply two numbers
     * @param cmd command to multiply two numbers.
     */
    public void action( MulCommand cmd )
    {
        BigDecimal op1 = cmd.getOperand1();
        BigDecimal op2 = cmd.getOperand2();
        BigDecimal result;
        MathContext mc = new MathContext(4);
        result = op1.multiply(op2,mc);
        cmd.setResult(result);
    }

    /**
     * to divide two numbers
     * @param cmd command to divide two numbers
     */
    public void action( DivCommand cmd )
    {
        BigDecimal op1 = cmd.getOperand1();
        BigDecimal op2 = cmd.getOperand2();
        BigDecimal result;
        result = op1.divide(op2,3, RoundingMode.CEILING);
        cmd.setResult(result);

    }

    /**
     * to send nakcommand
     * @param cmd nakcommand object
     */
    public void action(NAKCommand cmd)
    {
        System.out.println("NAKCommand Received");
    }

    /**
     * to send shutdown command
     * @param cmd shutdown the server
     */
    public void action (ShutdownCommand cmd)
    {
        System.out.println("NAKCommand Received");
    }




}
