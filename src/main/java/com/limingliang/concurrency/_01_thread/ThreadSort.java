package com.limingliang.concurrency._01_thread;

import java.util.concurrent.CountDownLatch;

public class ThreadSort {

    public static void main(String[] args) {
        //joinSort();
        countDownLatchSort();
    }

    private static CountDownLatch first = new CountDownLatch(1);
    private static CountDownLatch second = new CountDownLatch(1);
    public static void countDownLatchSort() {

        Thread thread1 = new Thread(() -> {
            System.out.println("打开冰箱!");
            first.countDown();
        });

        Thread thread2 = new Thread(() -> {
            try {
                first.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("拿出一瓶牛奶!");
            second.countDown();
        });

        Thread thread3 = new Thread(() -> {
            try {
                second.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("关闭冰箱门!");
        });

        thread3.start();
        thread2.start();
        thread1.start();
    }

    public static void joinSort() {
        Thread thread1 = new Thread(() -> {
            System.out.println("打开冰箱!");
        });

        Thread thread2 = new Thread(() -> {
            try {
                thread1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("拿出一瓶牛奶!");
        });

        Thread thread3 = new Thread(() -> {
            try {
                thread2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("关闭冰箱门!");
        });

        thread3.start();
        thread2.start();
        thread1.start();
    }
}
