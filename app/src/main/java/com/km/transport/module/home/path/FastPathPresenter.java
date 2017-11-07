package com.km.transport.module.home.path;

import com.km.transport.dto.HomeFastPathDto;
import com.km.transport.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/20.
 */

public class FastPathPresenter extends PresenterWrapper<FastPathContract.View> implements FastPathContract.Presneter {

    public FastPathPresenter(FastPathContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getFastPathList(int pageNo) {
       mView.showLoading();
        mApiwrapper.getFastPathList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<HomeFastPathDto>>() {
                    @Override
                    public void accept(@NonNull List<HomeFastPathDto> fastPathDtos) throws Exception {
                        mView.showFastPathList(fastPathDtos);
                    }
                }));
    }

    @Override
    public void deleteFastPath(final HomeFastPathDto homeFastPathDto) {
        mView.showLoading();
        mApiwrapper.deleteFastPath(homeFastPathDto.getId())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.deleteSuccess(homeFastPathDto);
                    }
                }));
    }
}
