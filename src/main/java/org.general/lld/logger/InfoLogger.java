package lld.logger;

public class InfoLogger extends AbstractLogger {

    public InfoLogger(int level) {
        this.level = level;
    }

    @Override
    protected void display(String message, LogSubject logSubject) {
        String msg = "INFO: " + message;
        logSubject.notifyObservers(1, message);
    }
}
