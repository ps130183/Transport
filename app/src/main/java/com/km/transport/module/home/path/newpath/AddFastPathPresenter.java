package com.km.transport.module.home.path.newpath;

import com.km.transport.utils.retrofit.PresenterWrapper;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/20.
 */

public class AddFastPathPresenter extends PresenterWrapper<AddFastPathContract.View> implements AddFastPathContract.Presenter {

    public AddFastPathPresenter(AddFastPathContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void addFastPath(String sourceProvince, String sourceCity, String sourceZoning, String bournProvince, String bournCity, String bournZoning, String carType, String carWidth) {
        mView.showLoading();
        mApiwrapper.addFastPath(sourceProvince,sourceCity,sourceZoning,
                bournProvince,bournCity,bournZoning,carType,carWidth)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.addFastPathSuccess();
                    }
                }));
    }
}
