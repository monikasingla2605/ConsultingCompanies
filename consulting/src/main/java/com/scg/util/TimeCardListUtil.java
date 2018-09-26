package com.scg.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;



/**
 * This class is utility for timecards
 * @author Monika
 *
 */
public class TimeCardListUtil
{

    /**
     * Gets the list of time cards for a particular consultant
     * @param timeCards list of timecards
     * @param consultant consultant for which timecard are to be found.
     * @return list of timecards for the particular consultant.
     */
    public static List<TimeCard> getTimeCardsForConsultant(List<TimeCard> timeCards, 
            Consultant consultant)
    {
        List<TimeCard> list = new LinkedList<>();
        timeCards.stream()
        .filter(p->p.getConsultant().getName().equals(consultant.getName()))
        .forEach(list::add);
        return list;
    }

    /**
     * Get time cards in the particular time range
     * @param timeCards list of timecards
     * @param dateRange date rnage in which timecard should be
     * @return returns the list of timecard in the given range
     */
    public static List<TimeCard> 
    getTimeCardsForDateRange(List<TimeCard> timeCards, DateRange dateRange)
    {
        List<TimeCard> list = new LinkedList<>();
        timeCards.stream()
       .filter(p->dateRange.isInRange(p.getWeekStartingDate()))
       .forEach(list::add);
        return list;

    }

    /**
     * sorts the list by conultant name
     * @param timeCards timecard list to be sorted
     */
    public static void sortByConsultantName( List<TimeCard> timeCards )
    {
        Collections.sort(timeCards, Comparator.comparing(TimeCard::getConsultant));
    }

    /**
     * sorts the timecard list according to start date
     * @param timeCards timecard list to be sorted
     */
    public static void sortByStartDate( List<TimeCard> timeCards )
    {
        Collections.sort(timeCards, Comparator.comparing(TimeCard:: getWeekStartingDate));
    }

}
