package com.scg.beans;

import java.time.LocalDate;
import java.util.EventObject;
import java.util.Optional;

import com.scg.domain.Consultant;

/**
 * This class encapsulates the benefits for the consultants.
 * @author Monika
 *
 */
public class BenefitEvent extends EventObject
{
    /**
     * serial version id
     * 
     */
    private static final long serialVersionUID = 1L;
    private Consultant consultant;
    private LocalDate effectiveDate;
    private Optional<Boolean> medicalStatus;
    private Optional<Boolean> dentalStatus;
    
    /**
     * Enrolls a consultant for the medical benefit.
     * @param consultant Consultant to be enrolled.
     * @param effectiveDate effective date for the property.
     * @return BenefitEvent object.
     */
    public static BenefitEvent enrollMedical( Consultant consultant, LocalDate effectiveDate )
    {
        BenefitEvent be = new BenefitEvent
                (consultant, effectiveDate,
                        Optional.of(true), Optional.empty());  
        return be;
        
    }
    
    /**
     * Cancels medical benefit
     * @param consultant Consultant whose medical benefit is to canceled.
     * @param effectiveDate Effective date for the property change.
     * @return BenefitEvent Object
     */
    public static BenefitEvent cancelMedical( Consultant consultant, LocalDate effectiveDate )
    {
        BenefitEvent be = new BenefitEvent
                (consultant, effectiveDate,
                        Optional.of(false), Optional.empty());  

        return be;
        
    }
    
    /**
     * Enrolls dental benefit
     * @param consultant Consultant whose benefit has to be enrolled.
     * @param effectiveDate Effective date for the property change.
     * @return Returns Benefit Event Object.
     */
    public static BenefitEvent enrollDental( Consultant consultant, LocalDate effectiveDate )
    {
        BenefitEvent be = new BenefitEvent
                (consultant, 
                 effectiveDate,
                 Optional.empty(),
                 Optional.of(true));  

        return be;

    }
    
    /**
     * Cancels dental plan
     * @param consultant Consultant whose dental plan has to be canceled
     * @param effectiveDate effective date for 
     * @return benefitEvent object
     */
    public static BenefitEvent cancelDental( Consultant consultant, LocalDate effectiveDate )
    {
        BenefitEvent be = new BenefitEvent
                (consultant, 
                 effectiveDate,
                 Optional.empty(),
                 Optional.of(false));  

        return be;
 
    }
    
    /**
     * Gets the medical status for the consultant
     * @return true / false for medical status.
     */
    public Optional<Boolean> medicalStatus()
    {
        
        return this.medicalStatus;
        
    }

    /**
     * gets the dental status
     * @return true/ false if dental status is enrolled or not.
     */
    public Optional<Boolean> dentalStatus()
    {
        
        return this.dentalStatus;
        
    }
    
    /**
     * Getter for the consultant.
     * @return Returns the consultant/.
     */
    public Consultant getConsultant()
    {
        return this.consultant;
    }

    /**
     * Getter for the effective date
     * @return returns the effective date for the plan
     */
    public LocalDate getEffectiveDate()
    {
        return this.effectiveDate;
    }

    
    /**
     * Instantiates a new class.
     * @param obj for the event object.
     */
    private BenefitEvent(
            Object consultant,
            LocalDate effectiveDate,
            Optional<Boolean> medicalStatus,
            Optional<Boolean> dentalStatus)
    {
        super(consultant);
        this.consultant = (Consultant) consultant;
        this.effectiveDate = effectiveDate;
        this.medicalStatus = medicalStatus;
        this.dentalStatus = dentalStatus;
        
    }
    
}
