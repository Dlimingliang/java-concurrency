package com.limingliang.concurrency._04_executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CountDownDemo {

    private static CountDownLatch startSignal = new CountDownLatch(1);
    private static CountDownLatch endSignal = new CountDownLatch(6);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = new ThreadPoolExecutor(10,10,10, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
        for (int i = 0; i < 6; i++) {
            executor.execute(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + " 运动员等待裁判员响哨！！！");
                    startSignal.await();
                    System.out.println(Thread.currentThread().getName() + "正在全力冲刺");
                    endSignal.countDown();
                    System.out.println(Thread.currentThread().getName() + "  到达终点");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        System.out.println("裁判员发号施令啦！！！");
        startSignal.countDown();
        endSignal.await();
        System.out.println("所有运动员到达终点，比赛结束！");
        executor.shutdown();
    }
}
