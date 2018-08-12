package it.rra.fe;

public class LogBean {
    public static final int DEBUG = 0;
    public static final int INFO = 1;
    public static final int WARNING = 2;
    public static final int ERROR = 3;

    /** Holds value of property logLevel. */
    private int logLevel;

    /** Creates new LogBean */
    public LogBean() {
    }

    // Singleton: inizio.
    private static LogBean instance = null;
    public static LogBean getInstance() {
        if (instance==null) instance = new LogBean();
        return instance;
    }
    // Singleton: fine.


    public void write(int level, String message) {
        if ( level <= logLevel )
            System.out.print( message );
    }
    public void writeln(int level, String message) {
        if ( level <= logLevel )
            System.out.println( message );
    }

    // Getters & Setters
    public int getLogLevel() {
        return logLevel;
    }
    public void setLogLevel(int logLevel) {
        int oldLogLevel = this.logLevel;
        this.logLevel = logLevel;
    }
}
