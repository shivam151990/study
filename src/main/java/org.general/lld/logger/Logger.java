package lld.logger;

public class Logger {

    private static volatile Logger logger;
    private static volatile AbstractLogger chainOfLogger ;
    private static volatile LogSubject logSubject ;
    private Logger() {
    }

    public synchronized static Logger getInstance() {
        if (logger == null) {
            synchronized (Logger.class) {
                if (logger == null) {
                    logger = new Logger();
                    chainOfLogger = LogManager.buildChainOfLogger();
                    logSubject = LogManager.buildSubject();
                }
            }
        }
        return logger;
    }

    private void createLog(int level, String msg, LogSubject logSubject) {
        chainOfLogger.logMessage(level, msg, logSubject);
    }

    public void info(String msg) {
        createLog(1, msg, logSubject);
    }

    public void error(String msg) {
        createLog(2, msg, logSubject);
    }

    public void debug(String msg) {
        createLog(3, msg, logSubject);
    }
}
