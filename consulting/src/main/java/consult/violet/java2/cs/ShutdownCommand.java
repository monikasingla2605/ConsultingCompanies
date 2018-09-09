package consult.violet.java2.cs;

/**
 * shutdown class
 * @author monika
 *
 */
public class ShutdownCommand extends AbstractCommand
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
        getReceiver().action( this ); 
    }

}
