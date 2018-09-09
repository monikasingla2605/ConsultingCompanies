package consult.violet.java2.cs;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This is the superclass of all command objects; 
 * its behavior is dictated by the Command Pattern.
 * @author Monika
 *
 */
public abstract class AbstractCommand implements Serializable
{

    /**
     * default serial version id
     */
    private static final long serialVersionUID = 1L;
    public Receiver receiver;
    private BigDecimal operand1;
    private BigDecimal operand2;
    private BigDecimal result;
    private long workMillisMin;
    private long workMillisMax;


    /**
     * Instantiates a new class.
     * Following invocation, the operand1 and operand2 properties will be set to 0, 
     * and the receiver and result properties will be set to null.
     */
    public AbstractCommand()
    {
        operand1 = BigDecimal.ZERO;
        operand2 = BigDecimal.ZERO;
        workMillisMin = 0;
        workMillisMax = 0;
        result = null;
        receiver = null;
    }

    /**
     * Constructor. Sets the operand1 and operand2 properties to the given parameter values; 
     * sets the receiver and result properties to null. 
     * @param operand1 operand1 to be set
     * @param operand2 operand2 to be set
     * @param workMillisMin min time for pause
     * @param workMillisMax max time for paue
     * @throws NullPointerException throws exception if operand1/operand2 is null.
     */
    public AbstractCommand(BigDecimal operand1, BigDecimal operand2, 
            long workMillisMin, long workMillisMax) 
                    throws NullPointerException
    {
        setOperand1(operand1);
        setOperand2(operand2);
        setWorkMillisMin(workMillisMin);
        setWorkMillisMax(workMillisMax);
        result = null;
        receiver = null;   
    }

    /**
     * gets the minimum work milliseconds.
     * @return returns the minimum work milliseconds for thread
     */
    public long getWorkMillisMin()
    {
        return workMillisMin;
    }

    /**
     * sets the minimum work milliseconds
     * @param workMillisMin sets the minimum work milliseconds
     */
    public void setWorkMillisMin(long workMillisMin)
    {
        this.workMillisMin = workMillisMin;
    }

    /**
     * gets the max work milliseconds.
     * @return returns the max work milliseconds for thread
     */
    public long getWorkMillisMax()
    {
        return workMillisMax;
    }

    /**
     * sets the maximum work milliseconds
     * @param workMillisMin sets the max work milliseconds
     */
    public void setWorkMillisMax(long workMillisMax)
    {
        this.workMillisMax = workMillisMax;
    }

    /**
     * Receiver for the connection
     * @return returns the receiver
     */
    public Receiver getReceiver()
    {
        return receiver;
    }

    /**
     * Sets the receiver for the connection
     * @param receiver Sets the receiver
     */
    public void setReceiver(Receiver receiver)
    {
        this.receiver = receiver;
    }

    /**
     * Gets the operand1
     * @return returns the operand1.
     */
    public BigDecimal getOperand1()
    {
        return operand1;
    }

    /**
     * Sets operand1
     * @param operand1 returns operand1
     */
    public void setOperand1(BigDecimal operand1)
    {
        if(operand1 == null)
            throw new NullPointerException();
        this.operand1 = operand1;
    }

    /**
     * Gets the operand2
     * @return returns the operand2.
     */
    public BigDecimal getOperand2()
    {
        return operand2;
    }

    /**
     * Sets operand2
     * @param operand2 sets operand2
     */
    public void setOperand2(BigDecimal operand2)
    {
        if(operand2 == null)
            throw new NullPointerException();
        this.operand2 = operand2;
    }

    /**
     * Gets the result of operation.
     * @return Returns result of the operation.
     */
    public BigDecimal getResult()
    {
        return result;
    }

    /**
     * Sets the results of operation.
     * @param result Sets the result of operation.
     */
    public void setResult(BigDecimal result)
    {
        this.result = result;
    }


    /**
     * Abstract method required by command pattern
     */
    public abstract void execute();
}
