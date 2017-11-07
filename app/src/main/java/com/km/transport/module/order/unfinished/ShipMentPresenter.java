package com.km.transport.module.order.unfinished;

import com.km.transport.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/26.
 */

public class ShipMentPresenter extends PresenterWrapper<ShipMentContract.View> implements ShipMentContract.Presenter {

    public ShipMentPresenter(ShipMentContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void shipMentGoods(String id, String outTunnage, String sourceImage, String sourceTime) {
        mView.showLoading();
        mApiwrapper.shipMentGoods(id,outTunnage,sourceImage,sourceTime)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.shipMentSuccess();
                    }
                }));
    }

    @Override
    public void uploadPicture(String picturePath, String optionType) {
        mApiwrapper.imageUpload(optionType,picturePath)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.uploadSuccess(s);
                    }
                }));
    }
}
