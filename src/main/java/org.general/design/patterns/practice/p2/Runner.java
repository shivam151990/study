package design.patterns.practice.p2;

public class Runner {
    public static void main(String[] args) {
        User user1 = new User(1);
        User user2 = new User(2);
        User user3 = new User(3);

        NotificationObservable obs = new NotificationWatcher();

        obs.add(user1);
        obs.add(user2);
        obs.add(user3);

        obs.notifyMessage();
    }
}
