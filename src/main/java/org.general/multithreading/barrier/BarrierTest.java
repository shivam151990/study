package multithreading.barrier;

public class BarrierTest {

    public static void main(String[] args) throws InterruptedException {

        Barrier barrier = new Barrier(3);

        Thread t1 = new Thread(() -> {
            try {
                System.out.println("Thread 1");
                barrier.await();
                Thread.sleep(3000);
                System.out.println();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                System.out.println("Thread 2");
                barrier.await();
                Thread.sleep(3000);
                System.out.println();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                System.out.println("Thread 3");
                barrier.await();
                Thread.sleep(3000);
                System.out.println();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t4 = new Thread(() -> {
            try {
                System.out.println("Thread 4");
                barrier.await();
                Thread.sleep(3000);
                System.out.println();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }
}
