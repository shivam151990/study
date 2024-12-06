package design.patterns.mediator;

public class ChatUser extends User {

    public ChatUser(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    void sendMessage(String message) {
        System.out.println("Sending message to: " + this.name);
        mediator.sendMessage(this, message);
    }

    @Override
    void receiveMessage(String message) {
        System.out.println("User: " + this.name + " received message");
    }
}
