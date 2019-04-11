package com.zlrx.multithreading;

import java.util.ArrayList;
import java.util.List;

import static com.zlrx.multithreading.MultiThread.createThread;

public class ConsumerProducer {

    private Object lock = new Object();
    private List<Integer> list = new ArrayList<>();
    private final int LIMIT = 5;
    private final int BOTTOM = 0;
    private int value = 0;


    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == LIMIT) {
                    System.out.println("Waiting for removing items from the list");
                    lock.notify();
                    lock.wait();
                } else {
                    list.add(value++);
                    System.out.println("Added: " + value);
                }
            }
        }

    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == BOTTOM) {
                    System.out.println("Wait for adding element to the list");
                    lock.notify();
                    lock.wait();
                } else {
                    System.out.println("Removed: " + list.remove(--value));
                }
                Thread.sleep(500);
            }
        }
    }

    public static void main(String[] args) {
        ConsumerProducer consumerProducer = new ConsumerProducer();
        Thread t1 = createThread(consumerProducer::produce);
        Thread t2 = createThread(consumerProducer::consume);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
