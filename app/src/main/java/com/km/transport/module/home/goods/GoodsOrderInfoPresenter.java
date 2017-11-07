package com.km.transport.module.home.goods;

import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/25.
 */

public class GoodsOrderInfoPresenter extends PresenterWrapper<GoodsOrderInfoContract.View> implements GoodsOrderInfoContract.Presenter {

    public GoodsOrderInfoPresenter(GoodsOrderInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getGoodsOrderInfo(String orderId) {
        mView.showLoading();
        mApiwrapper.getOrderInfo(orderId)
                .subscribe(newSubscriber(new Consumer<GoodsOrderDetailDto>() {
                    @Override
                    public void accept(@NonNull GoodsOrderDetailDto goodsOrderDetailDto) throws Exception {
                        mView.showGoodsOrderInfo(goodsOrderDetailDto);
                    }
                }));
    }

    @Override
    public void cancelOrder(String id) {
        mView.showLoading();
        mApiwrapper.cancelOrder(id)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.cancelOrderSuccess();
                    }
                }));
    }
}
