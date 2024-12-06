package multithreading.readwritelock;

public class ReadWriteLock {

    private int readLock = 0;
    private boolean isWriteLocked = false;
    public synchronized void acquireReadLock() throws InterruptedException {
        while (isWriteLocked) {
            System.out.println("READ WAITING....WRITING IN PROGRESS: " + Thread.currentThread().getName());
            wait();
        }
        readLock++;
    }

    public synchronized void releaseReadLock() {
        readLock--;
        System.out.println("RELEASED READ LOCK " + Thread.currentThread().getName());
        notify();
    }

    public synchronized void acquireWriteLock() throws InterruptedException {
        if (isWriteLocked || readLock > 0) {
            System.out.println("WRITE WAITING ...READ LOCK " + readLock + " " + Thread.currentThread().getName());
            wait();
        }
        isWriteLocked = true;
    }

    public synchronized void releaseWriteLock() {
        isWriteLocked = false;
        System.out.println("RELEASED WRITE LOCK " + Thread.currentThread().getName());
        notify();
    }
}
