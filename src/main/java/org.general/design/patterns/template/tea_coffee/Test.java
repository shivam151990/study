package design.patterns.template.tea_coffee;

public class Test {
    public static void main(String[] args) {
        Beverage tea = new Tea();
        tea.prepare();
        System.out.println();
        Beverage coffee = new Coffee();
        coffee.prepare();
    }
}
