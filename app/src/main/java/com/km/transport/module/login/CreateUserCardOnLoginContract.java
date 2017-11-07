package com.km.transport.module.login;


import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;

/**
 * Created by kamangkeji on 17/9/4.
 */

public interface CreateUserCardOnLoginContract {
    interface View extends BaseView {
        void createUserCardSuccess(String token);
    }

    interface Presenter extends BasePresenter {
        void createUserCard(String name, String position, String phone);
    }
}
