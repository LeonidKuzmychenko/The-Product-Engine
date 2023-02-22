package org.example.dto;

public class SearchRequest {

    private final String key;
    private final String rootPath;
    private final int depth;
    private final String mask;

    public SearchRequest(String key, String rootPath, int depth, String mask) {
        this.key = key;
        this.rootPath = rootPath;
        this.depth = depth;
        this.mask = mask;
    }

    public String getKey() {
        return key;
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

    @Override
    public String toString() {
        return "SearchRequest{" +
                "key='" + key + '\'' +
                ", rootPath='" + rootPath + '\'' +
                ", depth=" + depth +
                ", mask='" + mask + '\'' +
                '}';
    }
}
