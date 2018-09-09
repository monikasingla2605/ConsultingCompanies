package consult.violet.java2.log_util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Encapsulates a record from a log file. The format is specified by
 * Appendix A of the Java Logging APIs specification.  
 * 
 * @author jack
 */
public class LogRecord
{
    private final LocalDateTime time;
    private final long          millis;
    private final int           sequence;
    private final String        logger;
    private final String        level;
    private final String        clazz;
    private final String        method;
    private final String        thread;
    private final String        message;
    
    private LogRecord( Element xmlRecord )
    {
        time = LocalDateTime.parse( getNodeText( xmlRecord, "date") );
        millis = Long.parseLong( getNodeText( xmlRecord, "millis" ) );
        sequence = Integer.parseInt( getNodeText( xmlRecord, "sequence" ) );
        logger = getNodeText( xmlRecord, "logger" );
        level = getNodeText( xmlRecord, "level" );
        clazz = getNodeText( xmlRecord, "class" );
        method = getNodeText( xmlRecord, "method" );
        thread = getNodeText( xmlRecord, "thread" );
        message = getNodeText( xmlRecord, "message" );
    }
    
    /**
     * Builds a list of log records parsed from a given file.
     * The file must be formatted as specified by 
     * the Java Logging APIs specification. If an I/O error
     * or parse error occurs, null is returned. If the file
     * is a valid file with no records, an empty list will
     * be returned.
     * 
     * @param file  The given file.
     * 
     * @return A list of records parsed from the given file.
     */
    public static List<LogRecord> parseLogFile( File file )
    {
        List<LogRecord> list    = null;
        try
        {
            list = parse( file );
        }
        catch ( ParserConfigurationException
            | IOException 
            | SAXException exc
        )
        {
            exc.printStackTrace();
        }
        return list;
    }
    
    /**
     * Returns the time stamp of this record as a LocalDateTime object.
     *
     * @return The time stamp of this record
     * 
     * @see #getMillis()
     */
    public LocalDateTime getTime()
    {
        return time;
    }

    /**
     * Returns the time stamp of this record as a milliseconds since
     * the epoch.
     *
     * @return The time stamp of this record in milliseconds
     * 
     * @see #getTime()
     * @see java.lang.System#currentTimeMillis()
     */
    public long getMillis()
    {
        return millis;
    }

    /**
     * Returns the sequence number of this log record.
     * 
     * @return The sequence number of this log record.
     */
    public int getSequence()
    {
        return sequence;
    }

    /**
     * Returns the name of the logger that generated the file
     * this log record belongs to.
     * @return The name of the logger
     */
    public String getLogger()
    {
        return logger;
    }

    /**
     * Returns the log-level of this log record.
     * For example:
     * <ul>
     * <li>SEVERE</li>
     * <li>INFO</li>
     * <li>WARNING</li>
     * <li>etc.</li>
     * </ul>
     * 
     * @return The log-level of this log record
     */
    public String getLevel()
    {
        return level;
    }

    /**
     * Returns the name of the class that generated this log message.
     * 
     * @return The name of the class that generated this log message.
     */
    public String getClazz()
    {
        return clazz;
    }

    /**
     * Returns the name of the method that generated this log message.
     * 
     * @return The name of the method that generated this log message.
     */
    public String getMethod()
    {
        return method;
    }

    /**
     * Returns the ID of the thread that generated this log message.
     * 
     * @return The ID of the thread that generated this log message
     */
    public String getThread()
    {
        return thread;
    }

    /**
     * Returns the message associated with this log record.
     * 
     * @return The message associated with this log record
     */
    public String getMessage()
    {
        return message;
    }

    /** 
     * Returns a readable representation of this log record
     * with no line breaks.
     * 
     * @return A readable representation of this log record
     */
    @Override
    public String toString()
    {
        StringBuilder   bldr    = new StringBuilder();
        bldr.append( "date: " ).append( time ).append( "; " );
        bldr.append( "millis: " ).append( millis ).append( "; " );
        bldr.append( "sequence: " ).append( sequence ).append( "; " );
        bldr.append( "logger: " ).append( logger ).append( "; " );
        bldr.append( "level: " ).append( level ).append( "; " );
        bldr.append( "class: " ).append( clazz ).append( "; " );
        bldr.append( "method: " ).append( method ).append( "; " );
        bldr.append( "thread: " ).append( thread ).append( "; " );
        bldr.append( "message: " ).append( message );

        return bldr.toString();
    }
    
    private static List<LogRecord> parse( File xmlFile ) 
        throws IOException, SAXException, ParserConfigurationException
    {
        DocumentBuilder bldr    = 
            DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document        xmlDoc  = bldr.parse( xmlFile );
        Element         root    = xmlDoc.getDocumentElement();
        NodeList        nodes   = xmlDoc.getElementsByTagName( "record" );
        int             listLen = nodes.getLength();
        
        root.normalize();
        List<LogRecord> allRecords  = new ArrayList<>();
        for ( int inx = 0 ; inx < listLen ; ++inx )
        {
            Node        node    = nodes.item( inx );
            short       type    = node.getNodeType();
            if ( type == Node.ELEMENT_NODE )
                allRecords.add( new LogRecord( (Element)node ) );
        }
        
        return allRecords;
    }

    private String getNodeText( Element element, String tag )
    {
        NodeList    nodes   = element.getElementsByTagName( tag );
        Node        node    = nodes.item( 0 );
        String      text    = node.getTextContent();
        
        return text;
    }
}
