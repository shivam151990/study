package multithreading.oddeven;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OddEvenWithLock1 {

    public static final int MAX_COUNT = 100;
    private int count = 0;

    private Lock lock = new ReentrantLock();

    private final Condition evenCond = lock.newCondition();
    private final Condition oddCond = lock.newCondition();

    private void printEven() {
        lock.lock();
        try {
            while (count < MAX_COUNT) {

                if (count % 2 == 0) {
                    System.out.println("Even Value : " + count + ", Thread: " + Thread.currentThread().getName());
                    count++;
                    oddCond.signal();
                } else {
                    evenCond.await();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private void printOdd() {
        lock.lock();
        try {
            while (count < MAX_COUNT) {
                if (count % 2 == 1) {
                    System.out.println("Odd Value : " + count + ", Thread: " + Thread.currentThread().getName());
                    count++;
                    evenCond.signal();
                } else {
                    oddCond.await();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        OddEvenWithLock1 oddEven = new OddEvenWithLock1();

        Thread evenThread = new Thread(oddEven::printEven);
        Thread oddThread = new Thread(oddEven::printOdd);

        evenThread.start();
        oddThread.start();
    }

}
