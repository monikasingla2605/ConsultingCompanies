package com.scg.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

import com.scg.domain.TimeCard;

/**
 * This is Address Class.
 * It defines the address attributes like streetNumber, city, state and post code.
 * @author Monika
 *
 */
public class Address implements Comparable<Address>, Serializable
{
    private String streetNumber;
    private String city;
    private StateCode state;
    private String postCode;

    /**
     * Instantiates a new Address.
     * And throws an illegalArgumentException if any of the parameter is null.
     * @param streetNum Street Address of the address part.
     * @param city City of the address part.
     * @param state State of the address part. It is of type enum StateCode
     * @param postCode Zip code of the address part. It is of type String.
     * 
     */
    public Address(String streetNum, String city, StateCode state, String postCode)
    {   
        if(streetNum == null || city == null || postCode == null)
            throw new IllegalArgumentException();
        else
        {
            this.streetNumber = streetNum;
            this.city = city;
            this.postCode = postCode;
            this.state = state;
        }
    }

    /**
     * It gets the Street Number of the Address.
     * @return Returns street number of the address.
     */
    public String getStreetNumber()
    {
        return streetNumber;
    }

    /**
     * It gets the city name of the address part.
     * @return Returns the city from the address attributes.
     */
    public String getCity()
    {
        return city;
    }

    /**
     * It gets the state code for the 50 states of US
     * @return Returns the state code for the state which is type enum <em>stateCode.</em>
     */
    public StateCode getState()
    {
        return state;
    }

    /**
     * This get the zip code of the address.
     * @return Returns the post code of the address.
     */
    public String getPostalCode()
    {
        return postCode;
    }

    /* 
     * Overrides to string method.
     * returns the complete address
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append(streetNumber).append(System.getProperty("line.separator"));
        str.append(city).append(", ").append(state).append(" ").append(postCode);      
        return str.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        else if(this == obj)
            return true;
        else if(!(obj instanceof Address))
            return false;
        else
        {
            Address that = (Address)obj;
            return Objects.equals(this.streetNumber, that.streetNumber) &&
                    Objects.equals(this.city, that.city) &&
                    Objects.equals(this.state, that.state) &&
                    Objects.equals(this.postCode, that.postCode);

        }
    }

    @Override
    public int hashCode()
    {
        int hash = Objects.hash(this.streetNumber, this.city, this.state, this.postCode);
        return hash;
    }

    @Override
    public int compareTo(Address add)
    {
        int result = Comparator.comparing(Address::getState)
                .thenComparing(Address::getPostalCode)
                .thenComparing(Address::getCity)
                .thenComparing(Address::getStreetNumber)
                .compare(this,add);
        return result;


    }

}
