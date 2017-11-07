package com.km.transport.module.login;

import android.text.TextUtils;


import com.km.transport.dto.UserDto;
import com.km.transport.utils.Constant;
import com.km.transport.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.SPUtils;
import com.umeng.analytics.MobclickAgent;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/3/21.
 */

public class LoginPresenter extends PresenterWrapper<LoginContract.View> implements LoginContract.Presenter {


    public LoginPresenter(LoginContract.View mView) {
        super(mView);
    }

    @Override
    public void login(final String mobilePhone, String passWord) {
        mView.showLoading();
        mApiwrapper.login(mobilePhone,passWord)
                .subscribe(newSubscriber(new Consumer<UserDto>() {
                    @Override
                    public void accept(@NonNull UserDto userDto) throws Exception {
                        userDto.saveToSp();
                        Constant.user.getDataFromSp();
                        MobclickAgent.onProfileSignIn(mobilePhone);
                        mView.loginSuccess();

                        String deviceToken = SPUtils.getInstance().getString("deviceToken");
                        if (!TextUtils.isEmpty(deviceToken)){
                            mApiwrapper.uploadDeviceToken(deviceToken)
                                    .subscribe(newSubscriber(new Consumer<String>() {
                                        @Override
                                        public void accept(@NonNull String s) throws Exception {
                                            Logger.d("上传友盟deviceToken成功");
                                        }
                                    }));
                        }

                    }

                }));
    }

    @Override
    public void getPhoneCode(String phone) {
        mView.showLoading();
        mApiwrapper.getPhoneCode(phone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String defaultDto) throws Exception {
                        mView.getPhoneCodeSuccess();
                    }
                }));
    }

    @Override
    public void onCreateView() {

    }
}
