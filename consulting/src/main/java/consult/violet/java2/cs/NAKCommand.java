package consult.violet.java2.cs;

/**
 * NAKCommand class
 * @author Monika
 *
 */
public class NAKCommand extends AbstractCommand
{

    /**
     * default serial id
     */
    private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see edu.uweo.java2.assignment9.AbstractCommand#execute()
     */
    @Override
    public void execute()
    {
        // TODO Auto-generated method stub
        getReceiver().action( this );

    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String str = "NAKCommand";
        return str;      

    }


}
