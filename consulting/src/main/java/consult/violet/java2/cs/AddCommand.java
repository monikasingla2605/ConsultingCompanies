package consult.violet.java2.cs;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Command Class to add two BigDecimal
 * @author Monika
 *
 */
public class AddCommand extends AbstractCommand
{


    /**
     * default serial version id
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new class
     * @param op1 operand1 
     * @param op2 operand2
     */
    public AddCommand(BigDecimal op1, BigDecimal op2, long workMillisMin, long workMillisMax)
    {
        super(op1,op2,workMillisMin,workMillisMax);
    }

    /**
     * Instantiates a new class
     */
    public AddCommand()
    {
        super();
    }

    /**
     * Instantiate a new addcommand class
     * @param op1 Operand1 for command
     * @param op2 operand 2 for command
     * @param workMillisMin min time for pause
     * @param workMillisMax max time for pause
     */
    public AddCommand(Double op1, Double op2,long workMillisMin, long workMillisMax)
    {
        super(new BigDecimal(op1),new BigDecimal(op2), workMillisMin, workMillisMax);
    }


    /* (non-Javadoc)
     * @see edu.uweo.java2.assignment8.AbstractCommand#execute()
     */
    @Override
    public void execute()
    {
        getReceiver().action( this );
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getOperand1()).append(" + ");
        sb.append(this.getOperand2()).append(" = ");
        sb.append(this.getResult());
        return sb.toString();      

    }

}
