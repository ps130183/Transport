package com.km.transport.dto;

/**
 * Created by PengSong on 17/9/27.
 */

public class MessageDto {

    private String content;
    private int type;
    private long createDate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
