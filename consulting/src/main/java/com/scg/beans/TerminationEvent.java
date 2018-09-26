package com.scg.beans;

import java.util.EventObject;

import com.scg.domain.Consultant;


/**
 * This class generates object which termination listener listens to.
 * @author Monika
 *
 */
public class TerminationEvent extends EventObject
{
    private static final long serialVersionUID = 1L;
    private Consultant consultant;
    private boolean voluntary;
    
    /**
     * Getter for the consultant
     * @return Returns the consultant
     */
    public Consultant getConsultant()
    {
        return this.consultant;
    }
    
    /**
     * Getter for voluntary or forced termination.
     * @return true/false for termination is voluntary/involuntary 
     */
    public boolean isVoluntary()
    {
        return this.voluntary;
    }
    
    /**
     * Instantiate a new class
     * @param consultant Consultant for termination
     * @param voluntary if it/s voluntary or forced.
     */
    public TerminationEvent( Consultant consultant, boolean voluntary )
    {
        super(consultant);
        this.consultant = consultant;
        this.voluntary = voluntary;
    }
   
}
