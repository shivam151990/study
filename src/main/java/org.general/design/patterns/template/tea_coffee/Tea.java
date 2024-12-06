package design.patterns.template.tea_coffee;

public class Tea extends Beverage{
    @Override
    public void addCondiments() {
        System.out.println("Add masala");
    }

    @Override
    public void brew() {
        System.out.println("Adding tea leaves");
    }
}
