package com.km.transport.module.home.goods;

import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.dto.HomeGoodsOrderDto;
import com.km.transport.event.HomeTabLayoutEvent;
import com.km.transport.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/19.
 */

public class GoodsSearchPresenter extends PresenterWrapper<GoodsSearchContract.View> implements GoodsSearchContract.Presenter {

    public GoodsSearchPresenter(GoodsSearchContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getHomeGoodsOrders(final int pageNo, String sourceProvince,String sourceCity,String sourceZoning,
                                   String bournProvince,String bournCity,String bournZoning, String carType, String carWidth) {
        mView.showLoading();
        mApiwrapper.getHomeGoodsOrders(pageNo,sourceProvince,sourceCity,sourceZoning,bournProvince,bournCity,bournZoning,carType, carWidth)
                .subscribe(newSubscriber(new Consumer<List<HomeGoodsOrderDto>>() {
                    @Override
                    public void accept(@NonNull List<HomeGoodsOrderDto> homeGoodsOrderDtos) throws Exception {
                        mView.showHomeGoodsOrders(homeGoodsOrderDtos,pageNo);
                    }
                }));
    }

    @Override
    public void hireGoods(String id) {
        mView.showLoading();
        mApiwrapper.hireGoods(id)
                .subscribe(newSubscriber(new Consumer<GoodsOrderDetailDto>() {
                    @Override
                    public void accept(@NonNull GoodsOrderDetailDto goodsOrderDetailDto) throws Exception {
                        mView.showGoodsOrderInfo(goodsOrderDetailDto);
                    }
                }));
    }

    @Override
    public void getMarqueeDatas() {
        mApiwrapper.getHomeMarqueeDatas()
                .subscribe(newSubscriber(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> strings) throws Exception {
                        mView.showMarqueeDatas(strings);
                    }
                }));
    }
}
