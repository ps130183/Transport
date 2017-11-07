package com.km.transport.module.guide;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.greendao.City;

import java.util.List;

/**
 * Created by PengSong on 17/9/18.
 */

public interface LaunchContract {
    interface View extends BaseView{
        void showChinaCitys(List<City> cities);
    }
    interface Presenter extends BasePresenter{
        void getChinaCitys();
    }
}
