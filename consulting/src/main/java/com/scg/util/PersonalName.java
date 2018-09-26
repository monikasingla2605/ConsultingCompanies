package com.scg.util;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class defines the name of the person.
 * It defines first name, middle name and last name of a person.
 * @author Monika
 *
 */
public class PersonalName implements Comparable<PersonalName>, Serializable
{
    private String firstName;
    private String middleName;
    private String lastName;

    /**
     * Instantiates a new class.
     * It calls the 2 parameter constructor with empty last name and first name.
     */
    public PersonalName()
    {
        this("","");
    }

    /**
     * Instantiates a new class.
     * It calls the 3 parameter constructor with middle name as empty string.
     * @param lastName Last name of the person.
     * @param firstName First name of the person.
     */
    public PersonalName(String lastName, String firstName)
    {
        this(lastName,firstName, "");
    }

    /**
     * Instantiates a new class.
     * If last name, middle name or first name is null, it is replaced with empty string.
     * @param lastName Last name of the person.
     * @param firstName First name of the person.
     * @param middleName Middle name of the person.
     */
    public PersonalName(String lastName, String firstName, String middleName)
    {
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
    }

    //    private Object writeReplace() throws ObjectStreamException
    //    {
    //       // Logger.info(this.toString());
    //        System.out.println("here in PersonalName class"+this);
    //        Proxy proxy = new Proxy(this);
    //        return proxy;
    //        
    //    }
    //    
    //     /*
    //     * Deserialization shouldn't ever execute this bit
    //     * of logic; throw an exception if we ever get here.
    //     */
    //    private void readObject( ObjectInputStream in )
    //        throws InvalidObjectException
    //    { 
    //        throw new InvalidObjectException( "proxy required" );
    //    }


    /**
     * It gets the first name of the person name.
     * @return Returns the first name of the person.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets the first name of the person.
     * @param firstName First name of the person.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName == null? "":firstName; 
    }

    /**
     * It gets the middle name of the person.
     * @return Returns the middle name of the perosn.
     */
    public String getMiddleName()
    {
        return middleName;
    }

    /**
     * It sets the middle name of the person.
     * @param middleName Middle name of the person.
     */
    public void setMiddleName(String middleName)
    {
        this.middleName = middleName == null ? "" : middleName;
    }

    /**
     * It returns the last name of the person.
     * @return Returns the last name of the person.
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets the last name of the person.
     * @param lastName Last name of the person.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName == null ? "" : lastName;
    }

    /* 
     * over rides the toString method in java.util.
     * If middle name is empty or null, it does not show it.
     * It returns the name of the person.
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuffer str = new StringBuffer("");
        str.append(lastName).append(", ");
        if(middleName == null|| middleName.isEmpty())
            str.append(firstName);
        else
            str.append(firstName).append(" ").append(middleName);
        return str.toString();

    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        else if(this == obj)
            return true;
        else if(!(obj instanceof PersonalName))
            return false;
        else
        {
            PersonalName that = (PersonalName)obj;
            return Objects.equals(this.firstName, that.firstName) &&
                    Objects.equals(this.middleName, that.middleName) &&
                    Objects.equals(this.lastName, that.lastName);

        }
    }

    @Override
    public int hashCode()
    {
        int hash = Objects.hash(this.firstName, this.middleName, this.lastName);
        return hash;
    }

    @Override
    public int compareTo(PersonalName name)
    {
        int result = Comparator.comparing(PersonalName::getLastName)
                .thenComparing(PersonalName::getFirstName)
                .thenComparing(PersonalName::getMiddleName)
                .compare(this,name);
        return result;
    }


}
