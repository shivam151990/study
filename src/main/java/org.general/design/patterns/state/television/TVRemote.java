package design.patterns.state.television;

public class TVRemote {

    public static void main(String[] args) {
        Tv context = new Tv();
        TvState tvStartState = new TvOnState();
        TvState tvStopState = new TvOffState();

        // Set on state and press button
        context.setState(tvStartState);
        context.doAction();

        // Set off state and press button
        context.setState(tvStopState);
        context.doAction();
    }

}
