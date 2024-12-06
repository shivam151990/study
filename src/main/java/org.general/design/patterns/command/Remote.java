package design.patterns.command;

public class Remote {

    private ICommand command;

    public Remote(ICommand command) {
        this.command = command;
    }

    public void runCommand() {
        command.execute();
    }
}
