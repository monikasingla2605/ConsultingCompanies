package com.scg.beans;

import java.util.EventListener;

/**
 * Interface to listen to termination events
 * @author Monika
 *
 */
public interface TerminationListener extends EventListener
{
    /**
     * This method is called if forced termination
     * @param event Termination of consultant event
     */
    public abstract void forcedTermination( TerminationEvent event );
    
    
    /**
     * This method is called if termination is voluntary
     * @param event Termination of consultant event
     */
    public abstract void voluntaryTermination( TerminationEvent event );

}
