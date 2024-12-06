package design.patterns.bridge.move;

public class Swim implements Move {
    @Override
    public void move() {
        System.out.println("Swimming");
    }
}
