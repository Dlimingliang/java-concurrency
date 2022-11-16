package com.limingliang.concurrency._05_producer_consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ProductorConsumer {

    private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Productor p1 = new Productor(1, queue);
        Consumer c1 = new Consumer(1, queue);
        Consumer c2 = new Consumer(2, queue);

        ExecutorService service = Executors.newFixedThreadPool(5);
        service.execute(c1);
        service.execute(c2);
        service.execute(p1);
        service.shutdown();
    }

    static class Productor implements  Runnable {

        private int id;
        private BlockingQueue queue;

        public Productor(int id, BlockingQueue queue) {
            this.id = id;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Random random = new Random();
                    int i = random.nextInt();
                    System.out.printf("Producer %d produced %d%n", id, i);
                    queue.put(i);
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static class Consumer implements Runnable {

        private int id;
        private BlockingQueue queue;
        Random random = new Random();

        public Consumer(int id, BlockingQueue queue) {
            this.id = id;
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Integer element = (Integer) queue.take();
                    System.out.printf("Consumer %d consumed %d%n", id, element);
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
