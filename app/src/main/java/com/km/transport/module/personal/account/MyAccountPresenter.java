package com.km.transport.module.personal.account;

import com.km.transport.dto.UserAccountDetailDto;
import com.km.transport.dto.UserInfoDto;
import com.km.transport.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/28.
 */

public class MyAccountPresenter extends PresenterWrapper<MyAccountContract.View> implements MyAccountContract.Presenter {

    public MyAccountPresenter(MyAccountContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getAccountDetails(final int pageNo) {
        mApiwrapper.getAccountDetails(pageNo)
                .subscribe(newSubscriber(new Consumer<List<UserAccountDetailDto>>() {
                    @Override
                    public void accept(@NonNull List<UserAccountDetailDto> accountDetailDtos) throws Exception {
                        mView.showAccountDetails(accountDetailDtos,pageNo);
                    }
                }));
    }

    @Override
    public void getUserInfo() {
        mApiwrapper.getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(@NonNull UserInfoDto userInfoDto) throws Exception {
                        mView.showUserInfo(userInfoDto);
                    }
                }));
    }
}
