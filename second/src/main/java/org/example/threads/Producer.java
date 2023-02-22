package org.example.threads;

import org.example.DeepFileSearcher;
import org.example.SearchRequest;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<File> files;
    private final BlockingQueue<SearchRequest> requests;
    private final DeepFileSearcher deepFileSearcher = new DeepFileSearcher();

    public Producer(BlockingQueue<SearchRequest> requests, BlockingQueue<File> files) {
        this.requests = requests;
        this.files = files;
    }

    @Override
    public void run() {
        while (true) {
            if (requests.size() != 0) {
                SearchRequest request = requests.poll();
                deepFileSearcher.search(files, request.getRootPath(), request.getDepth(), request.getMask());
            }
        }

    }
}
