package com.km.transport.event;

/**
 * Created by PengSong on 17/9/22.
 */

public class ChooseCarTypeEvent {
    private String typeName;

    public ChooseCarTypeEvent(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
