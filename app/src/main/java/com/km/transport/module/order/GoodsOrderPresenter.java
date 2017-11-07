package com.km.transport.module.order;

import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/26.
 */

public class GoodsOrderPresenter extends PresenterWrapper<GoodsOrderContract.View> implements GoodsOrderContract.Presenter {

    public GoodsOrderPresenter(GoodsOrderContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getGoodsOrder(int type, final int pageNo) {
        mView.showLoading();
        mApiwrapper.getGoodsOrders(type,pageNo)
                .subscribe(newSubscriber(new Consumer<List<GoodsOrderDetailDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsOrderDetailDto> goodsOrderDetailDtos) throws Exception {
                        mView.showGoodsOrders(goodsOrderDetailDtos,pageNo);
                    }
                }));
    }
}
