package com.scg.persistent;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

import com.scg.domain.Account;
import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.ConsultantTime;
import com.scg.domain.Invoice;
import com.scg.domain.InvoiceLineItem;
import com.scg.domain.NonBillableAccount;
import com.scg.domain.Skill;
import com.scg.domain.TimeCard;
import com.scg.util.Address;
import com.scg.util.DateRange;
import com.scg.util.PersonalName;
import com.scg.util.StateCode;

/**
 * This class will create database and maintain it.
 * @author Monika
 *
 */
public class DbServer
{
    private final String dbURL;
    private final String dbUserName;
    private final String dbPassword;
    private final String clients_tbl = "CLIENTS";
    private final String nba_tbl = "NON_BILLABLE_ACCOUNTS";
    private final String skills_tbl = "SKILLS";
    private final String consultants_tbl = "CONSULTANTS";
    private final String timecards_tbl = "TIMECARDS";
    private final String bh_tbl = "BILLABLE_HOURS";
    private final String nbh_tbl = "NON_BILLABLE_HOURS";

    private final String insert_clients_tbl =  "INSERT INTO "+ clients_tbl +"("
            + "NAME, STREET, CITY, STATE, POSTAL_CODE,  "
            + "CONTACT_LAST_NAME, CONTACT_FIRST_NAME, CONTACT_MIDDLE_NAME)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private final String select_clients_tbl =  
            "SELECT NAME, STREET, CITY, STATE, POSTAL_CODE,  "
                    + "CONTACT_LAST_NAME, CONTACT_FIRST_NAME, CONTACT_MIDDLE_NAME"
                    + " FROM "+clients_tbl;

    private final String insert_consultants_tbl = "INSERT INTO " + consultants_tbl +"("
            + "LAST_NAME, FIRST_NAME, MIDDLE_NAME)"
            + "VALUES (?, ?, ?)";

    private final String select_consultants_tbl = "SELECT LAST_NAME, FIRST_NAME, MIDDLE_NAME"
            + " FROM " +consultants_tbl;


    private final String insert_timecards_tbl = "INSERT INTO " + timecards_tbl +"("
            + "CONSULTANT_ID, START_DATE)"
            + "VALUES (?, ?)";

    private final String select_timecards_tbl = "SELECT ID, CONSULTANT_ID, START_DATE"
            + " FROM " +timecards_tbl;

    private final String insert_bh_tbl = "INSERT INTO " + bh_tbl +" ("
            + "CLIENT_ID, TIMECARD_ID, DATE, SKILL, HOURS) "
            + "VALUES ((SELECT DISTINCT ID FROM "+clients_tbl
            + " WHERE NAME = ?),?,?,?,?)";

    private final String insert_nbh_tbl = "INSERT INTO " + nbh_tbl +"("
            + "ACCOUNT_NAME, TIMECARD_ID, DATE, HOURS)"
            + "VALUES (?,?,?,?)";

    /**
     * Instantiates a new class
     * @param dbURL url for the database
     * @param dbUserName Database name
     * @param dbPassword Database password
     */
    public DbServer( String dbURL, String dbUserName, String dbPassword )
    {
        this.dbURL = dbURL;
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
    }

    /**
     * Initiates a database connection and clears all tables before starting
     * @throws SQLException Throws exception if not able to establish connection.
     */
    public void initTables()
            throws SQLException
    {
        try(Connection conn = getConnection())
        {
            final String    truncate    = "DELETE FROM %s WHERE 1=1";
            final String[]  tables      = 
                {
                        nbh_tbl,
                        bh_tbl,
                        timecards_tbl,
                        clients_tbl,
                        consultants_tbl
                };
            Statement   statement   = conn.createStatement();
            for ( String table : tables )
                ( statement).executeUpdate( String.format( truncate, table ) );
                statement.close();
                conn.close();

        }



    }

    /**
     * Inserting client to the database
     * @param client Client to be added
     * @throws SQLException If connection can not be created
     */
    public void addClient(ClientAccount client)
            throws SQLException
    {
        try(Connection conn = getConnection())
        {
            //int flags = Statement.RETURN_GENERATED_KEYS;
            PreparedStatement statement  = 
                    conn.prepareStatement(insert_clients_tbl);
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress().getStreetNumber());
            statement.setString(3, client.getAddress().getCity());
            statement.setString(4, client.getAddress().getState().toString());
            statement.setString(5, client.getAddress().getPostalCode());
            statement.setString(6, client.getContact().getLastName());
            statement.setString(7, client.getContact().getFirstName());
            statement.setString(8, client.getContact().getMiddleName());
            statement.executeUpdate();
            if(statement!= null)
                statement.close();
            if(conn!= null)
                conn.close();

        }
    }

    private Connection getConnection()
            throws SQLException
    {
        Connection  conn    =
                DriverManager.getConnection( 
                        dbURL, dbUserName, dbPassword );
        return conn;
    }

    /**
     * Gets list of client in client table
     * @return list of all clients in clients table
     * @throws SQLException  If not able to create connection
     */
    public List<ClientAccount> getClients()
            throws SQLException
    {
        List<ClientAccount> list = new LinkedList<>();
        try(Connection conn = getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(select_clients_tbl);
            ResultSet result = statement.executeQuery();
            while(result.next())
            {
                String name  = result.getString("NAME");
                String street_number  = result.getString("STREET");
                String  city = result.getString("CITY");
                StateCode state = StateCode.valueOf(result.getString("STATE"));
                String postCode  = result.getString("POSTAL_CODE");
                String lName  = result.getString("CONTACT_LAST_NAME");
                String  fName = result.getString("CONTACT_FIRST_NAME");
                String  mName = result.getString("CONTACT_MIDDLE_NAME");
                Address add = new Address(city,postCode, state, street_number);
                PersonalName contact = new PersonalName(fName,lName,mName);
                list.add(new ClientAccount(name,contact,add));
            }
            result.close();
            conn.close();
        }
        return list;
    }

    /**
     * Adds consultant to the database
     * @param consultant consultant to be added
     * @throws SQLException throws exception if not able to open connection
     */
    public void addConsultant( Consultant consultant )
            throws SQLException
    {
        try(Connection conn = getConnection())
        {
            PreparedStatement statement  = 
                    conn.prepareStatement(insert_consultants_tbl);
            statement.setString(1, consultant.getName().getLastName());
            statement.setString(2, consultant.getName().getFirstName());
            statement.setString(3, consultant.getName().getMiddleName());
            statement.executeUpdate();
                statement.close();
                conn.close();

        }

    }

    /**
     * get consultants from consultants table
     * @return list of consultant from consultant table
     * @throws SQLException throws if not able to open connection
     */
    public List<Consultant> getConsultants()
            throws SQLException
    {
        List<Consultant> list = new LinkedList<>();
        try(Connection conn = getConnection())
        {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(select_consultants_tbl);
            while(result.next())
            {
                String  lName = result.getString("LAST_NAME");
                String fName  = result.getString("FIRST_NAME");
                String  mName = result.getString("MIDDLE_NAME");
                PersonalName contact = new PersonalName(lName,fName,mName);
                list.add(new Consultant(contact));
            }
            result.close();
            statement.close();
            conn.close();
        }
        return list;

    }

    /**
     * adds timecard to the table time_cards
     * @param timeCard timecard to be added to db
     * @throws SQLException throws if not able to open connection
     */
    public void addTimeCard( TimeCard timeCard )
            throws SQLException
    {
        List<ConsultantTime> list = timeCard.getConsultingHours();
        try(Connection conn = getConnection())
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ID FROM ").append(consultants_tbl);
            sb.append(" WHERE LAST_NAME = '");
            sb.append(timeCard.getConsultant().getName().getLastName()).toString();
            sb.append("' AND FIRST_NAME = '");
            sb.append(timeCard.getConsultant().getName().getFirstName()).toString();
            if(timeCard.getConsultant().getName().getMiddleName()!= null)
            {
                sb.append("' AND MIDDLE_NAME = '");
                sb.append(timeCard.getConsultant().getName().getMiddleName()).toString();
                sb.append("'");
            }
            else
            {
                sb.append("'");
            }
            System.out.println(sb.toString());
            Statement statement = conn.createStatement();
            String sql = sb.toString();
            ResultSet result = statement.executeQuery(sql);

            int consultant_id = 0;
            if(result.next())
                consultant_id = result.getInt("ID");
            result.close();
            statement.close();

            int flag = Statement.RETURN_GENERATED_KEYS;
            PreparedStatement ps_timecard = conn.prepareStatement(insert_timecards_tbl,flag);
            Date date = Date.valueOf(timeCard.getWeekStartingDate());

            ps_timecard.setInt(1, consultant_id);
            ps_timecard.setDate(2, date);
            ps_timecard.executeUpdate();
            ResultSet rs_timecard = ps_timecard.getGeneratedKeys();
            int timecard_id = 0;
            if(rs_timecard.next())
                timecard_id = rs_timecard.getInt(1);
            System.out.println("time_card id -> "+timecard_id);
            System.out.println(insert_bh_tbl);
            System.out.println(insert_nbh_tbl);
            PreparedStatement ps_bh = conn.prepareStatement(insert_bh_tbl);
            PreparedStatement ps_nbh = conn.prepareStatement(insert_nbh_tbl);
            for(ConsultantTime ct:list)
            {
                if(ct.isBillable() == true)
                {
                    String clientName = ct.getAccount().getName();
                    String skill = ct.getSkill().name();
                    Date date_ct = Date.valueOf(ct.getDate());
                    ps_bh.setString(1, clientName);
                    ps_bh.setInt(2, timecard_id);
                    ps_bh.setDate(3, date_ct);
                    //ps_bh.setString(4, ct.getSkill().toString());
                    ps_bh.setString(4, skill);
                    ps_bh.setInt(5, ct.getHours());
                    ps_bh.executeUpdate();

                }
                else
                {
                    String name =  ct.getAccount().getName();
                    if(name.equals("Vacation"))
                        name = "VACATION";
                    else if(name.equals("Business Development"))
                        name = "BUSINESS_DEVELOPMENT";
                    else
                        name = "SICK_LEAVE";
                    System.out.println(name);
                   Date date_ct = Date.valueOf(ct.getDate());
                   ps_nbh.setString(1, name);
                   ps_nbh.setInt(2, timecard_id);
                    ps_nbh.setDate(3, date_ct);
                    ps_nbh.setInt(4, ct.getHours());
                    ps_nbh.executeUpdate();

                }
            }
            ps_bh.close();
            ps_nbh.close();
            ps_timecard.close();
            rs_timecard.close();


        }
    }

    /**
     * Gets time cards
     * @return returns list of timecards from table time_cards
     * @throws SQLException if not able to open the connection
     */
    public List<TimeCard> getTimeCards()
            throws SQLException
    {
        List<TimeCard> list = new LinkedList<>();
        try(Connection conn = getConnection())
        {
            System.out.println(select_timecards_tbl);
            PreparedStatement ps = conn.prepareStatement(select_timecards_tbl);
            ResultSet result = ps.executeQuery();
            while(result.next())
            {
                int tc_id = result.getInt("ID");
                int consultant_id = result.getInt("CONSULTANT_ID");
                LocalDate date  = result.getDate("START_DATE").toLocalDate();
                final String sql = "SELECT LAST_NAME, FIRST_NAME, MIDDLE_NAME FROM "
                        +consultants_tbl+" WHERE ID = "+consultant_id;
                Statement statement = conn.createStatement();
                ResultSet res = statement.executeQuery(sql);
                Consultant cons = null;
                while(res.next())
                {
                    PersonalName name = new PersonalName(
                            res.getString(1), res.getString(2),res.getString(3));
                    cons = new Consultant(name);
                }
                res.close();
                statement.close();
                TimeCard tc = new TimeCard(cons,date);
                list.add(tc);
                PreparedStatement ps_bh = conn.prepareStatement(
                        "SELECT bh.DATE, "
                                + "bh.SKILL,"
                                + "bh.HOURS,"
                                + "cl.CONTACT_LAST_NAME,"
                                + "cl.CONTACT_FIRST_NAME,"
                                + "cl.CONTACT_MIDDLE_NAME,"
                                + "cl.STREET,"
                                + "cl.CITY,"
                                + "cl.STATE,"
                                + "cl.POSTAL_CODE,"
                                + "cl.NAME"
                                + " FROM  BILLABLE_HOURS bh"
                                + " INNER JOIN TIMECARDS tc ON bh.TIMECARD_ID = tc.ID"
                                + " INNER JOIN CLIENTS cl ON bh.CLIENT_ID = cl.ID "
                                + " WHERE tc.ID = ?");
                ps_bh.setInt(1, tc_id );
                ResultSet r = ps_bh.executeQuery();
                while(r.next())
                {
                    PersonalName cl_name = new PersonalName(r.getString(4)
                            ,r.getString(5),r.getString(6));
                    String name = r.getString(11);
                    Address add = new Address(
                            r.getString(7),
                            r.getString(8),
                            StateCode.valueOf(r.getString(9)),
                            r.getString(9));
                    Account acc = new ClientAccount(name, cl_name,add);
                    ConsultantTime ct = new ConsultantTime(r.getDate(1).toLocalDate(),
                            acc, Skill.valueOf(r.getString(2)),
                            r.getInt(3));
                    tc.addConsultantTime(ct);
                }
                r.close();
                ps_bh.close();
                
                
                PreparedStatement ps_nbh = conn.prepareStatement(
                        "SELECT nbh.DATE, "
                                + "nbh.HOURS,"
                                + " nbh.ACCOUNT_NAME"
                                + " FROM  NON_BILLABLE_HOURS nbh"
                                + " INNER JOIN TIMECARDS tc ON nbh.TIMECARD_ID = tc.ID"
                                + " WHERE tc.ID = ?");
                ps_nbh.setInt(1, tc_id );
                ResultSet rs_nb = ps_nbh.executeQuery();
                while(rs_nb.next())
                {
                    Account acc_nba = NonBillableAccount.valueOf(rs_nb.getString(3));
                    ConsultantTime ct = new ConsultantTime
                            (rs_nb.getDate(1).toLocalDate(),
                            acc_nba,
                            Skill.UNKNOWN_SKILL,
                            rs_nb.getInt(2)
                            );
                    tc.addConsultantTime(ct);
                }
                rs_nb.close();
                ps_nbh.close();
            }
            result.close();
            ps.close();
            conn.close();
        }
        return list;


    }

    /**
     * Gets the invoice
     * @param client client whose invoice is generated
     * @param month month for which invoice is generated
     * @param year year for which it is generated
     * @return Invoice for the client
     * @throws SQLException if not able to open the connection
     */
    public Invoice getInvoice( ClientAccount client, Month month, int year )
            throws SQLException
    {
        Invoice invoice = new Invoice(client,month,year);
        try(Connection conn = getConnection())
        {
            final String sql = "SELECT ID FROM "+clients_tbl+
                    " WHERE NAME = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, client.getName());
            ResultSet result = statement.executeQuery();
            int client_id = 0;
            if(result.next())
                client_id = result.getInt("ID");

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT bh.DATE,cons.LAST_NAME,"
                            + "cons.FIRST_NAME,cons.MIDDLE_NAME,"
                            + "bh.SKILL,bh.HOURS"
                            + " FROM BILLABLE_HOURS bh"
                            + " INNER JOIN TIMECARDS tc ON bh.TIMECARD_ID = tc.ID"
                            + " INNER JOIN CONSULTANTS cons on tc.CONSULTANT_ID = cons.ID"
                            + " where bh.CLIENT_ID = ?");

            ps.setInt(1, client_id);
            result = ps.executeQuery();
            if(result.next())
            {
                final LocalDate date = result.getDate(1).toLocalDate();
                final String lName = result.getString(2);
                final String fName = result.getString(3);
                final String mName = result.getString(4);
                final Skill skill = Skill.valueOf(result.getString(5));
                final int hours = result.getInt(6);
                final PersonalName name = new PersonalName(lName,fName, mName);
                final Consultant cons = new Consultant(name);
                final InvoiceLineItem item = new InvoiceLineItem(date, cons, skill,hours);
                invoice.addLineItem(item);
            }
            result.close();
            ps.close();
            statement.close();
            conn.close();
        }

        return invoice;

    }


}
