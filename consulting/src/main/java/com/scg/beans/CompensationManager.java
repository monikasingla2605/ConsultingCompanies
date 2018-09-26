package com.scg.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.Consultant;

/**
 * This class supports the staff consultant properties.
 * @author monikas
 *
 */
public class CompensationManager implements PropertyChangeListener, VetoableChangeListener
{
    private static final Logger log  = 
            (Logger) LoggerFactory.getLogger(CompensationManager.class.getName());

    /* (non-Javadoc)
     * @see java.beans.VetoableChangeListener#vetoableChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
    {
        long oldVal = (int) evt.getOldValue();
        long newVal = (int) evt.getNewValue();
        long limit = (long) (oldVal * 1.05);
        if(newVal>limit)
            throw new PropertyVetoException("Change is more than 5%", evt);
       
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        log.info(evt.getPropertyName()+" change for: "+((Consultant) evt.getSource()).getName()+
                "; Old Value : "+evt.getOldValue()
                +", New Value : "+evt.getNewValue());

    }


}
