package org.example.threads;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<File> files;

    public Consumer(BlockingQueue<File> files) {
        this.files = files;
    }

    @Override
    public void run() {
        while (true) {
            try {
                File file = files.take();
                System.out.println(file);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
