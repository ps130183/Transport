package com.km.transport.module.personal.account;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.UserAccountDetailDto;
import com.km.transport.dto.UserInfoDto;

import java.util.List;

/**
 * Created by PengSong on 17/9/28.
 */

public interface MyAccountContract {
    interface View extends BaseView{
        void showAccountDetails(List<UserAccountDetailDto> accountDetailDtos,int pageNo);
        void showUserInfo(UserInfoDto userInfoDto);
    }
    interface Presenter extends BasePresenter{
        void getAccountDetails(int pageNo);
        void getUserInfo();
    }
}
