package com.scg.beans;

import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.Consultant;

/**
 * This class describes the termiantion events and benefits for the consultants.
 * @author Monika
 *
 */
public class HumanResourceManager
{
    private static final Logger log  = 
            (Logger) LoggerFactory.getLogger(HumanResourceManager.class.getName());
    
    private List<BenefitListener> listBE;
    private List<TerminationListener> listTE;

    /**
     * Method is called to accept resignation of a consultant
     * @param consultant consultant who has resigned
     */
    public void acceptResignation( Consultant consultant )
    {
        TerminationEvent evt = new TerminationEvent(consultant, true);
        listTE.forEach(p->p.voluntaryTermination(evt));
    }

    /**
     * To terminate a consultant.
     * @param consultant Consultant who is terminated.
     */
    public void terminate( Consultant consultant )
    {
        TerminationEvent evt = new TerminationEvent(consultant, false);
        listTE.forEach(p->p.forcedTermination(evt));
        
    }

    /**
     * to change the pay rate
     * @param consultant consultant whose pay rate is changed.
     * @param newPayRate new pay rate for the consultant.
     */
    public void adjustPayRate( StaffConsultant consultant, int newPayRate )
    {
        try
        {
            consultant.setPayRate(newPayRate);
            log.info("Pay rate change for: "+consultant.getName().toString()
                    +"; approved");

        }
        catch(PropertyVetoException exc)
        {
            log.info("Pay rate change for: "+consultant.getName().toString()
                    +"; vetoed");
        }
    }

    /**
     * sick leave hours change
     * @param consultant consultant whose sick leave are to be changed
     * @param newSickLeaveHours new sick leave hours consultant gets.
     */
    public void adjustSickLeaveHours( StaffConsultant consultant, int newSickLeaveHours )
    {
        consultant.setSickLeaveHours(newSickLeaveHours);
    }

    /**
     * vacation hours changes
     * @param consultant consultant whose vacation hours are to be changed.
     * @param newVacationHours new vacation hours for the consultant
     */
    public void adjustVacationHours( StaffConsultant consultant, int newVacationHours )
    {
        consultant.setVacationHours(newVacationHours);
    }
    
    /**
     * add listener for any termination event
     * @param listener listener event for termination.
     */
    public void addTerminationListener( TerminationListener listener )
    {
        listTE.add(listener);
    }

    /**
     * remove termination listener evemts.
     * @param listener termination event to be removed.
     */
    public void removeTerminationListener( TerminationListener listener )
    {
        listTE.remove(listener);
    }

    /**
     * adds a benefit listener
     * @param listener listener event to be added.
     */
    public void addBenefitListener( BenefitListener listener )
    {
        listBE.add(listener);
    }

    /**
     * remove a benefit listener.
     * @param listener listener event to be removed.
     */
    public void removeBenefitListener( BenefitListener listener )
    {
        listBE.remove(listener);
    }
    
    /**
     * fires medical enrollment event.
     * @param consultant consultant who has to be enrolled.
     */
    public void enrollMedical( Consultant consultant )
    {
        LocalDate date = LocalDate.now();
        BenefitEvent event = BenefitEvent.enrollMedical( consultant, date);
        listBE.forEach(p->p.enrollMedical(event));
    }
    
    /**
     * fires medical cancellation event.
     * @param consultant consultant who has to be cancelled.
     */
    public void cancelMedical( Consultant consultant )
    {
        LocalDate date = LocalDate.now();
        BenefitEvent event = BenefitEvent.cancelMedical( consultant, date);
        listBE.forEach(p->p.cancelMedical(event));
    }
    
    /**
     * fires dental enrollment event.
     * @param consultant consultant who has to be enrolled.
     */
    public void enrollDental( Consultant consultant )
    {
        LocalDate date = LocalDate.now();
        BenefitEvent event = BenefitEvent.enrollDental( consultant, date);
        listBE.forEach(p->p.enrollDental(event));
    }
    
    /**
     * fires dental enrollment event.
     * @param consultant consultant who has to be cancelled.
     */
    public void cancelDental( Consultant consultant )
    {
        LocalDate date = LocalDate.now();
        BenefitEvent event = BenefitEvent.cancelDental( consultant, date);
        listBE.forEach(p->p.cancelDental(event));
    }
   
    /**
     * Instantiates a new class
     */
    public HumanResourceManager()
    {
        listBE = new ArrayList<>();
        listTE = new ArrayList<>();
        
    }
}
