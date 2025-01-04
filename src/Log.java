public class Log {
    // Instance is created at the time of class loading
    private static final Log instance = new Log();

    private StringBuffer logBuffer;

    // Private constructor to prevent external instantiation
    private Log() {
        logBuffer = new StringBuffer();
    }

    // Public method to provide access to the instance
    public static Log getInstance() {
        return instance; // Always returns the pre-created instance
    }

    // Method to add an entry to the log
    public void addEntry(String message) {
        logBuffer.append(message).append("\n");
    }

    // Method to retrieve the log
    public String getLog() {
        return logBuffer.toString();
    }

    //Clear Log
    public void clearLog() {
        logBuffer.setLength(0);
    }
}