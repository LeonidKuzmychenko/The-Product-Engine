package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    //Не судите строго за незапуск с консоли, я все силы вложил в 3 уровень
    public static void main(String[] args){
        String rootPath = "C:\\test";
        int depth = 3;
        String mask = "oo";
        DeepFileSearcher fileSearcher = new DeepFileSearcher();
        List<File> search = fileSearcher.search(rootPath, depth, mask);
        System.out.println(search);
    }

}