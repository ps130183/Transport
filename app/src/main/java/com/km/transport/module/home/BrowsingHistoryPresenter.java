package com.km.transport.module.home;

import com.km.transport.dto.BrowsingHistoryDto;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/21.
 */

public class BrowsingHistoryPresenter extends PresenterWrapper<BrowsingHistoryContract.View> implements BrowsingHistoryContract.Presneter {

    public BrowsingHistoryPresenter(BrowsingHistoryContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getBrowsingHistoryList(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getBrowsingHistoryList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<BrowsingHistoryDto>>() {
                    @Override
                    public void accept(@NonNull List<BrowsingHistoryDto> browsingHistoryDtos) throws Exception {
                        mView.showBrowsingHistroyList(browsingHistoryDtos,pageNo);
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
    public void clearBrowsingHistory() {
        mView.showLoading();
        mApiwrapper.clearHistoryBrowing()
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.clearSuccess();
                    }
                }));
    }
}
