package com.zlrx.multithreading;

public abstract class MultiThread {

    public static Thread createThread(Action action) {
        return new Thread(() -> {
            try {
                action.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
