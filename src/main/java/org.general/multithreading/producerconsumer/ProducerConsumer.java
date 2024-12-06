package multithreading.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockingQueue<E> {
    private final Queue<E> queue;
    private final int size;
    private final Lock lock = new ReentrantLock();
    private final Condition quFullCond = lock.newCondition();
    private final Condition quEmptyCond = lock.newCondition();

    public BlockingQueue(int size) {
        this.queue = new LinkedList<>();
        this.size = size;
    }

    public void put(E item) {
        lock.lock();
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
            if(queue.size() == size) {
                System.out.println("Queue is Full, cannot fill !!!!");
                quEmptyCond.await();
            }
            System.out.println("Adding item to queue: " + item);
            queue.offer(item);
            quFullCond.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public E take() {
        lock.lock();
        E element;
        try {
            TimeUnit.MILLISECONDS.sleep(500);
            while(queue.size() == 0) {
                System.out.println("Queue is Empty, cannot remove !!!!");
                quFullCond.await();
            }
            element = queue.remove();
            System.out.println("Element removed: " + element);
            quEmptyCond.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return element;
    }
}


public class ProducerConsumer {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);
        Random random = new Random();

        Thread t1 = new Thread(() -> {
            while (true) {
                queue.put(random.nextInt(10));
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                queue.take();
            }
        });

        t1.start();
        t2.start();
    }
}