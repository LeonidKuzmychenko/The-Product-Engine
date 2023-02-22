package org.example.services;

import org.example.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DeepFileSearcher {

    public void search(Map<String, List<File>> files, String key, String rootPath, int depth, String mask) {
        Stream<Path> paths = null;
        try {
            paths = Files.list(Path.of(rootPath));
            for (int i = 0; i < depth + 1; i++) {
                paths = deepFileSearch(files, key, paths, i, depth, mask);
            }
            paths.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (paths != null) {
                paths.close();
            }
        }
    }

    private Stream<Path> deepFileSearch(Map<String, List<File>> files, String key, Stream<Path> paths, int currentDeep, int maxDeep, String mask) {
        return paths.flatMap(it -> {//TODO как тебе такое, Илон Маск
            File file = it.toFile();
            if (file.getName().contains(mask)) {
                synchronized (Utils.ANCHOR) {
                    List<File> list = files.getOrDefault(key, new ArrayList<>());
                    list.add(file);
                    files.put(key, list);
                }
            }
            if (file.isDirectory() && currentDeep != maxDeep) {
                return getPathsList(file.getAbsolutePath());
            }
            return Stream.empty();
        });
    }

    private Stream<Path> getPathsList(String path) {
        try {
            return Files.list(Path.of(path));
        } catch (IOException e) {
            return Stream.empty();
        }
    }

}
