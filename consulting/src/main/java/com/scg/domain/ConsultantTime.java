package com.scg.domain;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.util.PersonalName;



/**
 * This class defines the how many hours a consultant worked for any account
 * @author Monika
 *
 */
public class ConsultantTime implements Serializable
{
    private LocalDate date;
    private Account account;
    private Skill skill;
    private int hours;

    /**
     * Instantiates a new class.
     * @param date Date on which consultant worked.
     * @param account Account for which consultant worked.
     * @param skill Skills of the consultant.
     * @param hours Hours for which consultant worked.
     * @throws IllegalArgumentException If the time is less than or equal to 0 then 
     * this exception is thrown.
     */
    public ConsultantTime(LocalDate date, Account account, Skill skill, int hours)
            throws IllegalArgumentException
    {
        setDate(date);
        setAccount(account);
        setSkill(skill);
        if(hours>0)
            this.hours = hours;
        else
            throw new IllegalArgumentException();
    
    }
    
    /**
     * Gets the date
     * @return Returns date in the LocalDate format
     */
    public LocalDate getDate()
    {
        return date;
    }

    /**
     * Sets the date for which consultant worked.
     * @param date Date in LocalDate format on which consultant worked.
     */
    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    /**
     * Gets the account for which consultant worked.
     * @return Account for which consultant worked.
     */
    public Account getAccount()
    {
        return account;
    }

    /**
     * Sets the account for which consultant worked.
     * @param account Account for which consultant worked.
     */
    public void setAccount(Account account)
    {
        this.account = account;
    }

    /**
     * Gets the skill of Consultant
     * @return Skill of the consultant. Its of the type enum.
     */
    public Skill getSkill()
    {
        return skill;
    }

    /**
     * Sets the skill of the consultant
     * @param skill Skill of the consultant. It is of the enum type skill
     */
    public void setSkill(Skill skill)
    {
        if(account.isBillable() == false)
        {
            skill = Skill.UNKNOWN_SKILL;
        }
        else
            this.skill = skill;
    }

    /**
     * Gets hours for which consutlant worked.
     * @return Hours for which consultant worked.
     */
    public int getHours()
    {
        return hours;
    }

    /**
     * Sets the hours for which consultant worked.
     * @param hours Hours for which consultant worked.
     */
    public void setHours(int hours)
    {
        if(hours>0)
            this.hours = hours;
        else
            throw new IllegalArgumentException();
    }

    /**
     * Checks if the account is billable or not.
     * @return True if account is billable and false if not.
     */
    public boolean isBillable()
    {
        return this.account.isBillable();
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
        else if (!(obj instanceof ConsultantTime))
            return false;
        else
        {
            ConsultantTime that = (ConsultantTime)obj;
            return Objects.equals(this.date, that.date) &&
                    Objects.equals(this.skill, that.skill) &&
                    Objects.equals(this.account, that.account) &&
                    Objects.equals(this.hours, that.hours);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int hash = Objects.hash(this.date, this.account, this.skill, this.hours);
        return hash;    
    }
}
