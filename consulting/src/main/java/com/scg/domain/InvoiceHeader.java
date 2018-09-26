package com.scg.domain;

import java.time.LocalDate;

import com.scg.util.Address;
import com.scg.util.PersonalName;
import com.scg.util.StateCode;

/**
 * Class encapsulates the properties of Header of the invoice report.
 * @author Monika
 *
 */
public class InvoiceHeader
{
    private String businessName;
    private Address businessAddress;
    private ClientAccount client;
    private LocalDate invoiceDate;
    private LocalDate invoiceForMonth;

    /**
     * Instantiates a new class
     * @param businessName Name of the consulting company.
     * @param businessAddress Consulting company address
     * @param client Name of client company
     * @param invoiceDate Date of Invoice
     * @param invoiceForMonth For which month Invoice is for
     */
    public InvoiceHeader( String businessName, Address businessAddress, 
            ClientAccount client, LocalDate invoiceDate, LocalDate invoiceForMonth) 
    {
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.client = client;
        this.invoiceDate = invoiceDate;
        this.invoiceForMonth = invoiceForMonth;
    }

    private String line(char c, int i)
    {
        StringBuilder bld = new StringBuilder("");
        for(int j=0;j<i;j++)
            bld.append(c);
        return bld.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuilder bldr = new StringBuilder("");
        bldr.append(businessName).append(System.lineSeparator());
        bldr.append(businessAddress).append(System.lineSeparator());    
        bldr.append(System.lineSeparator()).append("Invoice for:");
        bldr.append(System.lineSeparator());
        bldr.append(client.getName());
        bldr.append(System.lineSeparator());
        bldr.append("Invoice For Month of: ");
        String str = String.format("%tB %tY", invoiceForMonth,invoiceForMonth);
        bldr.append(str);
        bldr.append(System.lineSeparator());
        bldr.append("Invoice Date: ");
        str = String.format("%tB %td, %tY", invoiceDate, invoiceDate, invoiceDate);
        bldr.append(str);
        bldr.append(System.lineSeparator());
        bldr.append(System.lineSeparator());
        str = String.format("%-10s", "Date");
        bldr.append(str).append("  ");
        str = String.format("%-28s", "Consultant");
        bldr.append(str).append("  ");
        str = String.format("%-18s", "Skill");
        bldr.append(str).append("  ");
        str = String.format("%-5s", "Hours");
        bldr.append(str).append("  ");
        str = String.format("%-10s", "Charge");
        bldr.append(str);
        bldr.append(System.lineSeparator());
        bldr.append(line('-',10));
        bldr.append("  ");
        bldr.append(line('-',28));
        bldr.append("  ");
        bldr.append(line('-',18));
        bldr.append("  ");
        bldr.append(line('-',5));
        bldr.append("  ");
        bldr.append(line('-',10));
        bldr.append(System.lineSeparator());
        return bldr.toString();

    }
}
