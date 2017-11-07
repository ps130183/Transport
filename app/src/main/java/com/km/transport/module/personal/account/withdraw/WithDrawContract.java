package com.km.transport.module.personal.account.withdraw;


import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.WithDrawAccountDto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/1.
 */

public interface WithDrawContract {
    interface View extends BaseView {
        void creatOrUpdateSuccess();
        void showWithDrawList(List<WithDrawAccountDto> withDrawAccountDtos);
        void deleteSuccess(WithDrawAccountDto withDrawAccountDto);
    }

    interface Presenter extends BasePresenter {
        void createWithDrawAccount(WithDrawAccountDto withDrawAccountDto);
        void updateWithDrawAccount(WithDrawAccountDto withDrawAccountDto);
        void getWithDrawList();
        void deleteWithdrawAccount(WithDrawAccountDto withDrawAccountDto);
    }
}
