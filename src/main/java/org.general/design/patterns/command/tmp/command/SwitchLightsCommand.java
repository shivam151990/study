package design.patterns.command.tmp.command;

import design.patterns.command.tmp.Light;

public class SwitchLightsCommand implements Command {

    private Light light;

    public SwitchLightsCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.switchLights();
    }
}
