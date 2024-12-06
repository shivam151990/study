package design.patterns.observer;

public interface StockObservable {

    void add(NotificationObserver notObs);
    void remove(NotificationObserver notObs);
    void notifySubscriber();
}

