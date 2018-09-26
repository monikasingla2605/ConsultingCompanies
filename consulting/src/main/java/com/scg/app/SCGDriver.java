package com.scg.app;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import com.scg.domain.Account;
import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.ConsultantTime;
import com.scg.domain.Invoice;
import com.scg.domain.NonBillableAccount;
import com.scg.domain.Skill;
import com.scg.domain.TimeCard;
import com.scg.util.Address;
import com.scg.util.PersonalName;
import com.scg.util.StateCode;

/**
 *                  Mon       Tue       Wed       Thu       Fri
 *                -------   -------   -------   -------   -------
 * consultant 1:   V,8      C1,SE,2   C1,SE,3   C1,SE,2   C1,SE,5
 *                          C2,PM,5   C2,PM,4   C2,PM,5   C2,PM,2
 *                          BD,1      BD,1      BD,1      BD,1
 *                          
 * consultant 2:  C1,ST,4   C1,ST,2             C1,ST,4   
 *                C2,ST,4   C2,ST,6   SL,8      C2,ST,4   C2,ST,8
 *                
 * consultant3:   C1,SA,8   C1,SA,7   C1,SA,4   BD,8      BD,8
 *                          C2,SA,1   C2,SA,4
 * @author jack
 */
public class SCGDriver
{
    private final   Consultant      consultant1;
    private final   Consultant      consultant2;
    private final   Consultant      consultant3;
    
    private final   TimeCard        timeCard1;
    private final   TimeCard        timeCard2;
    private final   TimeCard        timeCard3;
    
    private final   ClientAccount   client1;
    private final   ClientAccount   client2;
    
    private static final Month      thisMonth   = Month.MAY;
    private static final int        thisYear    = 2018;
    private static final LocalDate  thisDate    = 
        LocalDate.of( thisYear, thisMonth, 1 );
    public static final LocalDate   baseDate    = thisDate.minusDays( 2 );
    
    public static void main(String[] args)
    {
        new SCGDriver().execute();
    }
    
    public SCGDriver()
    {
        consultant1 = new Consultant( new UniquePersonalName() );
        consultant2 = new Consultant( new UniquePersonalName() );
        consultant3 = new Consultant( new UniquePersonalName() );
        
        client1 = new UniqueClient();
        client2 = new UniqueClient();
        
        timeCard1 = new TimeCard( consultant1, baseDate );
        timeCard2 = new TimeCard( consultant2, baseDate );
        timeCard3 = new TimeCard( consultant3, baseDate );
        
        recordHoursConsultant1();
        recordHoursConsultant2();
        recordHoursConsultant3();
    }
    
    public Month getMonth()
    {
        return thisMonth;
    }
    
    public int getYear()
    {
        return thisYear;
    }
    
    public List<TimeCard> getTimeCards()
    {
    	List<TimeCard> list    = new ArrayList<>();
        list.add( timeCard1 );
        list.add( timeCard2 );
        list.add( timeCard3 );
        return list;
    }
    
    public List<ClientAccount> getClientAccounts()
    {
        List<ClientAccount> list    = new ArrayList<>();
        list.add( client1 );
        list.add( client2 );
        return list;
    }
    
    public List<Consultant> getConsultants()
    {
        List<Consultant>    list    = new ArrayList<>();
        list.add( consultant1 );
        list.add( consultant2 );
        list.add( consultant3 );
        return list;
    }
    
    private void execute()
    {
        System.out.println( "          ********************" );
        System.out.println( "          **** Time Cards ****" );
        System.out.println( "          ********************" );
        for ( TimeCard card : new TimeCard[] { timeCard1, timeCard2, timeCard3 } )
        {
            String  str = card.toReportString();
            System.out.println( str );
        }

        System.out.println();
        System.out.println( "          ********************" );
        System.out.println( "          ***** Invoices *****" );
        System.out.println( "          ********************" );
        for ( ClientAccount client : new ClientAccount[] { client1, client2 } )
        {
            Invoice invoice = new Invoice( client, thisMonth, thisYear );
            invoice.extractLineItems( timeCard1 );
            invoice.extractLineItems( timeCard2 );
            invoice.extractLineItems( timeCard3 );
            String  report  = invoice.toReportString();
            System.out.println( report );
        }
    }
    
    private void recordHoursConsultant1()
    {
        ConsultantTime  cTime;
        Account         account;
        LocalDate       date;
        Skill           skill;
        
        // Mon, Vacation
        date = baseDate;
        account = NonBillableAccount.VACATION;
        skill = Skill.UNKNOWN_SKILL;
        cTime = new ConsultantTime( date, account, skill, 8 );
        timeCard1.addConsultantTime( cTime );
        
        // 1 hour business development Tue - Fri
        for ( int inx = 1 ; inx < 5 ; ++inx )
        {
            date = baseDate.plusDays( inx );
            account = NonBillableAccount.BUSINESS_DEVELOPMENT;
            cTime = new ConsultantTime( date, account, skill, 1 );
            timeCard1.addConsultantTime( cTime );
        }
        
        // Tue
        date = baseDate.plusDays( 1 );
        account = client1;
        skill = Skill.SOFTWARE_ENGINEER;
        cTime = new ConsultantTime( date, account, skill, 2 );
        timeCard1.addConsultantTime( cTime );
        
        account = client2;
        skill = Skill.PROJECT_MANAGER;
        cTime = new ConsultantTime( date, account, skill, 5 );
        timeCard1.addConsultantTime( cTime );
        
        // Wed
        date = baseDate.plusDays( 2 );
        account = client1;
        skill = Skill.SOFTWARE_ENGINEER;
        cTime = new ConsultantTime( date, account, skill, 3 );
        timeCard1.addConsultantTime( cTime );

        account = client2;
        skill = Skill.PROJECT_MANAGER;
        cTime = new ConsultantTime( date, account, skill, 4 );
        timeCard1.addConsultantTime( cTime );
        
        // Thu
        date = baseDate.plusDays( 3 );
        account = client1;
        skill = Skill.SOFTWARE_ENGINEER;
        cTime = new ConsultantTime( date, account, skill, 2 );
        timeCard1.addConsultantTime( cTime );

        account = client2;
        skill = Skill.PROJECT_MANAGER;
        cTime = new ConsultantTime( date, account, skill, 5 );
        timeCard1.addConsultantTime( cTime );
        
        // Fri
        date = baseDate.plusDays( 4 );
        account = client1;
        skill = Skill.SOFTWARE_ENGINEER;
        cTime = new ConsultantTime( date, account, skill, 5 );
        timeCard1.addConsultantTime( cTime );

        account = client2;
        skill = Skill.PROJECT_MANAGER;
        cTime = new ConsultantTime( date, account, skill, 2 );
        timeCard1.addConsultantTime( cTime );
    }

    private void recordHoursConsultant2()
    {
        ConsultantTime  cTime;
        Account         account;
        LocalDate       date;
        Skill           skill;

        // Mon
        date = baseDate;
        account = client1;
        skill = Skill.SOFTWARE_TESTER;
        cTime = new ConsultantTime( date, account, skill, 4 );
        timeCard2.addConsultantTime( cTime );
        
        account = client2;
        cTime = new ConsultantTime( date, account, skill, 4 );
        timeCard2.addConsultantTime( cTime );

        // Tue
        date = baseDate.plusDays( 1 );
        account = client1;
        skill = Skill.SOFTWARE_TESTER;
        cTime = new ConsultantTime( date, account, skill, 2 );
        timeCard2.addConsultantTime( cTime );
        
        account = client2;
        cTime = new ConsultantTime( date, account, skill, 6 );
        timeCard2.addConsultantTime( cTime );

        // Wed
        date = baseDate.plusDays( 2 );
        account = NonBillableAccount.SICK_LEAVE;
        skill = Skill.UNKNOWN_SKILL;
        cTime = new ConsultantTime( date, account, skill, 8 );
        timeCard2.addConsultantTime( cTime );

        // Thu
        date = baseDate.plusDays( 3 );
        account = client1;
        skill = Skill.SOFTWARE_TESTER;
        cTime = new ConsultantTime( date, account, skill, 4 );
        timeCard2.addConsultantTime( cTime );
        
        account = client2;
        cTime = new ConsultantTime( date, account, skill, 4 );
        timeCard2.addConsultantTime( cTime );

        // Fri
        date = baseDate.plusDays( 4 );
        account = client1;
        skill = Skill.SOFTWARE_TESTER;
        cTime = new ConsultantTime( date, account, skill, 8 );
        timeCard2.addConsultantTime( cTime );
    }

    private void recordHoursConsultant3()
    {
        ConsultantTime  cTime;
        Account         account;
        LocalDate       date;
        Skill           skill;

        // Mon
        date = baseDate;
        account = client1;
        skill = Skill.SYSTEM_ARCHITECT;
        cTime = new ConsultantTime( date, account, skill, 8 );
        timeCard3.addConsultantTime( cTime );

        // Tue
        date = baseDate.plusDays( 1 );
        account = client1;
        skill = Skill.SYSTEM_ARCHITECT;
        cTime = new ConsultantTime( date, account, skill, 7 );
        timeCard3.addConsultantTime( cTime );
        
        account = client2;
        cTime = new ConsultantTime( date, account, skill, 1 );
        timeCard3.addConsultantTime( cTime );

        // Wed
        date = baseDate.plusDays( 2 );
        account = client1;
        skill = Skill.SYSTEM_ARCHITECT;
        cTime = new ConsultantTime( date, account, skill, 4 );
        timeCard3.addConsultantTime( cTime );
        
        account = client2;
        cTime = new ConsultantTime( date, account, skill, 4 );
        timeCard3.addConsultantTime( cTime );

        // Thu
        date = baseDate.plusDays( 3 );
        account = NonBillableAccount.BUSINESS_DEVELOPMENT;
        skill = Skill.UNKNOWN_SKILL;
        cTime = new ConsultantTime( date, account, skill, 8 );
        timeCard3.addConsultantTime( cTime );

        // Fri
        date = baseDate.plusDays( 4 );
        account = NonBillableAccount.BUSINESS_DEVELOPMENT;
        skill = Skill.UNKNOWN_SKILL;
        cTime = new ConsultantTime( date, account, skill, 8 );
        timeCard3.addConsultantTime( cTime );
    }

    public static class UniquePersonalName extends PersonalName
    {
        private static final long serialVersionUID = 5583905142305077227L;
        private static final String[]   allFirstNames   = 
            { "Bob", "Sally", "Rhoda", "Fred", "Nancy", "Eddie" };
        private static final String[]   allLastNames    = 
            { "Taft", "Adams", "Harding", "Roosevelt", "Jackson", "Obama" };
        private static final String[]   allMiddleNames  = 
            { "Joyce", "Sam", "Max", "R.", "T.", "K." };
        
        private static int  next    = 0;
    
        public UniquePersonalName()
        {
            super(
                allLastNames[next % allLastNames.length],
                allFirstNames[next % allFirstNames.length],
                allMiddleNames[next % allMiddleNames.length]
            );
            ++next;
        }
        
        public static void reset()
        {
            next = 0;
        }
    }
    
    public static class UniqueClient extends ClientAccount
    {
        /**
         * 
         */
        private static final long serialVersionUID = 6417920328922464293L;

        private static final String[]   allNames    =
        {
            "Acme Widget Co.",
            "Pete's Party Stop",
            "Aunt Jenny's Cookies",
            "Charlie's Chum"
        };
        
        private static int  next    = 0;
        
        public UniqueClient()
        {
            super(
                allNames[next % allNames.length],
                new UniquePersonalName(),
                new UniqueAddress()
            );
            
            ++next;
        }
        
        public static void reset()
        {
            next = 0;
        }
    }
    
    public static class UniqueAddress extends Address
    {
        private static final long serialVersionUID = -2380139918970907460L;

        private static final String[]       allStreets  =
        {
            "1313 Mockingbird Ln",
            "221 B Baker St",
            "1600 Pennsylvania Ave NW",
            "350 Fifth Ave",
            "2311 Broadway Street",
            "105 Second Avenue"
        };
        
        private static final String[]       allCities   =
        {
            "New York",
            "Los Angeles",
            "Sioux Falls",
            "Omaha",
            "Whistler",
            "Sleepy Hollow"
        };
        
        private static final StateCode[]    allStates   = 
        {
            StateCode.WA, 
            StateCode.CA, 
            StateCode.NY, 
            StateCode.OR,
            StateCode.AZ,
            StateCode.ID
        };
        
        private static final String[]       allZips     =
            { "11111", "22222", "33333", "44444", "55555", "66666" };  
        
        private static int  next     = 0;
        
        public UniqueAddress()
        {
            super(
                allStreets[next % allStreets.length] + next,
                allCities[next % allCities.length] + next,
                allStates[next % allStates.length],
                allZips[next % allZips.length]
            );
            ++next;
        }
        
        public static void reset()
        {
            next = 0;
        }
    }
}
