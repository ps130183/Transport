package com.km.transport.module;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.UserInfoDto;

/**
 * Created by PengSong on 17/9/25.
 */

public interface MainContract {
    interface View extends BaseView{
    }
    interface Presenter extends BasePresenter{
        void getUserInfo();
        void uploadDeviceToken();
    }
}
