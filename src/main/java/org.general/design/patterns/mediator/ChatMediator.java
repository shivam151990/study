package design.patterns.mediator;

import java.util.ArrayList;
import java.util.List;

public class ChatMediator implements Mediator {

    private final List<User> users;

    public ChatMediator() {
        this.users = new ArrayList<>();
    }

    @Override
    public void sendMessage(User user, String message) {
        for (User u : users) {
            if (user != u) {
                u.receiveMessage(message);
            }
        }
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }
}
