package net.applee.minecraft.utils;

import java.util.concurrent.Executors;

public class ThreadExecutor {

    public static void run(Runnable method) {
        Executors.newSingleThreadExecutor().execute(method);
    }

    public static Thread runThread(Runnable method) {
        Thread thread = new Thread(method);
        thread.start();
        return thread;
    }

    public static Thread runThread(String name, Runnable method) {
        Thread thread = new Thread(method, name);
        thread.start();
        return thread;
    }

}
