package multithreading.zpractice.oddeven;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class OddEven {


    private final int maxCount;
    private int count;

    private final Object lockObject;

    public OddEven(int maxCount) {
        this.maxCount = maxCount;
        count = 0;
        lockObject = new Object();
    }

    @SneakyThrows
    private void printOdd() {
        synchronized (lockObject) {
            while (count <= maxCount) {
                while (count % 2 == 0) {
                    lockObject.wait();
                }
                System.out.println("Odd: " + count);
                count++;
                lockObject.notifyAll();
                TimeUnit.MILLISECONDS.sleep(500);
            }
        }
    }

    @SneakyThrows
    private void printEven() {
        synchronized (lockObject) {
            while (count <= maxCount) {
                while (count % 2 != 0) {
                    lockObject.wait();
                }
                System.out.println("Even: " + count);
                count++;
                lockObject.notifyAll();
                TimeUnit.MILLISECONDS.sleep(500);
            }
        }
    }

    public static void main(String[] args) {
        OddEven oddEven = new OddEven(100);

        Thread oddThread = new Thread(oddEven::printOdd);
        Thread evenThread = new Thread(oddEven::printEven);

        oddThread.start();
        evenThread.start();
    }


}
