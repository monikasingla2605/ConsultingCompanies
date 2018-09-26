package com.scg.domain;

/**'
 * This is an interface.
 * @author Monika
 *
 */
public interface Account
{
    /**
     * Gets the name of the consultant.
     * @return Returns the name of the consultant.
     */
    public String getName();
 
    /** 
     * Tells if account is billable or not.
     * @return true if account is billable and false if it's not.
     */
    public boolean isBillable();    
}
