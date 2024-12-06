package design.patterns.state.television;

public class TvOffState implements TvState {

    @Override
    public void doAction() {
        System.out.println("TV is turned OFF");
    }
}
