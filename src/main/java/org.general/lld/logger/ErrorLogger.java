package lld.logger;

public class ErrorLogger extends AbstractLogger {

    public ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    protected void display(String message, LogSubject logSubject) {
        String msg = "ERROR: " + message;
        logSubject.notifyObservers(3, message);
    }
}