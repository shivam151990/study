package design.patterns.bridge.move;

public abstract class Animal {

    protected Move move;

    public Animal(Move move) {
        this.move = move;
    }

    abstract String getName();

    abstract void howToMove();
}
