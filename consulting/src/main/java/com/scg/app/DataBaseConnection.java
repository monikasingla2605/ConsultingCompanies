package com.scg.app;

import java.sql.SQLException;
import java.time.Month;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Invoice;
import com.scg.persistent.DbServer;

/**
 * To print the invoices
 * @author Monika
 *
 */
public class DataBaseConnection {
    
    private static Month month = Month.MAY;

    private static  int year = 2018;
    
    private static String dbUrl = "jdbc:derby://localhost:1527/memory:scgDb";
    private static String username = "student";
    private static String password = "student";
    
    /**
     * main method
     * @param args main method args
     */
    public static void main(String[] args) {
        
        DbServer db = new DbServer(dbUrl,username,password);
        
        try 
        {
            List<ClientAccount> list = db.getClients();
            
            for(ClientAccount client : list){
                Invoice invoice = db.getInvoice(client, month , year);
                System.out.println(invoice.toReportString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}