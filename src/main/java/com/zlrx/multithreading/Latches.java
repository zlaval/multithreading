package com.zlrx.multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Latches {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(new Worker(i, countDownLatch));
        }

        try {
            countDownLatch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All works are down");


        executorService.shutdown();
    }

}

class Worker implements Runnable {

    private int id;
    private CountDownLatch countDownLatch;

    public Worker(int id, CountDownLatch countDownLatch) {
        this.id = id;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        doWork();
        countDownLatch.countDown();
    }

    private void doWork() {
        System.out.println("Thred with id:" + this.id + " starts working...");
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
