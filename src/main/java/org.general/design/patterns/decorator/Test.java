package design.patterns.decorator;

public class Test {

    public static void main(String[] args) {
        Pizza vegPizzaWithCheeseAndMushroom = new Mushroom(new ExtraCheese(new VeggieDelight()));
        System.out.println(vegPizzaWithCheeseAndMushroom.cost());
    }
}
