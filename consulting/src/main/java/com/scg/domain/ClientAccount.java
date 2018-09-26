package com.scg.domain;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import com.scg.util.Address;
import com.scg.util.PersonalName;

/**
 * This class defines the name of account and person and his address.
 * @author Monika
 *
 */
public class ClientAccount implements Account, Comparable<ClientAccount>, Serializable
{
    private String name;
    private PersonalName contact;
    private Address address;
    private boolean isBillable = true;

    /**
     * Instantiates a new class.
     * @param name Name of the account.
     * @param contact Contact name of the client.
     * @param address Address of the client.
     */
    public ClientAccount(String name, PersonalName contact, Address address)
    {
        this.setAddress(address);
        this.setContact(contact);
        this.name = name;
    }

    /* (non-Javadoc)
     * @see com.scg.domain.Account#getName()
     */
    public String getName()
    {
        return name;
    }

    /**
     * This gets the name of the client.
     * @return Returns name of the client. It is of the type PersonalName class.
     */
    public PersonalName getContact()
    {
        return contact;
    }

    /**
     * It sets the personal name of the client.
     * @param contact It sets the name of the client. It is of the type PersonalName class.
     */
    public void setContact(PersonalName contact)
    {
        this.contact = contact;
    }

    /**
     * This method gets the address of the client.
     * @return Returns address of the client.
     */
    public Address getAddress()
    {
        return address;
    }

    /**
     * This method sets the address of the client.
     * @param address It sets the address of the client. It is of the type class Address.
     */
    public void setAddress(Address address)
    {
        this.address = address;
    }

    /* (non-Javadoc)
     * @see com.scg.domain.Account#isBillable()
     */
    public boolean isBillable()
    {
        return isBillable;
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
        else if(!(obj instanceof ClientAccount ))
            return false;
        else
        {
            ClientAccount that = (ClientAccount)obj;
            return Objects.equals(this.name, that.name) &&
                    Objects.equals(this.contact, that.contact) &&
                    Objects.equals(this.address, that.address) &&
                    Objects.equals(this.isBillable, that.isBillable);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int hash = Objects.hash(this.name, this.contact, this.address, this.isBillable);
        return hash;
    }

    @Override
    public int compareTo(ClientAccount o)
    {
        if( o == null)
            throw new NullPointerException();
        else
        {
            int ret = Comparator.comparing(ClientAccount::getName)
                    .thenComparing(ClientAccount::getContact)
                    .thenComparing(ClientAccount::getAddress)
                    .compare(this,o);
            return ret;
        }
    }




}
