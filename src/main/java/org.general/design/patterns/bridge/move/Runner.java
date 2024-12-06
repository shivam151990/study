package design.patterns.bridge.move;

public class Runner {
    public static void main(String[] args) {
        Animal person = new Person(new Walk());
        person.howToMove();

        Animal fish = new Fish(new Swim());
        fish.howToMove();
    }
}
