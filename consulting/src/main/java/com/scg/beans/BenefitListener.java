package com.scg.beans;

import java.util.EventListener;

/**
 * Interface for benefit listener
 * @author Monika
 *
 */
public interface BenefitListener extends EventListener
{
    /**
     * Cancel dental plan
     * @param event BenefitEvent to cancel dental plan
     */
    public void cancelDental( BenefitEvent event );
    
    /**
     * Enroll for dental plan
     * @param event Benefit event to enroll for dental plan
     */
    public void enrollDental( BenefitEvent event );
    
    /**
     * Enroll medical plan
     * @param event BenefitEvent to cancel medical plan
     */
    public void cancelMedical( BenefitEvent event );
    
    /**
     * Cancel medical plan
     * @param event BenefitEvent to cancel medical plan
     */
    public void enrollMedical( BenefitEvent event );
   
}
