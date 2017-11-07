package com.km.transport.event;

/**
 * Created by PengSong on 17/10/19.
 */

public class MessageUnreadStatusEvent {
    private int unreadNums;

    public MessageUnreadStatusEvent(int unreadNums) {
        this.unreadNums = unreadNums;
    }

    public int getUnreadNums() {
        return unreadNums;
    }
}
