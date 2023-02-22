package org.example;

import org.example.threads.Consumer;
import org.example.threads.Producer;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    //Не судите строго за незапуск с консоли, я все силы вложил в 3 уровень
    public static void main(String[] args) {
        BlockingQueue<File> files = new LinkedBlockingQueue<>();
        BlockingQueue<SearchRequest> requests = new LinkedBlockingQueue<>();

        Thread cons = new Thread(new Consumer(files));
        Thread prod = new Thread(new Producer(requests, files));

        cons.start();
        prod.start();

        try {
            Thread.sleep(2000);
            requests.add(new SearchRequest("C:\\test", 5, ""));
            Thread.sleep(2000);
            requests.add(new SearchRequest("C:\\test", 5, ""));
            Thread.sleep(2000);
            requests.add(new SearchRequest("C:\\test", 5, ""));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}