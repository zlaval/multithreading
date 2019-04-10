package com.zlrx.multithreading;

public class WaitNotify {

    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer start");
            wait();
            System.out.println("Producer finished");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            System.out.println("Consumer start");
            notify();
            System.out.println("Consumer finished");
        }
    }

    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify();

        Thread t1 = new Thread(() -> {
            try {
                waitNotify.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                waitNotify.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

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
