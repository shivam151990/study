package lld.logger;

import static java.util.Objects.isNull;

public abstract class AbstractLogger {

    protected int level;
    private AbstractLogger next;

    public void setNextHandler(AbstractLogger next){
        this.next = next;
    }

    public void logMessage(int level, String message, LogSubject logSubject) {
        if (this.level <= level) {
            display(message, logSubject);
        }
        if (!isNull(next)) {
            next.logMessage(level, message, logSubject);
        }
    }
    protected abstract void display(String message, LogSubject logSubject);
}
