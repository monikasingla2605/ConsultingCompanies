package com.scg.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;

import com.scg.domain.*;

/**
 * This class defines list of time card a consultant worked on a particular week
 * @author Monika
 *
 */
public class TimeCard implements Comparable<TimeCard>, Serializable
{
    private LocalDate weekStartingDate;
    private Consultant consultant;
    private List<ConsultantTime> list;

    /**
     * Instantiates a new class
     * @param consultant Consultant whose time card is.
     * @param weekStartingDate Starting day of the week this activity is from.
     */
    public TimeCard(Consultant consultant, LocalDate weekStartingDate)
    {
        this.consultant = consultant;
        this.weekStartingDate = weekStartingDate;
        list = new LinkedList<>();
    }

    /**
     * Gets the consultant whose time card is
     * @return Returns consultant of the class type <em> Consultant. </em>
     */
    public Consultant getConsultant()
    {
        return this.consultant;
    }

    /**
     * Gets the week starting date for which consultant worked
     * @return Returns the starting date of the week for which consultant worked
     */
    public LocalDate getWeekStartingDate()
    {
        return this.weekStartingDate;
    }
    
    /**
     * gets the consulting hours
     * @return List of hours for every client the consultant worked
     */
    public List<ConsultantTime> getConsultingHours()
    {
        List<ConsultantTime> list_ct = new LinkedList<>();
       list.forEach(p->list_ct.add(new ConsultantTime
               (p.getDate(), p.getAccount(), p.getSkill(), p.getHours())));
       
        return list_ct;
        
    }

    /**
     * Gets the billable hours for a particular account.
     * @param clientName Name of the account for which consultant worked
     * @return List of consultant time for which consultant worked
     */
    public List<ConsultantTime> getBillableHoursForClient(String clientName)
    {
        List<ConsultantTime> listnew = new LinkedList<>();
        for(ConsultantTime time: list)
        {
            if((time.getAccount().isBillable() == true) && 
                    (time.getAccount().getName().equals(clientName))) 
            {
                listnew.add(time);
            }
        }
        return listnew;

    }

    /**
     * Adds the time for which consultant worked
     * @param time Time for which consultant worked. It is of the type ConsultantTime class.
     */
    public void addConsultantTime(ConsultantTime time)
    {
        //if(!(list.contains(time)))
        list.add(time);

    }

    /**
     * gets the totalBillableHours for the consultant
     * @return Total billable hours for the consultant.
     */
    public int getTotalBillableHours()
    {
        int hours = 0;
        for(ConsultantTime time: list)
        {
            if (time.getAccount().isBillable() == true)
            {
                hours += time.getHours();
            }
        }
        return hours;
    }

    /**
     * Gets the total non billable hours for the consultant.
     * @return Total non billable hours for the consultant.
     */
    public int getTotalNonBillableHours()
    {
        int hours = 0;
        for(ConsultantTime time: list)
        {
            if (time.getAccount().isBillable() == false)
            {
                hours += time.getHours();
            }
        }
        return hours;
    }

    /**
     * Gets the total hours including billable and non billable.
     * @return Total hours for the consultant
     */
    public int getTotalHours()
    {
        int hours = 0;
        for(ConsultantTime time: list)
        {
            hours += time.getHours();
        }
        return hours;
    }


    /**
     * Creates a report for the consultant on which accounts he worked.
     * @return Returns the report in string form.
     */
    public String toReportString()
    {
        StringBuilder bldr = new StringBuilder("");
        bldr.append(drawLine('=',71));
        bldr.append(System.lineSeparator());
        String name = consultant.getName().toString();
        if(consultant.getName().toString().length()>18)
        {
            name = name.substring(0, 18);

        }
        String fmt = String.format("Consultant: %-32s", name);
        bldr.append(fmt);
        fmt = String.format("Week Starting: %tb %td, %tY%n", 
                weekStartingDate,weekStartingDate,weekStartingDate);
        bldr.append(fmt).append(System.lineSeparator());
        bldr.append(timeString(true));
        bldr.append(System.lineSeparator());
        bldr.append(timeString(false));
        bldr.append(System.lineSeparator());
        bldr.append("Summary: ").append(System.lineSeparator());
        fmt = String.format("%-40s", "Total Billable:");
        bldr.append(fmt);
        fmt = String.format("%9d", getTotalBillableHours());
        bldr.append(fmt).append(System.lineSeparator());
        fmt = String.format("%-40s", "Total Non-Billable:");
        bldr.append(fmt);
        fmt = String.format("%9d", getTotalNonBillableHours());
        bldr.append(fmt).append(System.lineSeparator());
        fmt = String.format("%-40s", "Total Hours:");
        bldr.append(fmt);
        fmt = String.format("%9d", getTotalHours());
        bldr.append(fmt).append(System.lineSeparator());
        bldr.append(drawLine('=',71));
        fmt = bldr.toString();
        return fmt;
    }

    private String timeString(boolean isBill)
    {
        StringBuilder bldr = new StringBuilder("");
        String str;
        if(isBill == true)
        {
            bldr.append("Billable Time: ");
            bldr.append(System.lineSeparator());
            str = String.format("%-30s  %-10s  %-5s  %-20s", "Account","Date", "Hours", "Skill");
            bldr.append(str).append(System.lineSeparator());
            bldr.append(drawLine('-',30)).append("  ");
            bldr.append(drawLine('-',10)).append("  ");
            bldr.append(drawLine('-',5)).append("  ");
            bldr.append(drawLine('-',20)).append("  ");
            bldr.append(System.lineSeparator());
        }
        else
        {
            bldr.append("Non-Billable Time: ");
            bldr.append(System.lineSeparator());
            str = String.format("%-30s  %-10s  %-5s", "Account","Date", "Hours");
            bldr.append(str).append(System.lineSeparator());
            bldr.append(drawLine('-',30)).append("  ");
            bldr.append(drawLine('-',10)).append("  ");
            bldr.append(drawLine('-',5)).append("  ");
            //bldr.append(drawLine(20)).append("  ");
            bldr.append(System.lineSeparator());
        }
        for(ConsultantTime time:list)
        {
            if(isBill == true)
            {
                if(time.getAccount().isBillable() == true)
                {
                    str = time.getAccount().getName();
                    if(str.length()>30)
                        str = str.substring(0, 29);
                    str = String.format("%-30s",str);
                    bldr.append(str).append("  ");
                    str = String.format("%tY-%tm-%td",
                            time.getDate(),time.getDate(),time.getDate());
                    bldr.append(str).append("  ");
                    str = String.format("%5d", time.getHours());
                    bldr.append(str).append("  ");
                    str = String.format("%-20s", time.getSkill());
                    bldr.append(str).append(System.lineSeparator());
                }
            }
            else
            {
                if(time.getAccount().isBillable() == false)
                {
                    str = time.getAccount().getName();
                    //if(str.length()>30)
                    //str = str.substring(0, 29);
                    str = String.format("%-30s",time.getAccount().getName());
                    bldr.append(str).append("  ");
                    str = String.format("%tY-%tm-%td",
                            time.getDate(),time.getDate(),time.getDate());
                    bldr.append(str).append("  ");
                    str = String.format("%5d", time.getHours());
                    bldr.append(str);
                    bldr.append(System.lineSeparator());
                }
            }
            //bldr.append(System.lineSeparator());
        }
        return bldr.toString();        
    }


    private String drawLine(char c, int len)
    {
        StringBuilder bldr = new StringBuilder("");
        for(int i = 0;i<len;i++)
        {
            bldr.append(c);
        }
        return bldr.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()    
    {
        StringBuilder bldr = new StringBuilder();
        bldr.append(consultant.toString());
        String str = String.format("%tY-%tm-%td", 
                weekStartingDate, weekStartingDate, weekStartingDate);
        bldr.append(" ").append(str);
        return bldr.toString();

    }

    @Override
    public int compareTo(TimeCard o)
    {
        if( o == null)
            throw new NullPointerException();
        else
        {
            int ret = Comparator.comparing(TimeCard::getConsultant)
                    .thenComparing(TimeCard::getWeekStartingDate)
                    .thenComparing(TimeCard::getTotalBillableHours)
                    .thenComparing(TimeCard::getTotalNonBillableHours)
                    .compare(this,o);

            return ret;
        }  
    }

}
