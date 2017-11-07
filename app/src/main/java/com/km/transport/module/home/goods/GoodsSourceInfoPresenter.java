package com.km.transport.module.home.goods;

import com.km.transport.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/22.
 */

public class GoodsSourceInfoPresenter extends PresenterWrapper<GoodsSourceInfoContract.View> implements GoodsSourceInfoContract.Presenter {

    public GoodsSourceInfoPresenter(GoodsSourceInfoContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void confirmOrder(String demandId, String dealQuote) {
        mView.showLoading();
        mApiwrapper.confirmOrder(demandId,dealQuote)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.confirmOrderSuccess(s);
                    }
                }));
    }
}
