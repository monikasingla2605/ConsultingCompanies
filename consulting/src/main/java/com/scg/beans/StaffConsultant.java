package com.scg.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import com.scg.domain.Consultant;
import com.scg.util.PersonalName;

/**
 * This class defines the pay structure and leaves and vacations of a consultant.
 * @author MonikaSingla
 *
 */
public class StaffConsultant extends Consultant 
    //implements       PropertyChangeListener,VetoableChangeListener
{
    public static int payRate;
    public static int sickLeaveHours;
    public static int vacationHours; 

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private VetoableChangeSupport vcs = new VetoableChangeSupport(this);

    private final String PAY_RATE_PROPERTY_NAME = "payRate";
    private final String SICK_LEAVE_HOURS_PROPERTY_NAME = "sickLeaveHours";
    private final String VACATION_HOURS_PROPERTY_NAME = "vacationHours";
    
    
    /**
     * Instantiates a new class
     * @param name Name of the consultant
     * @param payRate payRAte of the consultant
     * @param sickLeaveHours sick leave hours of the consultant.
     * @param vacationHours vacation hours given to him.
     */
    public StaffConsultant
                ( PersonalName name,
                 int payRate,
                 int sickLeaveHours,
                 int vacationHours)
    {
        super(name);
        setSickLeaveHours(sickLeaveHours);
        setVacationHours(vacationHours);
        this.payRate = payRate;
//        try
//        {
//            setPayRate(payRate);
//        }
//        catch(PropertyVetoException exc)
//        {
//            exc.printStackTrace();
//        }
    }


    /**
     * Getter for the pay rate.
     * @return Returns the pay rate.
     */
    public int getPayRate()
    {
        return payRate;
    }

    /**
     * Sets the pat rate
     * @param payRate sets the pay rate to this value.
     * @throws PropertyVetoException Throws exception if the change is vetoed.
     */
    public void setPayRate(int payRate) throws PropertyVetoException
    {
        int oldVal = this.payRate;
        //System.out.println("oldVal"+oldVal);
        vcs.fireVetoableChange(PAY_RATE_PROPERTY_NAME, oldVal, payRate);
        this.payRate = payRate;
        pcs.firePropertyChange(PAY_RATE_PROPERTY_NAME,oldVal, this.payRate);

    }

    /**
     * Getter for the sick leaves.
     * @return Returns the sick leave hours.
     */
    public int getSickLeaveHours()
    {
        return sickLeaveHours;
    }

    /**
     * SEtter for sick leave hours.
     * @param sickLeaveHours Sets this to sick leave hours.
     */
    public void setSickLeaveHours(int sickLeaveHours)
    {
        int oldVal = this.sickLeaveHours;
        this.sickLeaveHours = sickLeaveHours;
        pcs.firePropertyChange(SICK_LEAVE_HOURS_PROPERTY_NAME, oldVal, this.sickLeaveHours);
    }

    /**
     * Getter for vacation hours.
     * @return the vacation hours for consultant.
     */
    public int getVacationHours()
    {
        return vacationHours;
    }

    /**
     * Setter for vacation hours.
     * @param vacationHours Sets this vacation hours.
     */
    public void setVacationHours(int vacationHours)
    {
        int oldVal = this.vacationHours;
        this.vacationHours = vacationHours;
        pcs.firePropertyChange(VACATION_HOURS_PROPERTY_NAME, oldVal, this.vacationHours);
    }
    
    /**
     * adds pay rate property change listener
     * @param listener listener event for pay rat property change
     */
    public synchronized void addPayRateListener(PropertyChangeListener listener)
    {
       pcs.addPropertyChangeListener(PAY_RATE_PROPERTY_NAME, listener);
      // vcs.addVetoableChangeListener((VetoableChangeListener) listener);

    }

    /**
     * Removes the pay rate property change listener
     * @param listener property change listener to be removed.
     */
    public synchronized void removePayRateListener(PropertyChangeListener listener)
    {
       pcs.removePropertyChangeListener(PAY_RATE_PROPERTY_NAME, listener);
    }

    /**
     * adds sick leave property change listener
     * @param listener listener event for pay rat property change
     */
    public synchronized void addSickLeaveHoursListener(PropertyChangeListener listener)
    {
       pcs.addPropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, listener);

    }

    /**
     * Removes the sick leave property change listener
     * @param listener property change listener to be removed.
     */
    public synchronized void removeSickLeaveHoursListener(PropertyChangeListener listener)
    {
       pcs.removePropertyChangeListener(SICK_LEAVE_HOURS_PROPERTY_NAME, listener);
    }

    /**
     * adds vacation hour property change listener
     * @param listener listener event for pay rat property change
     */
    public synchronized void addVacationHoursListener(PropertyChangeListener listener)
    {
       pcs.addPropertyChangeListener(VACATION_HOURS_PROPERTY_NAME, listener);

    }

    /**
     * Removes the vacation hour rate property change listener
     * @param listener property change listener to be removed.
     */
    public synchronized void removeVacationHoursListener(PropertyChangeListener listener)
    {
       pcs.removePropertyChangeListener(VACATION_HOURS_PROPERTY_NAME, listener);
    }


    
    /**
     * adds property change listener
     * @param listener listener to added to property change.
     */
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener)
    {
        pcs.addPropertyChangeListener(listener);

    }

    /**
     * removes a property change listener
     * @param listener listener for the property change support.
     */
    public synchronized void removePropertyChangeListener(PropertyChangeListener listener)
    {
        pcs.removePropertyChangeListener(listener); 
    }

    /**
     * add a vetoable property change listener.
     * @param listener listener for the vetoable change support.
     */
    public synchronized void addVetoableChangeListener(VetoableChangeListener listener)
    {
        vcs.addVetoableChangeListener(listener);

    }
    
   
    /**
     * removes a vetoable property change listener.
     * @param listener listener for the vetoable change support.
     */
    public synchronized void removeVetoableChangeListener(VetoableChangeListener listener)
    {
        vcs.removeVetoableChangeListener(listener);

    }
  
    /* (non-Javadoc)
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
//    @Override
//    public void propertyChange(PropertyChangeEvent evt)
//    {
//        System.out.println("Name = "+evt.getPropertyName());
//        System.out.println("Old Value = "+evt.getOldValue());
//        System.out.println("New Value = "+evt.getNewValue());
//
//    }

    /* (non-Javadoc)
     * @see java.beans.VetoableChangeListener#vetoableChange(java.beans.PropertyChangeEvent)
     */
//    @Override
//    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
//    {
//        System.out.println("Name = "+evt.getPropertyName());
//        System.out.println("Old Value = "+evt.getOldValue());
//        System.out.println("New Value = "+evt.getNewValue());
//
//    }
    

}
