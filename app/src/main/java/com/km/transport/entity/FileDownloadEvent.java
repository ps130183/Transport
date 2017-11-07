package com.km.transport.entity;

/**
 * Created by kamangkeji on 17/4/17.
 */

public class FileDownloadEvent {
    long total;
    long bytesLoaded;

    public long getBytesLoaded() {
        return bytesLoaded;
    }

    public long getTotal() {
        return total;
    }

    public FileDownloadEvent(long total, long bytesLoaded) {
        this.total = total;
        this.bytesLoaded = bytesLoaded;
    }
}
