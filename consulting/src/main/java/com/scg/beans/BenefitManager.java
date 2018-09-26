package com.scg.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.Consultant;

/**
 * Benefit Manager listens to benefit changes
 * @author Monika
 *
 */
public class BenefitManager implements PropertyChangeListener, BenefitListener
{
    /**
     * Logger to log changes 
     */
    public static final Logger log = 
            (Logger)LoggerFactory.getLogger(BenefitManager.class.getName());
    
    /* (non-Javadoc)
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        log.info(evt.getPropertyName()+" : "+((Consultant) evt.getSource()).getName()+"; old value: "+evt.getOldValue()
        +", new value: "+evt.getNewValue());

    }

    /* (non-Javadoc)
     * @see com.scg.beans.BenefitListener#cancelDental(com.scg.beans.BenefitEvent)
     */
    @Override
    public void cancelDental(BenefitEvent evt)
    {
        log.info("DentalBenefit Cancelled : "+evt.getConsultant().getName()+
                ", effectiveDate : "+evt.getEffectiveDate());
    }

    /* (non-Javadoc)
     * @see com.scg.beans.BenefitListener#enrollDental(com.scg.beans.BenefitEvent)
     */
    @Override
    public void enrollDental(BenefitEvent evt)
    {
        log.info("DentalBenefit Enrolled : "+evt.getConsultant().getName()+
                ", effectiveDate : "+evt.getEffectiveDate());
        }

    /* (non-Javadoc)
     * @see com.scg.beans.BenefitListener#cancelMedical(com.scg.beans.BenefitEvent)
     */
    @Override
    public void cancelMedical(BenefitEvent evt)
    {
        log.info("MedicalBenefit Cancelled : "+evt.getConsultant().getName()+
                ", effectiveDate : "+evt.getEffectiveDate());
          
    }

    /* (non-Javadoc)
     * @see com.scg.beans.BenefitListener#enrollMedical(com.scg.beans.BenefitEvent)
     */
    @Override
    public void enrollMedical(BenefitEvent evt)
    {
        log.info("MedicalBenefit Enrolled : "+evt.getConsultant().getName()+
                ", effectiveDate : "+evt.getEffectiveDate());
        
    }

}
