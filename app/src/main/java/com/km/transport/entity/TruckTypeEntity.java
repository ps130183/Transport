package com.km.transport.entity;

/**
 * Created by kamangkeji on 17/8/22.
 */

public class TruckTypeEntity {
    private float length;
    private String type;

    private boolean isChecked;

    public TruckTypeEntity(float length, String type) {
        this.length = length;
        this.type = type;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
