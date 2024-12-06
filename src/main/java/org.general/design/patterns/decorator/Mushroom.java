package design.patterns.decorator;

public class Mushroom implements Topping {

    private Pizza pizza;

    public Mushroom(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public int cost() {
        return pizza.cost() + 30;
    }
}
