package com.km.transport.module.login;


import com.km.transport.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/9/4.
 */

public class CreateUserCardOnLoginPresenter extends PresenterWrapper<CreateUserCardOnLoginContract.View> implements CreateUserCardOnLoginContract.Presenter {

    public CreateUserCardOnLoginPresenter(CreateUserCardOnLoginContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void createUserCard(String name, String position, String phone) {
//        mView.showLoading();
    }
}
