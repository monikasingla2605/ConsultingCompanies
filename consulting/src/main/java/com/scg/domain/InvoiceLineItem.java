package com.scg.domain;

import java.time.LocalDate;

/**
 * Class has the properties of a consultant for invoice report
 * @author Monika
 *
 */
public class InvoiceLineItem
{
    private LocalDate date;
    private Consultant consultant; 
    private Skill skill;
    private int hours;

    /**
     * Instantiates a new InvoiceLineItem 
     * @param date Date of the charge
     * @param consultant Details of the consultant
     * @param skill Skill of the consultant
     * @param hours Hours being billed
     */
    public InvoiceLineItem(LocalDate date, Consultant consultant, 
            Skill skill, int hours)
    {
        this.date = date;
        this.consultant = consultant;
        this.skill = skill;
        this.hours = hours;

    }

    /**
     * Gets the date of the charge
     * @return Date of the charge
     */
    public LocalDate getDate()
    {
        return date;
    }


    /**
     * Consultant who is responsible for charge
     * @return Consultant of type class Consultant who is responsible for charge
     */
    public Consultant getConsultant()
    {
        return consultant;
    }


    /**
     * Gets the skill of consultant
     * @return Skill of consultant
     */
    public Skill getSkill()
    {
        return skill;
    }


    /**
     * Gets the hours to be billed
     * @return Hours to be billed
     */
    public int getHours()
    {
        return hours;
    }

    /**
     * Charge of the consultant for his skill
     * @return Charge property according to the skill of consultant defined in the skill enum.
     */
    public int getCharge()
    {
        int charge = skill.getRate()*hours;
        return charge; 
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuilder bldr = new StringBuilder("");
        bldr.append("Consultant: ").append(consultant);
        bldr.append(System.lineSeparator());
        bldr.append("Date = ").append(date);
        bldr.append(System.lineSeparator());
        bldr.append("Skill = ").append(skill);
        bldr.append(System.lineSeparator());
        bldr.append("hours = ").append(hours);
        return bldr.toString();
    }

}
