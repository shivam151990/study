package design.patterns.practice.p2;

public class User implements NotificationObserver {

    private int id;

    public User(int id) {
        this.id = id;
    }

    @Override
    public void update(String str) {
        System.out.println("Update user " + id + ": " + str);
    }
}
