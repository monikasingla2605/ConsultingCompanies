package com.scg.util;

import java.time.LocalDate;
import java.time.Month;

/**
 * This class encapsulates the date proerties
 * @author Monikas
 *
 */
public class DateRange
{
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Instantiates a new class
     * @param month month for start and end both
     * @param year year for start and end both
     */
    public DateRange(Month month, int year)
    {
        this(LocalDate.of(year, month, 1), LocalDate.of(year, month, month.length(true)));
    }

    /**
     * instantiate a new date range class
     * @param start start date for the range
     * @param end end date for the range
     */
    public DateRange(String start, String end)
    {
        this(
                LocalDate.of(Integer.parseInt(start.substring(0,4)),
                        Integer.parseInt(start.substring(5,7)),
                        Integer.parseInt(start.substring(8,start.length()))),
                LocalDate.of(Integer.parseInt(end.substring(0,4)),
                        Integer.parseInt(end.substring(5,7)),
                        Integer.parseInt(end.substring(8,end.length()))));
    }

    /**
     * Instantiates a new date
     * @param startDate startdate for the range
     * @param endDate end date for the range
     */
    public DateRange(LocalDate startDate, LocalDate endDate)
    {
        this.startDate = startDate;
        this.endDate = endDate;

    }


    /**
     * Getter for the start date
     * @return Returns the start date
     */
    public LocalDate getStartDate()
    {
        return startDate;
    }

    /**
     * Getter for the end date.
     * @return Returns end date of the range
     */
    public LocalDate getEndDate()
    {
        return endDate;
    }

    /**
     * checks if a date is in range or not
     * @param date date to be checked
     * @return true or false if the given date is in range or not
     */
    public boolean isInRange(LocalDate date)
    {
        return (date.isEqual(startDate) || date.isEqual(endDate) || 
                (date.isAfter(startDate) && date.isBefore(endDate)));
        //        if(date.isEqual(startDate) || date.isEqual(endDate))
        //            return true;
        //        else if (date.isAfter(startDate) && date.isBefore(endDate))
        //            return true;
        //        else
        //            return false;

    }


}
