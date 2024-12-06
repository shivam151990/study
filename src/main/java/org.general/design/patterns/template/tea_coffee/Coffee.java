package design.patterns.template.tea_coffee;

public class Coffee extends Beverage {
    @Override
    public void addCondiments() {
        System.out.println("Adding cinnamon");
    }

    @Override
    public void brew() {
        System.out.println("Adding coffee");
    }
}
