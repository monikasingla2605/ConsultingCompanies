package com.scg.domain;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.util.PersonalName;

/**
 * This class defines the attributes of consultant.
 * @author Monika
 *
 */
public class Consultant implements Comparable<Consultant>,Serializable
{
    private PersonalName name;
    private static final Logger log  = 
            (Logger) LoggerFactory.getLogger(Consultant.class);
   

    /**
     * Instantiates a new class
     * @param name Name of the consultant which is of the type class <em> PersonalName. </em>
     */
    public Consultant(PersonalName name)
    {
        if(name == null)
            throw new IllegalArgumentException();
        else
            this.name = name;
    }
    
    /**
     * This method is called whenever consultant serialized request is made
     * @return It returns the serialized object
     * @throws ObjectStreamException throws an exception
     */
    private Object writeReplace() throws ObjectStreamException
    {
       // Logger.info(this.toString());
        log.info(this.toString());
        Proxy proxy = new Proxy(this);
        return proxy;
        
    }
    
     /*
     * Deserialization shouldn't ever execute this bit
     * of logic; throw an exception if we ever get here.
     */
    private void readObject( ObjectInputStream in )
        throws InvalidObjectException
    { 
        throw new InvalidObjectException( "proxy required" );
    }


    /**
     * It gets the name of the consultant.
     * @return Returns the name of the consultant.
     */
    public PersonalName getName()
    {
        return name;
    }

    /* 
     * Over rides the toString method.
     * returns the name of the consultant.
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return name.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        else if(this == obj)
            return true;
        else if (!(obj instanceof Consultant))
            return false;
        else
        {
            Consultant that = (Consultant)obj;
            return Objects.equals(this.name, that.name);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int hash = name.hashCode();
        return hash;
    }

    @Override
    public int compareTo(Consultant o)
    {
        if( o == null)
            throw new NullPointerException();
        else
        {
            int ret = Comparator.comparing(Consultant::getName)
                    .compare(this,o);
            return ret;
        }
        
    }
    
    /**
     * Proxy class to help with logging
     * @author Monika
     *
     */
    private static class Proxy implements Serializable
    {
        private static final long serialVersionUID = 6417920328922464293L;
        private static final Logger log  = 
                (Logger) LoggerFactory.getLogger(Proxy.class);
        private final PersonalName name;
        /**
         * Instantiates a new class
         * @param obj Object of type consultant to save <em>this </em> obj locally.
         */
        public Proxy(Consultant obj)
        {
            name = obj.getName();
        }
        
        /**
         * This method is called whever deserialized request to consultant class is made
         * @return Returns the deserialized object.
         */
        public Object readResolve()
        {
            Consultant result = new Consultant(name);
            System.out.println("reading data");
            log.info(name.toString());
            return result;
        }
       
       
    }

}
