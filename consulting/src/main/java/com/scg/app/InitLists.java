package com.scg.app;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scg.domain.ClientAccount;
import com.scg.domain.TimeCard;

/**
 * This class serializes the timecard and clientaccount list.
 * @author Monikas
 *
 */
public class InitLists implements Serializable
{

   
    /**
     * Method for serialization
     * @param list - list object fed to method for serializing
     * @param fileName - filename to persist/write out to
     */
    public static void serialize(List<?> list, String fileName)
    {
        try(
                FileOutputStream  bStream =
                new FileOutputStream(fileName);
                ObjectOutputStream oStream =
                        new ObjectOutputStream( bStream );
                )
        {
            oStream.writeObject( list );
            oStream.close();
        }
        catch ( Exception exc )
        {
            exc.printStackTrace();
            System.exit( 1 );
        }
    }

    /**
     * Main method for the class
     * @param args Main method arguments
     */
    public static void main(String[] args)
    {
        final long serialVersionUID = 6417920328922464293L;
        final String clientListFile = "ClientList.ser";
        final String timeCardListFile = "TimeCardList.ser";
        SCGDriver           driver       = new SCGDriver();
        List<TimeCard>      timeCardList = driver.getTimeCards();
        List<ClientAccount> clientList   = driver.getClientAccounts();
        for(TimeCard list: timeCardList)
            System.out.println(list);
        for(ClientAccount list: clientList)
            System.out.println(list.getName());
        serialize(timeCardList, timeCardListFile);
        serialize(clientList, clientListFile); 

    }
}
