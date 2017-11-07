package com.km.transport.event;

import com.km.transport.entity.TruckTypeEntity;

/**
 * Created by PengSong on 17/9/20.
 */

public class CheckTruckEvent {
    private TruckTypeEntity truckLength;
    private TruckTypeEntity truckType;

    public CheckTruckEvent(TruckTypeEntity truckLength, TruckTypeEntity truckType) {
        this.truckLength = truckLength;
        this.truckType = truckType;
    }

    public TruckTypeEntity getTruckLength() {
        return truckLength;
    }

    public void setTruckLength(TruckTypeEntity truckLength) {
        this.truckLength = truckLength;
    }

    public TruckTypeEntity getTruckType() {
        return truckType;
    }

    public void setTruckType(TruckTypeEntity truckType) {
        this.truckType = truckType;
    }
}
