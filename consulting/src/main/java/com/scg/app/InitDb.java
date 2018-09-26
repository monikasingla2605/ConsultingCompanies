package com.scg.app;
import java.sql.SQLException;
import java.time.Month;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.persistent.DbServer;
import com.scg.util.StateCode;

/**
 * To configure the database for printing invoices.  
 * @author monika
 *
 */
public class InitDb
{
    /**
     * main method
     * @param args main method args
     * @throws SQLException if can not create connection
     */
    public static void main(String[] args) throws SQLException
    {
        SCGDriver           driver       = new SCGDriver();

        List<Consultant>   consList = driver.getConsultants();
        List<TimeCard>      tcList = driver.getTimeCards();
        List<ClientAccount> clientList   = driver.getClientAccounts();
        Month month = driver.getMonth();
        int year =driver.getYear();

        String dbURL= "jdbc:derby://localhost:1527/memory:scgDb";
        String dbUserName="student";
        String dbPassword="student";

        DbServer db = new DbServer
                (dbURL, dbUserName, dbPassword);

        db.initTables();

        for (  Consultant cons: consList )
            db.addConsultant(cons);
        System.out.println(db.getConsultants());

        for (  ClientAccount client: clientList)
        {
            String test = client.getAddress().getState().name();
            StateCode.valueOf(test);
            db.addClient(client);
            
        }
        System.out.println(db.getClients());

        for (TimeCard timeCard: tcList)
        {
            db.addTimeCard(timeCard);
        }
        List<TimeCard> list = db.getTimeCards();
        for(TimeCard tc: list)
        {
            System.out.println(tc.toReportString());
        }

    }
}
