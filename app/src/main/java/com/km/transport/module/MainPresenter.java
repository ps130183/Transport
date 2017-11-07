package com.km.transport.module;

import com.km.transport.dto.UserInfoDto;
import com.km.transport.event.MessageUnreadStatusEvent;
import com.km.transport.utils.Constant;
import com.km.transport.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.SPUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/25.
 */

public class MainPresenter extends PresenterWrapper<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(MainContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getUserInfo() {
        if (Constant.user.isEmpty()){
            return;
        }
        mApiwrapper.getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(@NonNull UserInfoDto userInfoDto) throws Exception {
                        Constant.userInfo = userInfoDto;
                        EventBusUtils.post(new MessageUnreadStatusEvent(userInfoDto.getReadStatus()));
                    }
                }));
    }

    @Override
    public void uploadDeviceToken() {
        if (Constant.user.isEmpty()){
            return;
        }
        boolean updateDeviceToken = SPUtils.getInstance().getBoolean("updateDeviceToken");
        if (updateDeviceToken){
            String deviceToken = SPUtils.getInstance().getString("deviceToken");
            mApiwrapper.uploadDeviceToken(deviceToken)
                    .subscribe(newSubscriber(new Consumer<String>() {
                        @Override
                        public void accept(@NonNull String s) throws Exception {
                            Logger.d("上传友盟deviceToken成功");
                        }
                    }));
        }
    }
}
