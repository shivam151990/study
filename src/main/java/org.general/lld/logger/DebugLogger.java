package lld.logger;

public class DebugLogger extends AbstractLogger {

    public DebugLogger(int level) {
        this.level = level;
    }

    @Override
    protected void display(String message, LogSubject logSubject) {
        String msg = "DEBUG: " + message;
        logSubject.notifyObservers(2, message);
    }
}
