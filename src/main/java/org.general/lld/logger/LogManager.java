package lld.logger;

public class LogManager {
    protected static AbstractLogger buildChainOfLogger() {
        AbstractLogger info = new InfoLogger(1);
        AbstractLogger error = new DebugLogger(2);
        AbstractLogger debug = new ErrorLogger(3);
        info.setNextHandler(error);
        error.setNextHandler(debug);
        return info;
    }

    protected static LogSubject buildSubject() {
        LogSubject logSubject  = new LogSubject();
        ConsoleLogger consoleLogger = new ConsoleLogger();
        FileLogger fileLogger = new FileLogger();

        logSubject.addObserver(1, consoleLogger);
        logSubject.addObserver(2, fileLogger);
        logSubject.addObserver(1, fileLogger);

        return logSubject;
    }
}
