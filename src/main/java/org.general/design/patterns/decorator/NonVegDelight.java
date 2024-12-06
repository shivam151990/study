package design.patterns.decorator;

public class NonVegDelight implements Pizza {

    @Override
    public int cost() {
        return 200;
    }
}
