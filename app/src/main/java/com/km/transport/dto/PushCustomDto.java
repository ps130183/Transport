package com.km.transport.dto;

/**
 * Created by PengSong on 17/10/20.
 */

public class PushCustomDto {
    private int type;
    private String content;
    private String id;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
