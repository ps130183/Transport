package com.km.transport.event;

import com.km.transport.greendao.City;

/**
 * Created by PengSong on 17/9/22.
 */

public class ChooseCarAddressEvent {
    private City[] chooseCitys;

    public ChooseCarAddressEvent(City[] chooseCitys) {
        this.chooseCitys = chooseCitys;
    }

    public City[] getChooseCitys() {
        return chooseCitys;
    }

    public void setChooseCitys(City[] chooseCitys) {
        this.chooseCitys = chooseCitys;
    }
}
