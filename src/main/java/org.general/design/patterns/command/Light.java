package design.patterns.command;

// Receiver of command
public class Light {

    public void turnOnLight() {
        System.out.println("Lights are turned on");
    }

    public void turnOffLight() {
        System.out.println("Lights are turned off");
    }
}
