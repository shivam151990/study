package design.patterns.command;

public class TurnOnCommand implements ICommand {

    private Light light;

    public TurnOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOnLight();
    }
}
