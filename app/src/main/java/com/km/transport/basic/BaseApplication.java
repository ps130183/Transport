package com.km.transport.basic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;
import com.km.transport.dto.PushCustomDto;
import com.km.transport.event.UpdateMarqueeDataEvent;
import com.km.transport.greendao.GreenDbManager;
import com.km.transport.module.home.goods.GoodsOrderInfoActivity;
import com.km.transport.service.UmengPushReveiveService;
import com.km.transport.utils.UmengShareUtils;
import com.km.transport.utils.crash.CrashHandler;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.SPUtils;
import com.ps.androidlib.utils.Utils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;


/**
 * Created by kamangkeji on 17/2/11.
 */

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication mInstance;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        initCrashHandler();
        initLogUtils();
        initDateBase();
        registCallback();
        initUtils();
        UmengShareUtils.initUmengShare(this);
        initEaseUI();
        initPush();
        initBaiduMap();
//        RxBus.config(AndroidSchedulers.mainThread());
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }


    /**
     * 初始化 异常捕获
     */
    private void initCrashHandler() {
        CrashHandler.getInstance().init(this);
    }

    /**
     * 初始化日志打印
     */
    private void initLogUtils() {
        Logger.init("Transport")               // default tag : PRETTYLOGGER or use just init()
                .setMethodCount(2)            // default 2
//                .hideThreadInfo()           // default it is shown
                .setLogLevel(LogLevel.FULL);  // default : LogLevel.FULL
//        Logger.d("initLogUtils");
    }

    /**
     * 弹出提示框 初始化
     */
    private void registCallback() {
        StyledDialog.init(this);
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActyManager.getInstance().setCurrentActivity(activity);

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initUtils() {
        Utils.init(this);
//        SPUtils spUtils = new SPUtils("RMbank");
    }

    private void initEaseUI() {
//        if (EaseUI.getInstance().init(this,null)){
//            EMClient.getInstance().setDebugMode(true);
//            EaseUserChatUtils.init();
//        }
    }

    /**
     * 初始化 推送
     */
    private void initPush() {
        final PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Logger.d("友盟推送 初始化成功 ---- " + deviceToken);
                String spDeviceToken = SPUtils.getInstance().getString("deviceToken");
                if (TextUtils.isEmpty(spDeviceToken) || !deviceToken.equals(spDeviceToken)){
                    SPUtils.getInstance().put("deviceToken",deviceToken);
                    SPUtils.getInstance().put("updateDeviceToken",true);
                } else {
                    SPUtils.getInstance().put("updateDeviceToken",false);
                }
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.d("友盟推送 初始化失败 ---- " + s + "   " + s1);
            }
        });

        //自定义 通知行为
        UmengNotificationClickHandler clickHandler = new UmengNotificationClickHandler(){
            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                if (!TextUtils.isEmpty(uMessage.custom)){
                    Logger.d(uMessage.custom);
                    Gson gson = new Gson();
                    PushCustomDto customDto = gson.fromJson(uMessage.custom,PushCustomDto.class);
                    switch (customDto.getType()){
                        case 1://抢到订单
                            Intent intent = new Intent(mInstance, GoodsOrderInfoActivity.class);
                            intent.putExtra("orderId",customDto.getId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            break;
                    }
                }
            }
        };
        mPushAgent.setNotificationClickHandler(clickHandler);

        //自定义消息
        UmengMessageHandler messageHandler = new UmengMessageHandler(){
            @Override
            public void dealWithCustomMessage(Context context, UMessage uMessage) {
                // 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
                boolean isClickOrDismissed = true;
                if(isClickOrDismissed) {
                    //自定义消息的点击统计
                    UTrack.getInstance(getApplicationContext()).trackMsgClick(uMessage);
                } else {
                    //自定义消息的忽略统计
                    UTrack.getInstance(getApplicationContext()).trackMsgDismissed(uMessage);
                }
                if (!TextUtils.isEmpty(uMessage.custom)){
                    Logger.d(uMessage.custom);
                    Gson gson = new Gson();
                    PushCustomDto customDto = gson.fromJson(uMessage.custom,PushCustomDto.class);
                    switch (customDto.getType()){
                        case 1://首页跑马灯
                            EventBusUtils.post(new UpdateMarqueeDataEvent(customDto.getContent()));
                            break;
                    }
                }

            }
        };
        mPushAgent.setMessageHandler(messageHandler);
        mPushAgent.setPushIntentServiceClass(null);
//        mPushAgent.setPushIntentServiceClass(UmengPushReveiveService.class);
    }

    /**
     * 初始化本地数据库
     */
    private void initDateBase() {
        GreenDbManager.getInstances().initDbHelp(this);
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap(){
        SDKInitializer.initialize(this);
    }
}
