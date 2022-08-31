package com.limingliang.concurrency._04_executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolDemo {
    static class RunnableDemo implements Runnable {

        @Override
        public void run() {
            System.out.println("RunnableDemo is execute");
        }
    }

   static class CallableDemo implements Callable<String> {

        @Override
        public String call() {
            return "callable is done";
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> call = executor.submit(new CallableDemo());
        try {
            System.out.println("Callable Result:" + call.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        Future run = executor.submit(new RunnableDemo());
        try {
            System.out.println("Runnable Result:" + run.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
    }
}
