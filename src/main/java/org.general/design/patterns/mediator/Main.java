package design.patterns.mediator;

public class Main {
    public static void main(String[] args) {
        Mediator mediator = new ChatMediator();

        User cu1 = new ChatUser(mediator, "user1");
        User cu2 = new ChatUser(mediator, "user2");
        User cu3 = new ChatUser(mediator, "user3");
        User cu4 = new ChatUser(mediator, "user4");

        mediator.addUser(cu1);
        mediator.addUser(cu2);
        mediator.addUser(cu3);
        mediator.addUser(cu4);

        cu1.sendMessage("Hello how are you");
    }
}
