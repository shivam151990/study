package multithreading.dining;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher extends Thread {

    private int id;
    private Lock left;
    private Lock right;

    public Philosopher(int id, Lock left, Lock right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            think();
            eating();
        }
    }


    private void think() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Philosopher " + id + " is thinking");
    }

    private void eating() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Philosopher " + id + " is eating");
        left.lock();
            System.out.println("Philosopher " + id + " using left chopstick");
                right.lock();
                    System.out.println("Philosopher " + id + " using right chopstick");
                right.unlock();
                    System.out.println("Philosopher " + id + " put down right chopstick");
        left.unlock();
            System.out.println("Philosopher " + id + " put down left chopstick");
    }
}


public class DiningPhilosopher {

    private static final int NUM_PHILOSOPHER = 5;


    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHER];
        ReentrantLock[] chopsticks = new ReentrantLock[NUM_PHILOSOPHER];
        for (int i = 0; i < NUM_PHILOSOPHER; i++) {
            chopsticks[i] = new ReentrantLock();
        }

        philosophers[0] = new Philosopher(0, chopsticks[0], chopsticks[1]);
        philosophers[1] = new Philosopher(1, chopsticks[1], chopsticks[2]);
        philosophers[2] = new Philosopher(2, chopsticks[2], chopsticks[3]);
        philosophers[3] = new Philosopher(3, chopsticks[3], chopsticks[4]);
        philosophers[4] = new Philosopher(4, chopsticks[0], chopsticks[4]);

        for (int i = 0; i < NUM_PHILOSOPHER; i++) {
            philosophers[i].start();
        }
    }
}
