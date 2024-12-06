package multithreading.ratelimiting;

import java.util.concurrent.TimeUnit;

public class TokenBucketAuto {

    private final int MAX_TOKEN;
    private int currentToken;

    public TokenBucketAuto(int MAX_TOKEN) {
        this.MAX_TOKEN = MAX_TOKEN;
        currentToken = 0;

        // TODO Refactor to move thread code to some other method
        Thread t1 = new Thread(this::fillBucketAuto);
        t1.setDaemon(true);
        t1.start();
    }

    private void fillBucketAuto() {
        while(true) {
            synchronized (this) {
                if (currentToken < MAX_TOKEN) {
                    currentToken++;
                }
                notifyAll();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getToken() {
        synchronized (this) {
            while (currentToken == 0) {
                try {
                    System.out.println("NO TOKEN, WAITING");
                    wait();
                } catch (InterruptedException e) {
                    // Log & Swallow Exception
                }
            }
            currentToken--;
            System.out.println("GRANTED TOKEN");
        }
    }
}
