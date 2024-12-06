package design.patterns.practice.decorator;

public class ExtraCheese implements Topping {

    private Pizza pizza;


    public ExtraCheese(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public double cost() {
        return pizza.cost() + 30;
    }
}
