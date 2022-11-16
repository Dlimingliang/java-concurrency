package com.limingliang.concurrency._06_leetcode;

import java.util.concurrent.CountDownLatch;

class Foo {
    private CountDownLatch first = new CountDownLatch(1);
    private CountDownLatch second = new CountDownLatch(1);

    public Foo() {
        
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        first.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        first.await();
        printSecond.run();
        second.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        second.await();
        printThird.run();
    }
}