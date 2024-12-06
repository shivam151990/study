package multithreading.semaphore;

public class CountingSemaphore {

    private int permits;
    private final int MAX_PERMITS;

    public CountingSemaphore(int permits) {
        this.MAX_PERMITS = permits;
        this.permits = 0;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits == MAX_PERMITS) {
            System.out.println("WAITING....NO PERMITS TO ACQUIRE");
            wait();
        }
        permits++;
        notify();
    }

    public synchronized void release() throws InterruptedException {
        while (permits == 0) {
            System.out.println("WAITING....NO PERMITS TO RELEASE");
            wait();
        }
        permits--;
        notify();
    }
}
