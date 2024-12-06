package design.patterns.practice.p2;

import java.util.ArrayList;
import java.util.List;

public class NotificationWatcher implements NotificationObservable {

    private List<NotificationObserver> observers;

    public NotificationWatcher() {
        observers = new ArrayList<>();
    }

    @Override
    public void add(NotificationObserver obs) {
        observers.add(obs);
    }

    @Override
    public void remove(NotificationObserver obs) {
    observers.remove(obs);
    }

    @Override
    public void notifyMessage() {
        for (NotificationObserver o: observers) {
            o.update("");
        }
    }
}
