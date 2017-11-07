package com.km.transport.module.personal.account.withdraw;

import com.km.transport.dto.UserBalanceDto;
import com.km.transport.dto.UserInfoDto;
import com.km.transport.dto.WithDrawAccountDto;
import com.km.transport.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class WithDrawAPresenter extends PresenterWrapper<WithDrawAContract.View> implements WithDrawAContract.Presenter {


    public WithDrawAPresenter(WithDrawAContract.View mView) {
        super(mView);
    }

    @Override
    public void getUserBalance() {
        mApiwrapper.getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(@NonNull UserInfoDto userInfoDto) throws Exception {
                        mView.showBalance(userInfoDto);
                    }
                }));
    }

    @Override
    public void submitWithdraw(WithDrawAccountDto withDrawAccountDto, String money) {
        mView.showLoading();
        mApiwrapper.submiWithDraw(withDrawAccountDto.getId(),money)
                .subscribe(newSubscriber(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        mView.withdrawSuccess();
                    }
                }));
    }


    @Override
    public void onCreateView() {
        getUserBalance();
    }
}
