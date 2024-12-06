package design.patterns.command.tmp;

import design.patterns.command.tmp.command.Command;

public class Room {

    private Light light;

    /**
     * Add Command Instance
     */
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public Room() {
        this.light = new Light();
    }

    /**
     * Add method to execute command
     */
    public void executeCommand() {
       command.execute();
    }

    /**
        1. We will unnecessarily get this feature even if we do not want it
        2. the invoked operation might need to be called from multiple places
            like if we have a lamp then we are duplicating the logic there.
     */
    public void switchLights() {
        light.setSwitchedOn(!light.isSwitchedOn());
    }
}
