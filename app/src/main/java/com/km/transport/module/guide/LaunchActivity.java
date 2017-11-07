package com.km.transport.module.guide;

import android.content.Intent;
import android.os.Build;

import com.km.transport.module.MainActivity;
import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.greendao.City;
import com.km.transport.greendao.GreenDbManager;
import com.km.transport.utils.selectcity.CityPickData;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.SPUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LaunchActivity extends BaseActivity<LaunchPresenter> implements LaunchContract.View {

    @Override
    protected int getContentView() {
        return R.layout.activity_launch;
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected int getToolBarType() {
        return BaseActivity.TOOLBAR_TYPE_HOME;
    }

    @Override
    public LaunchPresenter getmPresenter() {
        return new LaunchPresenter(this);
    }

    @Override
    protected void onCreate() {
        List<City> dbCityList = GreenDbManager.getInstances().getDaoSession().getCityDao().loadAll();
        if (dbCityList == null || dbCityList.size() == 0){
            mPresenter.getChinaCitys();
        } else {
            Logger.d("****************** 来自本地数据库的数据 ************************");
            showChinaCitys(dbCityList);
        }
    }

    @Override
    public void showChinaCitys(List<City> cities) {
        Logger.d(cities.toString());
        init();
    }

    private void init(){
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        boolean isFirst = SPUtils.getInstance().getBoolean("isFirst",true);
                        if (isFirst && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            startActivity(new Intent(LaunchActivity.this,GuideActivity.class));
                        } else {
                            startActivity(new Intent(LaunchActivity.this,MainActivity.class));
                        }
                        finish();
                    }
                });
    }
}
