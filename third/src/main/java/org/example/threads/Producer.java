package org.example.threads;

import org.example.services.DeepFileSearcher;
import org.example.dto.SearchRequest;

import java.io.File;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Producer implements Runnable {

    private final ConcurrentHashMap<String, List<File>> files;
    private final BlockingQueue<SearchRequest> requests;
    private final DeepFileSearcher deepFileSearcher = new DeepFileSearcher();

    public Producer(BlockingQueue<SearchRequest> requests, ConcurrentHashMap<String, List<File>> files) {
        this.requests = requests;
        this.files = files;
    }

    @Override
    public void run() {
        while (true) {
            if (requests.size() != 0) {
                SearchRequest request = requests.poll();
                deepFileSearcher.search(files, request.getKey(), request.getRootPath(), request.getDepth(), request.getMask());
            }
        }

    }
}
