package com.km.transport.module.personal;

import com.km.transport.dto.UserInfoDto;
import com.km.transport.utils.Constant;
import com.km.transport.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/21.
 */

public class PersonalPresenter extends PresenterWrapper<PersonalContract.View> implements PersonalContract.Presenter {

    public PersonalPresenter(PersonalContract.View mView) {
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
        mView.showLoading();
        mApiwrapper.getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(@NonNull UserInfoDto userInfoDto) throws Exception {
                        mView.showUserInfo(userInfoDto);
                    }
                }));
    }
}
