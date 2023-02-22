package org.example;

public class SearchRequest {

    private final String rootPath;
    private final int depth;
    private final String mask;

    public SearchRequest(String rootPath, int depth, String mask) {
        this.rootPath = rootPath;
        this.depth = depth;
        this.mask = mask;
    }

    public String getRootPath() {
        return rootPath;
    }

    public int getDepth() {
        return depth;
    }

    public String getMask() {
        return mask;
    }
}
