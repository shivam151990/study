package design.patterns.bridge.move;

public class Fish extends Animal {

    public Fish(Move move) {
        super(move);
    }

    @Override
    String getName() {
        return "Fish";
    }

    @Override
    void howToMove() {
        move.move();
    }
}
