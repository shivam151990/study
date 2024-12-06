package lld.logger;

public class FileLogger implements LogObserver {

    @Override
    public void log(String message) {
        System.out.println("FILE: " + message);
    }
}
