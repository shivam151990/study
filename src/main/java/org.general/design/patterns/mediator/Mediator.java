package design.patterns.mediator;

public interface Mediator {
    void sendMessage(User user, String message);
    void addUser(User user);
}
