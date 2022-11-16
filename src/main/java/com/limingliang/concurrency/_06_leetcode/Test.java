package com.limingliang.concurrency._06_leetcode;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();

        Runnable printThird = () -> {
            System.out.print("third");
        };
        new Thread(() -> {
            try {
                foo.third(printThird);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Runnable printSecond = () -> {
            System.out.print("second");
        };
        new Thread(() -> {
            try {
                foo.second(printSecond);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Runnable printFirst = () -> {
            System.out.print("first");
        };
        new Thread(() -> {
            try {
                foo.first(printFirst);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();


    }
}
