package design.patterns.observer;

public class Test {
    public static void main(String[] args) {
        PhoneStockObservable observable = new PhoneStockObservable();

        NotificationObserver ob1 = new EmailObserver("shivamsrivas@adobe.com");
        NotificationObserver ob2 = new PhoneObserver("9742745991");

        observable.add(ob1);
        observable.add(ob2);

        observable.setPhoneCount(10);
    }
}
