package com.km.transport.module.home.path.newpath;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;

/**
 * Created by PengSong on 17/9/20.
 */

public interface AddFastPathContract {
    interface View extends BaseView{
        void addFastPathSuccess();
    }
    interface Presenter extends BasePresenter{
        void addFastPath(String sourceProvince,String sourceCity,String sourceZoning,
                         String bournProvince,String bournCity,String bournZoning,
                         String carType,
                         String carWidth);
    }
}
