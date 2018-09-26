package com.scg.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements termination listener
 * @author monika
 *
 */
public class Eeoc implements TerminationListener
{
    private int countVT =0;
    private int countIVT = 0;
    private static final Logger log  = 
            (Logger) LoggerFactory.getLogger(Eeoc.class.getName());

    /**
     * Getter for forced termination count;
     * @return Forced Termination count.
     */
    public int forcedTerminationCount()
    {
        return countIVT;

    }

    /**
     * Getter for Voluntary termination count.
     * @return Returns voluntary termination count.
     */
    public int voluntaryTerminationCount()
    {
        return countVT;

    }

    /* (non-Javadoc)
     * @see com.scg.beans.TerminationListener#forcedTermination(com.scg.beans.TerminationEvent)
     */
    @Override
    public void forcedTermination(TerminationEvent event)
    {
        countIVT++;
        log.info("Involuntary termination:"+event.getConsultant().getName());
    }

    /* (non-Javadoc)
     * @see com.scg.beans.TerminationListener#voluntaryTermination(com.scg.beans.TerminationEvent)
     */
    @Override
    public void voluntaryTermination(TerminationEvent event)
    {
        countVT++;
        log.info("Voluntary termination:"+event.getConsultant().getName());
    }

}
