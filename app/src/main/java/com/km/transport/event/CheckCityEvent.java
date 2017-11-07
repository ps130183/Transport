package com.km.transport.event;

import com.km.transport.greendao.City;

/**
 * Created by PengSong on 17/9/20.
 */

public class CheckCityEvent {

    private int cityType;
    private City[] checkedCites;

    public CheckCityEvent(int cityType, City[] checkedCites) {
        this.cityType = cityType;
        this.checkedCites = checkedCites;
    }

    public int getCityType() {
        return cityType;
    }

    public void setCityType(int cityType) {
        this.cityType = cityType;
    }

    public City[] getCheckedCites() {
        return checkedCites;
    }

    public void setCheckedCites(City[] checkedCites) {
        this.checkedCites = checkedCites;
    }
}
