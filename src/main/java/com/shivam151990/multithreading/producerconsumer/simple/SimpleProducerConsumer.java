package com.shivam151990.multithreading.producerconsumer.simple;

public class SimpleProducerConsumer {

    static class SharedResource {
        int value;
        boolean isPresent;

        public synchronized void produce(int value) {
            while (isPresent) {                 // wait while the slot is full
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.value = value;
            isPresent = true;
            System.out.println("Produced Value: " + value);
            notifyAll();                        // wake the consumer
        }

        public synchronized void consume() {
            while (!isPresent) {                // wait while the slot is empty
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Consumed Value: " + value);
            isPresent = false;
            notifyAll();                        // wake the producer
        }
    }

    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) resource.produce(i);
        });
        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) resource.consume();
        });
        t1.start();
        t2.start();
    }
}