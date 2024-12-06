package multithreading.oddeven;

public class OddEven {

    private int count;
    private final Object block = new Object();
    private int start = 0;

    public OddEven(final int count) {
        this.count = count;
    }

    private void printOdd() {
        while (start < count) {
             synchronized (block) {
                if (start % 2 == 1) {
                    System.out.println("Odd Value : " + start + ", Thread: " + Thread.currentThread().getName());
                    start++;
                    block.notify();
                }
            }
        }
    }

    private void printEven() {
        while (start < count) {
            synchronized (block) {
                if (start % 2 == 0) {
                    System.out.println("Even Value : " + start + ", Thread: " + Thread.currentThread().getName());
                    start++;
                    try {
                        block.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        OddEven oddEven = new OddEven(100);

        Thread evenThread = new Thread(oddEven::printEven);
        Thread oddThread = new Thread(oddEven::printOdd);

        evenThread.start();
        oddThread.start();
    }


}
