package design.patterns.observer;

public class EmailObserver implements NotificationObserver {

    private String email;

    public EmailObserver(String email) {
        this.email = email;
    }

    @Override
    public String update() {
        return "Notification send to: " + email;
    }
}
