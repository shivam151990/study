package multithreading.semaphore;

public class SemaphoreRunner {

    public static void main(String[] args) throws InterruptedException {
        CountingSemaphore semaphore = new CountingSemaphore(5);

        Thread t1 = new Thread(() -> {
           for (int i = 0; i < 100; i++) {
               System.out.println("PING T1");
               try {
                   semaphore.acquire();
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("PING T2");
                try {
                    semaphore.release();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }
}
