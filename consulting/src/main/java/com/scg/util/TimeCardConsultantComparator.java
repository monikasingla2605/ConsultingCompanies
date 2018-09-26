package com.scg.util;

import java.util.Comparator;

import com.scg.domain.TimeCard;

/**
 * This class compares timecards for consultant
 * @author Monikas
 *
 */
public class TimeCardConsultantComparator implements Comparator<TimeCard>
{

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(TimeCard card1, TimeCard card2)
    {
        if(card1 == null || card2 == null)
            throw new NullPointerException();
        else
        {
            int result = Comparator.comparing(TimeCard::getConsultant)
                    .thenComparing(TimeCard::getWeekStartingDate)
                    .thenComparing(TimeCard::getTotalBillableHours)
                    .thenComparing(TimeCard::getTotalNonBillableHours)
                    .compare(card1,card2);
       
                return result;
            
                    
        }
    }

}
