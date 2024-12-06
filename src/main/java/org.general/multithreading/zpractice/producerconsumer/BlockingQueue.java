package multithreading.zpractice.producerconsumer;

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {

    private final int maxCount;
    private final Queue<Integer> qu;
    private final Lock lock;
    private final Condition condition;

    public BlockingQueue(int maxCount) {
        this.maxCount = maxCount;
        this.qu = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();

    }

    @SneakyThrows
    private void put(int ele) {
        lock.lock();
        try {
            while (qu.size() >= maxCount) {
                System.out.println("Queue Full..." + qu.size());
                condition.await();
            }
            qu.offer(ele);
            System.out.println("Added in queue: " + ele);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    private int take() {
        lock.lock();
        int ele;
        try {
            while (qu.size() == 0) {
                System.out.println("No elements to be removed..." + qu.size());
                condition.await();
            }
            ele = qu.poll();
            System.out.println("Element removed: " + ele);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
        return ele;
    }

    private static void sleep(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        BlockingQueue bq = new BlockingQueue(10);

        Thread putThread = new Thread(() -> {
            for (int i = 1; i <= 1000; i++) {
                bq.put(i);
                sleep(1000);
            }
        });
        Thread takeThread = new Thread(() -> {
            for (int i = 1; i <= 1000; i++) {
                bq.take();
                sleep(500);
            }
        });

        putThread.start();
        takeThread.start();
    }
}
