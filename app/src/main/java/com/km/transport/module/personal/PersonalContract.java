package com.km.transport.module.personal;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.UserInfoDto;
import com.tencent.connect.UserInfo;

/**
 * Created by PengSong on 17/9/21.
 */

public interface PersonalContract {
    interface View extends BaseView{
        void showUserInfo(UserInfoDto userInfoDto);
    }
    interface Presenter extends BasePresenter{
        void getUserInfo();
    }
}
