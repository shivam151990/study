package design.patterns.bridge.shape;

public abstract class Shape {

    private final Color color;

    protected Shape(Color color) {
        this.color = color;
    }
    abstract void draw();

    public Color getColor() {
        return color;
    }
}
