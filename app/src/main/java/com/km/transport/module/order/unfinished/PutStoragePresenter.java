package com.km.transport.module.order.unfinished;

import com.km.transport.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/26.
 */

public class PutStoragePresenter extends PresenterWrapper<PutStorageContract.View> implements PutStorageContract.Presenter {

    public PutStoragePresenter(PutStorageContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void putStorageGoods(String id, String bournTunnage, String bournImage, String bournTime) {
        mView.showLoading();
        mApiwrapper.sendGoods(id,bournTunnage,bournImage,bournTime)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.putStorageSuccess();
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
