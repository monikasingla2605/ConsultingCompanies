package consult.violet.java2.log_util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides access to the capabilities of logging management
 * as performed by the java.util.logging package. It is implemented as a
 * singleton, and exposes two features
 * of the logging package:
 * 
 * <ol>
 * <li>
 * Setting the log level in the root logger.<br>
 * The caller sets the level to LOW, MEDIUM or HIGH.
 * The LOW setting generates the least amount log traffic;
 * only SEVERE messages will be logged. HIGH generates the
 * most amount of log traffic; all messages will be logged.
 * MEDIUM generates log messages at the INFO level and above.
 * </li>
 * <li>
 * Adding a file appender for creating a log file.
 * </li>
 * </ol>
 * 
 * @see LevelProxy
 * @see #setLogLevel
 * @see #addFileHandler
 *
 * @author jStr
 */
public enum LogConfigurator
{
    /** This class's singleton. */
    INSTANCE;
    
    private final Logger rootLogger  = Logger.getLogger( "" );
    
    private final Map<String, FileHandler>   fileHandlerMap  = 
        new HashMap<>();

    /**
     * Establishes a coarse mapping of the desired log level to the
     * actual log level. This allows users of SLF4J loggers to roughly control
     * the log level provided by the java.util.logging package without
     * actually declaring dependencies on that package.
     * 
     * @author jack
     */
    public enum LevelProxy
    {
        /** Generates the least volume of log traffic. */
        LOW,
        /** Generates a moderate volume of log traffic. */
        MEDIUM,
        /** Generates the highest volume of log traffic. */
        HIGH
    };
    
    /**
     * Sets the log level for the root logger.
     * 
     * @param level The desired level for the root logger.
     */
    public void setLogLevel( LevelProxy level )
    {
        Level   jdkLogLevel = Level.SEVERE;
        switch ( level )
        {
        case LOW:
            jdkLogLevel = Level.SEVERE;
            break;
        case MEDIUM:
            jdkLogLevel = Level.WARNING;
            break;
        case HIGH:
            jdkLogLevel = Level.ALL;
            break;
        }
        
        rootLogger.setLevel( jdkLogLevel );
    }
    
    /**
     * Adds a file handler to the root logger.
     * It will be an instance of java.util.logging.FileHandler.
     * The file handler may later be removed.
     * If the file already exists, there it may optionally be
     * overwritten or appended to.
     * <p>
     * <b>Important:</b>
     * The <em>append</em> mode of java.util.logging.FileHandler
     * generates an invalid XML file. The file can be opened
     * and examined, but, as of now, it cannot be parsed by
     * LogRecord.parseLogFile().
     * 
     * @param file      The file to use for logging.
     * @param append    If true, an existing file will be appended to,
     *                  otherwise an existing file will be overwritten
     *                  
     * @throws IOException If an IO exception occurs during the operation.
     */
    public void addFileHandler( File file, boolean append ) throws IOException
    {
        String  name    = file.getAbsolutePath();
        FileHandler fileHandler = new FileHandler( name, append );
        fileHandlerMap.put( name, fileHandler );
        rootLogger.addHandler( fileHandler );
    }
    
    /**
     * Adds a file handler to the root logger.
     * It will be an instance of java.util.logging.FileHandler.
     * The file handler may later be removed.
     * If the file already exists, there it may optionally be
     * overwritten or appended to.
     * <p>
     * <b>Important:</b>
     * The <em>append</em> mode of java.util.logging.FileHandler
     * generates an invalid XML file. The file can be opened
     * and examined, but, as of now, it cannot be parsed by
     * LogRecord.parseLogFile().
     * 
     * @param path      The path to the file to use for logging.
     * @param append    If true, an existing file will be appended to,
     *                  otherwise an existing file will be overwritten
     *                  
     * @throws IOException If an IO exception occurs during the operation.
     * 
     * @see #addFileHandler(File, boolean)
     */
    public void addFileHandler( String path, boolean append )
        throws IOException
    {
        File    file    = new File( path );
        addFileHandler( file, append );
    }
    
    /**
     * Adds a file handler to the root logger.
     * It will be an instance of java.util.logging.FileHandler.
     * If the file already exists, it will be silently overwritten.
     * 
     * @param path      The path to the file to use for logging.
     *                  
     * @throws IOException If an IO exception occurs during the operation.
     * 
     * @see #addFileHandler(File, boolean)
     */
    public void addFileHandler( String path ) throws IOException
    {
        addFileHandler( path, false );
    }
    
    /**
     * Removes a given file handler from the root logger. If the 
     * file handler does not exist, the operation is silently
     * ignored.
     * 
     * @param file  The file that identifies the file handler to
     *              be removed.
     */
    public void removeFileHandler( File file )
    {
        FileHandler handler = fileHandlerMap.remove( file.getAbsolutePath() );
        if ( handler != null )
        {
            rootLogger.removeHandler( handler );
            handler.close();
        }
    }
    
    /**
     * Removes a given file handler from the root logger. If the 
     * file handler does not exist, the operation is silently
     * ignored.
     * 
     * @param path  The path that identifies the file handler to
     *              be removed.
     */
    public void removeFileHandler( String path )
    {
        File    file    = new File( path );
        removeFileHandler( file );
    }
}
