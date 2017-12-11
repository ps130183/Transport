package com.km.transport.module;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.event.DownloadAppEvent;
import com.km.transport.event.MessageUnreadStatusEvent;
import com.km.transport.event.RefreshPersonalEvent;
import com.km.transport.event.RefreshSearchGoodsEvent;
import com.km.transport.event.ShipmentEvent;
import com.km.transport.event.UserIsEmptyEvent;
import com.km.transport.module.home.HomeFragment;
import com.km.transport.module.login.LoginActivity;
import com.km.transport.module.order.OrderFragment;
import com.km.transport.module.personal.PersonalFragment;
import com.km.transport.utils.Constant;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.EventBusUtils;
import com.startsmake.mainnavigatetabbar.widget.MainNavigateTabBar;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import kr.co.namee.permissiongen.PermissionGen;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private static boolean isExsit = false;

    private String[] mNavigationTitles = {"货源","订单","我的"};
    private int[] mSelectedIcon = {R.mipmap.ic_main_home_pressed, R.mipmap.ic_main_order_pressed, R.mipmap.ic_main_personal_pressed};
    private int[] mUnSelectedIcon = {R.mipmap.ic_main_home_unpress, R.mipmap.ic_main_order_unpress, R.mipmap.ic_main_personal_unpress};

    @BindView(R.id.mainTabBar)
    MainNavigateTabBar mNavigateTabBar;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    public MainPresenter getmPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getToolBarType() {
        return TOOLBAR_TYPE_HOME;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
    }

    @Override
    protected void onCreate() {
        //退出程序
        setClickKeyCodeBackLisenter(new OnClickKeyCodeBackLisenter() {
            @Override
            public boolean onClickKeyCodeBack() {
                exsit();
                return false;
            }
        });

        requestPremission();
        //检测app版本
        EventBusUtils.post(new DownloadAppEvent(this));

        Constant.user.getDataFromSp();

        mPresenter.getUserInfo();
        mPresenter.uploadDeviceToken();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int position = intent.getIntExtra("position", -1);
        if (position >= 0) {
            mNavigateTabBar.setCurrentSelectedTab(position);
        }
    }

    private void requestPremission() {
        String[] locationPermission = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionGen.needPermission(MainActivity.this, 1, locationPermission);
    }

    private void initView(Bundle savedInstanceState){
        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[0], mSelectedIcon[0], mNavigationTitles[0]));
        mNavigateTabBar.addTab(OrderFragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[1], mSelectedIcon[1], mNavigationTitles[1]));
        mNavigateTabBar.addTab(PersonalFragment.class, new MainNavigateTabBar.TabParam(mUnSelectedIcon[2], mSelectedIcon[2], mNavigationTitles[2]));

        mNavigateTabBar.setTabSelectListener(new MainNavigateTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(MainNavigateTabBar.ViewHolder holder) {
                if (holder.tabIndex > 0 && Constant.user.isEmpty()){//我的
                    toNextActivity(LoginActivity.class);
                }

                switch (holder.tabIndex){
                    case 0:
                        EventBusUtils.post(new RefreshSearchGoodsEvent());
                        break;
                    case 1:
                        EventBusUtils.post(new ShipmentEvent());
                        break;
                    case 2:
                        EventBusUtils.post(new RefreshPersonalEvent());
                        break;
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userNotLogin(UserIsEmptyEvent emptyEvent){
        Constant.user.clear();
        toNextActivity(LoginActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setUnreadStatus(MessageUnreadStatusEvent event){
        mNavigateTabBar.setUnreadShowStatus(2,event.getUnreadNums() > 0);
    }

    /**
     * 退出
     */
    private void exsit() {
        if (isExsit) {
            MobclickAgent.onKillProcess(this);
            finish();
            System.exit(0);
        } else {
            isExsit = true;
            showToast("再按一次退出程序");
            Observable.timer(2, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                            isExsit = false;
                        }
                    });
        }
    }
}
