package design.patterns.command.tmp;

public class Light {

    private boolean switchedOn;

    public boolean isSwitchedOn() {
        return switchedOn;
    }

    public void setSwitchedOn(boolean switchedOn) {
        this.switchedOn = switchedOn;
    }

    /*
        Light Switching logic moved here.
     */
    public void switchLights() {
        switchedOn = !switchedOn;
    }
}
