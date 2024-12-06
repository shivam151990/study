package multithreading.barrier;

public class Barrier1 {

    private int count = 0;
    private final int maxCount;
    private int released = 0;

    public Barrier1(int maxCount) {
        this.maxCount = maxCount;
    }

    public synchronized void await() throws InterruptedException {
        while (count == maxCount) {
            wait();
        }

        count++;
        if (count == maxCount) {
            notifyAll();
            released = maxCount;
        } else {
            while (count < maxCount) {
                wait();
            }
        }

        released--;
        if (released == 0) {
            count = 0;
            notifyAll();
        }
    }
}
