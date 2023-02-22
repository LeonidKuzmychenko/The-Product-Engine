package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DeepFileSearcher {

    public List<File> search(String rootPath, int depth, String mask) {
        List<File> files = new ArrayList<>();
        Stream<Path> paths = null;
        try {
            paths = Files.list(Path.of(rootPath));
            for (int i = 0; i < depth + 1; i++) {
                paths = deepFileSearch(files, paths, i, depth, mask);
            }
            paths.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (paths != null) {
                paths.close();
            }
        }
        return files;
    }

    private Stream<Path> deepFileSearch(List<File> files, Stream<Path> paths, int currentDeep, int maxDeep, String mask) {
        return paths.flatMap(it -> {
            File file = it.toFile();
            if (file.getName().contains(mask)){
                files.add(file);
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
