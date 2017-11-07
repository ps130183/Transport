package com.km.transport.module.personal;


import com.km.transport.dto.AppVersionDto;
import com.km.transport.module.MainActivity;
import com.km.transport.utils.retrofit.PresenterWrapper;
import com.km.transport.utils.retrofit.RetrofitUtil;
import com.ps.androidlib.utils.AppUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by kamangkeji on 17/6/5.
 */

public class SettingPresenter extends PresenterWrapper<SettingContract.View> implements SettingContract.Presenter {

    public SettingPresenter(SettingContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void checkAppVersion(int curVersion) {
        mApiwrapper.checkAppVersion(curVersion)
                .subscribe(new DisposableSubscriber<AppVersionDto>() {
                    @Override
                    public void onNext(AppVersionDto appVersionDto) {
                        mView.findNewVersion(appVersionDto);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
