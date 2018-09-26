package com.scg.domain;

/**
 * This is enum class which implements Account interface.
 * This class defines the types of non billable accounts.
 * @author Monika
 *
 */
public enum NonBillableAccount implements Account
{
    BUSINESS_DEVELOPMENT("Business Development"),
    SICK_LEAVE("Sick Leave"),
    VACATION("Vacation");
    
    private String name;
   
    private NonBillableAccount(String name)
    {
        this.name= name;
    }
    
    /* (non-Javadoc)
     * @see com.scg.domain.Account#getName()
     */
    /* (non-Javadoc)
     * @see com.scg.domain.Account#getName()
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    /* (non-Javadoc)
     * @see com.scg.domain.Account#isBillable()
     */
    @Override
    public boolean isBillable()
    {
        return false;
    }

}
