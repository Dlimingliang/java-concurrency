package com.limingliang.concurrency._01_thread;

public class MyThread extends Thread{

    @Override
    public void run() {
        System.out.println("继承Thread");
        super.run();
    }
}
