package multithreading.zpractice.oddeven;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OddEven1 {

    private final Lock lock;
    private final Condition condition;
    private int count;
    private final int maxCount;

    public OddEven1(int maxCount) {
        this.maxCount = maxCount;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    @SneakyThrows
    private void printEven() {
        lock.lock();
        try {
            while (count <= maxCount) {
                while (count % 2 != 0) {
                    condition.await();
                }
                System.out.println("Even: " + count);
                count++;
                condition.signalAll();
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    private void printOdd() {
        lock.lock();
        try {
            while (count <= maxCount) {
                while (count % 2 == 0) {
                    condition.await();
                }
                System.out.println("Odd: " + count);
                count++;
                condition.signalAll();
                TimeUnit.MILLISECONDS.sleep(500);
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        OddEven1 oe1 = new OddEven1(100);

        Thread oddThread = new Thread(oe1::printOdd);
        Thread evenThread = new Thread(oe1::printEven);

        oddThread.start();
        evenThread.start();
    }
}
