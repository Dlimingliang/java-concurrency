package com.limingliang.concurrency._01_thread;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    @Override
    public String call() {
        return "实现Callable接口";
    }
}
