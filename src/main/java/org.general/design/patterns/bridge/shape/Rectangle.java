package design.patterns.bridge.shape;

public class Rectangle extends Shape {
    protected Rectangle(Color color) {
        super(color);
    }
    @Override
    void draw() {
        System.out.println("This triangle is drawn in: " + getColor().color());
    }
}
