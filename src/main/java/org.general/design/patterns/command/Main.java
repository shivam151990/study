package design.patterns.command;

public class Main {
    public static void main(String[] args) {
        Light light = new Light();

        ICommand command = new TurnOffCommand(light);
        command.execute();

        ICommand command1 = new TurnOnCommand(light);
        command1.execute();
    }
}
