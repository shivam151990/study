package design.patterns.prototype;

public class Test {
    public static void main(String[] args) {
        Circle c1 = new Circle(10);
        System.out.println(c1);

        Circle c2 = c1.clone();
        c2.setRadius(20);
        System.out.println(c2);
    }
}
