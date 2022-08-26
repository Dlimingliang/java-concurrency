package com.limingliang.concurrency._01_thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();

        Thread myRunnable = new Thread(new MyRunable());
        myRunnable.start();

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future= service.submit(new MyCallable());
        String result = null;
        try {
            result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
         service.shutdown();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<String> futureTask = new FutureTask<>(new MyCallable());
        executor.submit(futureTask);
        executor.shutdown();
        System.out.println("task运行结果"+futureTask.get());


        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("thread运行结果"+futureTask.get());

    }
}
