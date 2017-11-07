package com.km.transport.module.personal.approve;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.UserInfoDto;

/**
 * Created by PengSong on 17/9/22.
 */

public interface ApproveDriverInfoContract {
    interface View extends BaseView{
        void uploadSuccess(String resultUrl);
        void submitSuccess();
    }
    interface Presenter extends BasePresenter{
        void uploadPicture(String picturePath,String optionType);
        void submitUserInfo(UserInfoDto userInfoDto);
    }
}
