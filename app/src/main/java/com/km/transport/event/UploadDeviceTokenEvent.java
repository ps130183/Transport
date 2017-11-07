package com.km.transport.event;

/**
 * Created by PengSong on 17/10/20.
 */

public class UploadDeviceTokenEvent {
    private String deviceToken;

    public UploadDeviceTokenEvent(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
