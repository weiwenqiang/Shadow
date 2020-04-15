package com.videocut.eventbus;

public class EvnVideoPath {
    public String urlPath;
    public String filePath;
    public String fileSize;

    public EvnVideoPath() {
    }

    public EvnVideoPath(String urlPath, String filePath, String fileSize) {
        this.urlPath = urlPath;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
