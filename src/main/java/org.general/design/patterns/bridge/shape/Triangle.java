package design.patterns.bridge.shape;

public class Triangle extends Shape {

    protected Triangle(Color color) {
        super(color);
    }
    @Override
    void draw() {
        System.out.println("This triangle is drawn in: " + getColor().color());
    }
}
