package org.example;

import org.example.dto.SearchRequest;
import org.example.telnet.Client;
import org.example.threads.Producer;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

//Короче, идея заключается в том, чтобы иметь один продюсер и на каждого клиента консюмер
//Продюсер пишет в мапу, где ключ это ip клиента, а значение, это список файлов, которые он нашел
//Клиент инитит запуск реквеста, который подхватывает продюсер
//Консюмер по мере наполнения мапы ее читает и пишет в консоль

//Я чет намудрил конечно, SOLID вышел из чата
//Чес слово я голые трэды пишу второй раз в жизни, Stream.parallel мое всё
//Архитектурно можно поправить сильно, но прилизывать этот код можно дольше, чем я писал его

//Мне от консоли телнета уже плохо.
//Насколько я помню, HRы сказали, что тестовое не относится к самой работе, надеюсь это так
//Сложность заключается в том, чтобы блокировки одних клиентов не аффектили других, я пока с этой проблемой не справился в полной мере
//Так же надо допилить условие окончания потока, чтобы клиент не висел на вечном цикле
//Можно зарефачить незакрывающиеся или закрывающиеся слишком поздно стримы. @Cleanup от ломбока мне в помощь
//Пообрабатывать рантайм екзепшены, а то я забил на них
//Так же можно лучше повалидировать и зациклить входные параметры, чтобы пользователь вводил их пока не будет корректно

//Если бы задача не стояла таким образом, я бы сделал намного проще архитектуру, где можно было наплодить консюмеров,
//которые читают из одной блокирующей очереди
//Да, было бы последовательно, но это позволило бы избавиться от других лишних блокировок. Тут надо тестить перформанс
//Ну и не понял прикола с всего 1 потоком продюсера, он же только читает, если его размножить, ничего не сломается

//Тестовое задание невероятного смысла жизни
public class Main {
    public static void main(String[] args) throws IOException {
        ConcurrentHashMap<String, List<File>> files = new ConcurrentHashMap<>();
        BlockingQueue<SearchRequest> requests = new LinkedBlockingQueue<>();
        Thread prod = new Thread(new Producer(requests, files));
        prod.start();
        try (ServerSocket serverSocket = new ServerSocket(9090)) {
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new Client(socket, files, requests));
                thread.start();
            }
        }
    }

}


