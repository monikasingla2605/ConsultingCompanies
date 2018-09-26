package com.scg.domain;

/**
 * This class encapsulates the properties of footer
 * @author Monika
 *
 */
public class InvoiceFooter
{
    public String businessMan;
    public int PageNo;

    /**
     * Instantiates a new footer
     * @param businessMan Company name creating the invoice
     */
    public InvoiceFooter(String businessMan)
    {
        this.businessMan = businessMan;
        PageNo = 1;
    }

    /**
     * Method updates the page number to next.
     */
    public void incrementPageNumber()
    {
        PageNo++;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuilder bldr = new StringBuilder();
        String str = businessMan;;
        str = String.format("%-70s", str);
        bldr.append(str).append("Page:");
        str = String.format("%4d", PageNo);
        bldr.append(str);
        bldr.append(System.lineSeparator());
        for(int i=0;i<79;i++)
            bldr.append("=");
        return bldr.toString();
    }
}
