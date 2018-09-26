package com.scg.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import com.scg.util.Address;
import com.scg.util.StateCode;

/**
 * Class Invoice which creates report and has the properties for client and line items.
 * @author Monika
 *
 */
public class Invoice
{
    private ClientAccount client;
    private int year;
    private Month month;
    private List<InvoiceLineItem> items;
    private InvoiceHeader header = null;
    private InvoiceFooter footer = null;


    /**
     * Instantiates a new class
     * @param client Client to be charged
     * @param month month for which client is charged
     * @param year year for which client is charged
     */
    public Invoice(ClientAccount client, Month month, int year) 
    {
        this.client = client;
        this.month = month;
        this.year = year;
        items = new LinkedList<>();
        String file_name = "invoice.properties";
        try(InputStream inStream = 
                ClassLoader.getSystemResourceAsStream(file_name))
        {
            if(inStream == null)
                throw new IOException(file_name+" not found");
            Properties props = new Properties();
            props.load(inStream);
            String businessName = props.getProperty("business.name");
            String street = props.getProperty("business.street");
            String city = props.getProperty("business.city");
            StateCode state = StateCode.valueOf(props.getProperty("business.state"));
            String zip = props.getProperty("business.zip");
            Address add = new Address(street,city, state, zip);
            this.header = new InvoiceHeader(businessName, add,client, 
                    LocalDate.now(), getStartDate() );
            this.footer = new InvoiceFooter(businessName);
        }
        catch(IOException exc)
        {
            System.out.println("resource file is missing");
        }
    }
    

    /**
     * Adds InvoiceLineItems to the list item
     * @param item Add <em> items </em> to the list item, it's of the type class InVoiceLineItem.
     */
    public void addLineItem(InvoiceLineItem item)
    {
        items.add(item);
    }
    
    /**
     * Extracts InvoiceLineItem properties and add to the list.
     * @param timeCard Timecard for which invoice line items are extracted
     */
    public void extractLineItems(TimeCard timeCard)
    {
        
//        List<InvoiceLineItem> item = new LinkedList<>();
//        timeCard.getBillableHoursForClient(client.getName()).stream()
//        .filter(p->p.getDate().getMonth() == month)
//        .map(p->new InvoiceLineItem
//              (p.getDate(),timeCard.getConsultant(),
//              p.getSkill(), p.getHours()))
//        .forEach(item::add);
//       
   //     item.forEach(k -> addLineItem(k));        
        List<ConsultantTime> list = timeCard.getBillableHoursForClient(client.getName());
        list.stream()
        .filter(p->p.getDate().getMonth() == month)
        .forEach(p->addLineItem(new InvoiceLineItem
                (p.getDate(),timeCard.getConsultant(),
                        p.getSkill(), p.getHours())));
        
    }

    /**
     * Gets the clientAccount
     * @return Return Client name of the type class ClientName
     */
    public ClientAccount getClientAccount()
    {
        return this.client;
    }

    /**
     * Gets the Invoice month
     * @return Returns month of the Invoice date.
     */
    public Month getInvoiceMonth()
    {
        return this.month;
    }

    /**
     * Gets the date for invoice year
     * @return Returns the year for invoice year.
     */
    public int getInvoiceYear()
    {
        return this.year;
    }

    /**
     * Gets the start date of the invoice
     * @return Returns the start date with default day of month as 1.
     */
    public LocalDate getStartDate()
    {
        return LocalDate.of(this.year, this.month, 1);

    }

    /**
     * Gets the total charges for the client
     * @return Returns total charges of the client.
     */
    public int getTotalCharges()
    {
        int charges = 0;
        for(InvoiceLineItem it:items)
        {
            int rate = it.getCharge();
            charges += rate;
        }
        return charges;        
    }

    /**
     * Gets the total billable hours
     * @return Returns the total billable hours
     */
    public int getTotalHours()
    {
        int hours =0;
        for(InvoiceLineItem it: items)
        {
            hours += it.getHours();
        }
        return hours;
    }

    /**
     * Generates a report in string format
     * @return returns the invoice report in string format
     */
    public String toReportString()
    {
        StringBuilder bldr = new StringBuilder();
        int counter = 0;
        int count = items.size();
        for(InvoiceLineItem it: items)
        {
            if(counter%5 == 0)
            {
                bldr.append(header.toString());
            }
            LocalDate date = it.getDate();
            String str = String.format("%tm/%td/%tY", date, date, date);
            str = String.format("%10s", str);
            bldr.append(str).append("  ");
            str = String.format("%-28s",it.getConsultant());
            bldr.append(str).append("  ");
            str = String.format("%-18s", it.getSkill());
            bldr.append(str).append("  ");
            str = String.format("%5d", it.getHours());
            bldr.append(str).append("  ");
            float i = it.getCharge();
            str = String.format("%,10.2f", i);
            bldr.append(str).append(System.lineSeparator());
            if(counter %5 == 4 && ((counter+1) != count))
            {
                bldr.append(System.lineSeparator());
                bldr.append(System.lineSeparator());
                if(counter>4)
                    footer.incrementPageNumber();
                bldr.append(footer.toString());
                bldr.append(System.lineSeparator());
                bldr.append(System.lineSeparator());
            }
            counter++;
        }
        bldr.append(System.lineSeparator());
        bldr.append("Total:");
        String str = String.format("%61d", this.getTotalHours());
        bldr.append(str);
        float  i = this.getTotalCharges();
        str = String.format("%,12.2f", i);
        bldr.append(str);
        bldr.append(System.lineSeparator());
        bldr.append(System.lineSeparator());
        bldr.append(System.lineSeparator());
        if(counter>5)
            footer.incrementPageNumber();
        bldr.append(footer.toString());
        bldr.append(System.lineSeparator());
        return bldr.toString(); 

    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder bldr = new StringBuilder("");
        bldr.append("client=").append(client.getName());
        bldr.append(",invoiceYear=").append(year);
        bldr.append(",invoiceYear=").append(month);
        return bldr.toString();        
    }


}
