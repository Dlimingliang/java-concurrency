package com.limingliang.concurrency._06_leetcode;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();

        Runnable printThird = () -> {
            System.out.print("third");
        };
        foo.third(printThird);

        Runnable printSecond = () -> {
            System.out.print("second");
        };
        foo.second(printSecond);

        Runnable printFirst = () -> {
            System.out.print("first");
        };
        foo.first(printFirst);

        System.out.print("\n");
    }
}
