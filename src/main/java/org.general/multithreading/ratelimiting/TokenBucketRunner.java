package multithreading.ratelimiting;

import java.util.concurrent.TimeUnit;

public class TokenBucketRunner {

//    public static void main(String[] args) throws InterruptedException{
//
//        TokenBucket bucket = new TokenBucket(5);
//        TimeUnit.SECONDS.sleep(4);
//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 1000; i++) {
//                bucket.getTokens();
//
//                try {
//                    TimeUnit.MILLISECONDS.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//        t1.start();
//        t1.join();
//    }

    public static void main(String[] args) throws InterruptedException{

        TokenBucketAuto bucket = new TokenBucketAuto(5);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                bucket.getToken();

                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t1.join();
    }
}
