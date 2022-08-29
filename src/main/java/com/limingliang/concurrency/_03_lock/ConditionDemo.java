package com.limingliang.concurrency._03_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static volatile boolean flag = false;

    static class Waiter implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                while (!flag) {
                    System.out.println(Thread.currentThread().getName() + "当前条件不满足等待");
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "接收到通知条件满足");
                }
            }finally {
                lock.unlock();
            }
        }
    }

    static class Signaler implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                flag = true;
                System.out.println(Thread.currentThread().getName() + "通知其他线程处理");
                condition.signal();
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Waiter());
        thread1.start();
        Thread thread2 = new Thread(new Signaler());
        thread2.start();
        System.out.println(Thread.currentThread().getName() + "主线程结束结束");
    }
}
