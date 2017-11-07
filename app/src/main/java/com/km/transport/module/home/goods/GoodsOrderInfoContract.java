package com.km.transport.module.home.goods;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.GoodsOrderDetailDto;

/**
 * Created by PengSong on 17/9/25.
 */

public interface GoodsOrderInfoContract {
    interface View extends BaseView{
        void showGoodsOrderInfo(GoodsOrderDetailDto orderDetailDto);
        void cancelOrderSuccess();
    }
    interface Presenter extends BasePresenter{
        void getGoodsOrderInfo(String orderId);
        void cancelOrder(String id);
    }
}
