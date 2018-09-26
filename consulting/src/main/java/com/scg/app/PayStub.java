package com.scg.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.scg.domain.ClientAccount;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;

/**
 * This class will deserialize and then print the lists
 * @author Monika
 *
 */
public class PayStub implements Serializable
{
    /**
     * serialversion uid
     */
    private static final long serialVersionUID = 1L;
    public static final Month month = LocalDate.now().getMonth();
    public static final int year = LocalDate.now().getYear();
    /**
     * This method deserialize the object
     * @param fileName filename to be deserialized
     * @return Returns the deserialized object
     *   
     */
    public static Object deserialize(String fileName)
    {
        List<?> list = new LinkedList<Object>();
        try
        {
            FileInputStream inStream = new FileInputStream(fileName);
            ObjectInputStream oStream = new ObjectInputStream(inStream);
            list = (List<?>)oStream.readObject();
            oStream.close();
            inStream.close();
        }
        catch(Exception exc)
        {
            System.out.println("Exception");
            exc.printStackTrace();
        }

        return list;

    }

    private static void report(List<TimeCard> listTimeCard, List<ClientAccount> listClient)
    {
        for(ClientAccount listC:listClient)
        {
            Invoice invoice = new Invoice
                    (listC, month, year);
            for(TimeCard timeCard: listTimeCard)
            {
                invoice.extractLineItems(timeCard);
                String report = invoice.toReportString();
                System.out.println(report);
            }
        }

    }
   
    /**'
     * Main method of the class
     * @param args Main method args
     */
    public static void main(String[] args) 
    {
        final long serialVersionUID = 6417920328922464293L;
        String clientListFile = "ClientList.ser";
        String timeCardListFile = "TimeCardList.ser";
        List<ClientAccount> clientList = 
                (List<ClientAccount>) deserialize(clientListFile);
        List<TimeCard> timeCardList = 
                (List<TimeCard>) deserialize(timeCardListFile);
        report(timeCardList,clientList);
    }

}
