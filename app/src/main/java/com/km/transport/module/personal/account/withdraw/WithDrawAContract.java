package com.km.transport.module.personal.account.withdraw;


import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.UserBalanceDto;
import com.km.transport.dto.UserInfoDto;
import com.km.transport.dto.WithDrawAccountDto;

/**
 * Created by kamangkeji on 17/4/1.
 */

public interface WithDrawAContract {
    interface View extends BaseView {
        void showBalance(UserInfoDto userInfoDto);
        void withdrawSuccess();
    }
    interface Presenter extends BasePresenter {
        void getUserBalance();
        void submitWithdraw(WithDrawAccountDto withDrawAccountDto, String money);
    }
}
