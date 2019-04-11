package com.zlrx.multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {

    private static int counter = 0;
    private static Lock lock = new ReentrantLock();

    public static void increment() {
        lock.lock();
        try {
            for (int i = 0; i < 1000; i++)
                counter++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        Thread t1 = MultiThread.createThread(Locks::increment);
        Thread t2 = MultiThread.createThread(Locks::increment);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(counter);
    }

}
