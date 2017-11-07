package com.km.transport.event;

/**
 * Created by PengSong on 17/10/20.
 */

public class UpdateMarqueeDataEvent {
    private String content;

    public UpdateMarqueeDataEvent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
