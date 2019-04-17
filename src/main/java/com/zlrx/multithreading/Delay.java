package com.zlrx.multithreading;

import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Delay {

    public static void main(String[] args) {

        BlockingQueue<DelayedWorker> queue = new DelayQueue<>();
        try {
            queue.put(new DelayedWorker(1000L, "A"));
            queue.put(new DelayedWorker(3000L, "B"));
            queue.put(new DelayedWorker(100L, "C"));
            queue.put(new DelayedWorker(2000L, "D"));
            while (!queue.isEmpty()) {
                System.out.println(queue.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

@Getter
@ToString
class DelayedWorker implements Delayed {

    private Long duration;
    private String name;

    public DelayedWorker(Long duration, String name) {
        this.duration = System.currentTimeMillis() + duration;
        this.name = name;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return this.duration.compareTo(((DelayedWorker) o).getDuration());
    }
}
