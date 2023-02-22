package org.example.telnet;

import org.example.dto.SearchRequest;
import org.example.threads.Consumer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Client implements Runnable {

    private final Socket socket;
    private final ConcurrentHashMap<String, List<File>> files;
    private final BlockingQueue<SearchRequest> requests;

    public Client(Socket socket, ConcurrentHashMap<String, List<File>> files, BlockingQueue<SearchRequest> requests) {
        this.socket = socket;
        this.files = files;
        this.requests = requests;
    }

    @Override
    public void run() {
        Scanner reader;
        try {
            reader = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PrintWriter writer;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            reader.close();
            throw new RuntimeException(e);
        }

        while (reader.hasNext()) {
            writer.println("path: ");
            System.out.print("path: ");
            String path = reader.nextLine().trim();
            if (!new File(path).exists()) {
                continue;
            }
            System.out.println(path);

            writer.println("deep: ");
            System.out.print("deep: ");
            String deep = reader.nextLine().trim();
            System.out.println(deep);

            writer.println("mask: ");
            System.out.print("mask: ");
            String mask = reader.nextLine().trim();
            System.out.println(mask);

            String key = socket.getRemoteSocketAddress().toString();//TODO в идеале использовать уникальный идентификатор сессии
            System.out.println(key);

            SearchRequest searchRequest = new SearchRequest(key, path, Integer.parseInt(deep), mask);
            System.out.println(searchRequest);
            writer.println("...");
            Thread cons = new Thread(new Consumer(files, writer, key));
            cons.start();//TODO надо придумать, как уведомлять поток о том, что поиск закончен, чтобы прибить его
            requests.add(searchRequest);
        }
        reader.close();
        writer.close();
    }

}
