package com.km.transport.module.order;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.GoodsOrderDetailDto;

import java.util.List;

/**
 * Created by PengSong on 17/9/26.
 */

public interface GoodsOrderContract {
    interface View extends BaseView{
        void showGoodsOrders(List<GoodsOrderDetailDto> goodsOrderDetailDtos,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getGoodsOrder(int type,int pageNo);
    }
}
