package com.zlrx.multithreading;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Barrier {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CyclicBarrier barrier = new CyclicBarrier(10, () -> System.out.println("All the tasks are finished, barrier is tripped"));

        for (int i = 0; i < 10; i++) {
            executorService.execute(new Job(i, barrier));
        }
        executorService.shutdown();
    }

}

class Job implements Runnable {

    private int id;
    private CyclicBarrier barrier;
    private Random random;

    public Job(int id, CyclicBarrier barrier) {
        this.id = id;
        this.barrier = barrier;
        random = new Random();
    }

    @Override
    public void run() {
        doWork();
        try {
            barrier.await();
            System.out.println("After await, run code that not wait each others");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        System.out.println("Thred with id:" + this.id + " starts working...");
        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thred with id:" + this.id + " finished its job");
    }
}