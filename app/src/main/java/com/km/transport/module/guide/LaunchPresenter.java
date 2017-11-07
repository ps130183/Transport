package com.km.transport.module.guide;

import com.km.transport.greendao.City;
import com.km.transport.greendao.DaoSession;
import com.km.transport.greendao.GreenDbManager;
import com.km.transport.utils.retrofit.PresenterWrapper;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 17/9/18.
 */

public class LaunchPresenter extends PresenterWrapper<LaunchContract.View> implements LaunchContract.Presenter {

    public LaunchPresenter(LaunchContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getChinaCitys() {
        mApiwrapper.getChinaCitysInfo()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<List<City>>() {
                    @Override
                    public void accept(@NonNull List<City> cities) throws Exception {
                        DaoSession daoSession = GreenDbManager.getInstances().getDaoSession();
                        daoSession.getCityDao().insertInTx(cities);
                        Logger.d("****************** 来自服务器的数据 ************************");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(newSubscriber(new Consumer<List<City>>() {
                    @Override
                    public void accept(@NonNull List<City> cities) throws Exception {
                        mView.showChinaCitys(cities);
                    }
                }));
    }
}
