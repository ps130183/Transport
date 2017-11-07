package com.km.transport.module.home.goods;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;

/**
 * Created by PengSong on 17/9/22.
 */

public interface GoodsSourceInfoContract {
    interface View extends BaseView{
        void confirmOrderSuccess(String orderId);
    }
    interface Presenter extends BasePresenter{
        void confirmOrder(String demandId,String dealQuote);
    }
}
