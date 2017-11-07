package com.km.transport.module.home.goods;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.dto.HomeGoodsOrderDto;

import java.util.List;

/**
 * Created by PengSong on 17/9/19.
 */

public interface GoodsSearchContract {
    interface View extends BaseView{
        void showHomeGoodsOrders(List<HomeGoodsOrderDto> homeGoodsOrderDtos, int pageNo);
        void showGoodsOrderInfo(GoodsOrderDetailDto goodsOrderDetailDto);
        void showMarqueeDatas(List<String> marqueeDatas);
    }
    interface Presenter extends BasePresenter{
        void getHomeGoodsOrders(int pageNo,
                                String sourceProvince,String sourceCity,String sourceZoning,
                                String bournProvince,String bournCity,String bournZoning,
                                String carType,
                                String carWidth);
        void hireGoods(String id);

        void getMarqueeDatas();
    }
}
