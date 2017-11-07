package com.km.transport.module.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.dto.AppVersionDto;
import com.km.transport.event.DownloadAppEvent;
import com.km.transport.module.login.LoginActivity;
import com.km.transport.utils.Constant;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DataCleacManager;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.rey.material.widget.Switch;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.tv_find_new_version)
    TextView tvFindNewVersion;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "设置";
    }

    @Override
    public SettingPresenter getmPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected void onCreate() {
//        swichUsercard.setChecked(Constant.isAllowUserCard);
//
//        swichUsercard.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(Switch view, boolean checked) {
//                mPresenter.updateAllowUserCard(checked);
//            }
//        });
        mPresenter.checkAppVersion(AppUtils.getAppVersionCode(this));

        try {
            tvCacheSize.setText(DataCleacManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_clear_cache)
    public void clearCache(View view){
//        SPUtils.getInstance().clear();
        DialogUtils.showDefaultAlertDialog("是否清除缓存？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                Observable.just(1)
                        .doOnNext(new Consumer<Integer>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                                DataCleacManager.clearAllCache(SettingActivity.this);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                                Logger.d("缓存已清空");
                                try {
                                    tvCacheSize.setText(DataCleacManager.getTotalCacheSize(SettingActivity.this));
                                    showToast("缓存已清除");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });

    }

//    @OnClick(R.id.tv_push_message_setting)
//    public void pushMessageSetting(View view){
//        showToast("设置缓存推送消息");
//    }

//    @OnClick(R.id.tv_about)
//    public void aboutMe(View view){
//        showToast("关于我们");
//        toNextActivity(AboutMeActivity.class);
//    }

//    @OnClick(R.id.tv_user_agreement)
//    public void userAgreement(View view){
//        Bundle bundle = new Bundle();
//        bundle.putString("titleName","用户协议");
//        bundle.putString("agreementUrl","/member/agreement/view");
//        toNextActivity(AgreementActivity.class,bundle);
//    }

    @OnClick(R.id.tv_logout)
    public void logout(View view){
        DialogUtils.showDefaultAlertDialog("是否要退出登录", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
//                showToast("退出登录");
                Constant.user.clear();
//                EventBusUtils.post(new LogoutEntity(true));
                //此方法为异步方法
//                EMClient.getInstance().logout(true, new EMCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        Logger.d("已退出环信聊天");
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//                        Logger.d("退出环信聊天失败");
//                    }
//
//                    @Override
//                    public void onProgress(int i, String s) {
//
//                    }
//                });
                toNextActivity(LoginActivity.class);
            }
        });
    }

    /**
     * 检测app版本
     * @param view
     */
    @OnClick(R.id.tv_update)
    public void updateApp(View view){
        EventBusUtils.post(new DownloadAppEvent(this));
    }


    @Override
    public void findNewVersion(AppVersionDto appVersionDto) {
        tvFindNewVersion.setVisibility(View.VISIBLE);
        tvFindNewVersion.setText("发现新版本：V" + appVersionDto.getVersionView());
    }
}
