package multithreading.barrier;

public class Barrier {

    private int count;
    private final int totalThreadCount;

    private int released;
    public Barrier(int totalThreadCount) {
        this.totalThreadCount = totalThreadCount;
    }

    public synchronized void await() throws InterruptedException {
        while (count == totalThreadCount) {
            wait();
        }
        count++;

        if (count == totalThreadCount) {
            notifyAll();
            released = totalThreadCount;
        } else {
            while (count < totalThreadCount) {
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
