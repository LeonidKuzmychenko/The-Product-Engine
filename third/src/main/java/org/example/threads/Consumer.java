package org.example.threads;

import org.example.utils.Utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Consumer implements Runnable {

    private final ConcurrentHashMap<String, List<File>> files;
    private final PrintWriter writer;

    private final String key;

    public Consumer(ConcurrentHashMap<String, List<File>> files, PrintWriter writer, String key) {
        this.files = files;
        this.writer = writer;
        this.key = key;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (Utils.ANCHOR){
                List<File> list = files.get(key);
                if (list != null){
                    files.put(key, new ArrayList<>());
                    for (File file : list) {
                        System.out.println(file);
                        writer.println(file.getAbsolutePath());
                        writer.flush();
                    }
                }
            }
        }
    }

}
