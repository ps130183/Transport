package com.km.transport.module.personal;


import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.AppVersionDto;

/**
 * Created by kamangkeji on 17/6/5.
 */

public interface SettingContract {
    interface View extends BaseView {
        void findNewVersion(AppVersionDto appVersionDto);
    }
    interface Presenter extends BasePresenter {
        void checkAppVersion(int curVersion);
    }
}
