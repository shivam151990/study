package design.patterns.bridge;

public abstract class Animal {
    abstract void move();
}

class Person extends Animal {
    @Override
    void move() {
        System.out.println("Person walks");
    }
}

class Fish extends Animal {
    @Override
    void move() {
        System.out.println("Fish swims");
    }
}

class Runner {
    public static void main(String[] args) {
        Animal person = new Person();
        person.move();

        Animal fish = new Fish();
        fish.move();
    }
}
