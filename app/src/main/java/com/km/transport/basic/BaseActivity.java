package com.km.transport.basic;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.km.transport.R;
import com.km.transport.api.ApiWrapper;
import com.km.transport.dto.AppVersionDto;
import com.km.transport.event.DownloadAppEvent;
import com.km.transport.module.MainActivity;
import com.km.transport.titlebar.ToolBarTitle;
import com.km.transport.utils.retrofit.RetrofitUtil;
import com.laojiang.retrofithttp.weight.downfilesutils.FinalDownFiles;
import com.laojiang.retrofithttp.weight.downfilesutils.action.FinalDownFileResult;
import com.laojiang.retrofithttp.weight.downfilesutils.downfiles.DownInfo;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.DialogLoading;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.StatusBarUtil;
import com.ps.androidlib.utils.ViewUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;
import kr.co.namee.permissiongen.PermissionGen;
//import rx.Subscriber;
//import rx.functions.Action0;
//import rx.functions.Action1;
//import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {


    protected static final int TOOLBAR_TYPE_DEFAULT = 0;
    protected static final int TOOLBAR_TYPE_HOME = 1;

    public static BaseActivity context = null;
    private static final int DEFAULT_CENTERVIEW_VISIABLE_POSITION = 0;

    protected P mPresenter;
    protected SwipeRefreshLayout mSwipeRefresh;

    FrameLayout fltitle;
    FrameLayout flContent;


    private List<CenterViewInterface> centerViews;

    private TitleBarInterface titleBar;

    private OnClickKeyCodeBackLisenter clickKeyCodeBackLisenter;

    /**
     * 使用CompositeSubscription来持有f所有的Subscriptions
     */
    protected CompositeDisposable mCompositeSubscription;
    protected ApiWrapper apiWrapper;
    private DialogLoading loading;//加载提示框
    protected Toast mToast = null;//提示框


//    private EMUtils.EMMessageListener emMessageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        int toolbarType = getToolBarType();
        EventBusUtils.register(this);
        context = this;
        mCompositeSubscription = new CompositeDisposable();
        apiWrapper = ApiWrapper.getInstance();
        mPresenter = getmPresenter();//mPresenter
//        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiper_refresh);
        //友盟推送 统计
        PushAgent.getInstance(context).onAppStart();
        if (toolbarType == TOOLBAR_TYPE_DEFAULT) {
            setContentView(R.layout._baseview);
            initDefaultView();
        } else if (toolbarType == TOOLBAR_TYPE_HOME) {
            setContentView(R.layout.activity_base_home);
        }
        StatusBarUtil.StatusBarLightMode(context);//设置状态栏 字体颜色为深色
        //设置页面主内容布局
        View viewContent = ViewUtils.getView(context, getContentView());
        flContent = (FrameLayout) findViewById(R.id.fl_content);
        flContent.addView(viewContent);
        ButterKnife.bind(context);
//        StatusBarUtil.initState(context);
        onCreate();
        if (mPresenter != null) {
            mPresenter.onCreateView();
        }

//        if (!MainActivity.class.equals(this.getClass())){
//            //设置环信连接监听  检测
//            EMUtils.setEMConnectionListener(this,true);
//        }
    }

    /**
     * 初始化默认的view
     */
    private void initDefaultView() {
        titleBar = getTitleBar();//标题栏
        setViewContent();

//        userDao = new UserDao(context);
        initCenterViewOfTitle();
        initToolBar();
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏 强制
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        //session的统计
        MobclickAgent.onResume(this);
        //注册消息监听
//        if (!EaseChatActivity.class.equals(this.getClass())){
//            emMessageListener = EMUtils.setMessageReceiveListener(this,true);
//        }
//        if (mPresenter != null) {
//            mPresenter.onCreateView();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //session的统计
        MobclickAgent.onPause(this);
        //注销消息监听
//        if (emMessageListener != null && !EaseChatActivity.class.equals(this.getClass())){
//            EMUtils.removeMessageReceiverListener(emMessageListener);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //一旦调用了 CompositeSubscription.unsubscribe()，这个CompositeSubscription对象就不可用了,
        // 如果还想使用CompositeSubscription，就必须在创建一个新的对象了。
        mCompositeSubscription.clear();
        EventBusUtils.unregister(this);
    }

    @Override
    public Context getMContext() {
        return this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * _toolbar 类型
     *
     * @return
     */
    protected int getToolBarType() {
        return TOOLBAR_TYPE_DEFAULT;
    }

    /**
     * 返回标题栏布局
     *
     * @return
     */
    protected int getTitleView() {
        return R.layout._toolbar;
    }


    /**
     * 页面主要内容布局
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 标题栏控制类
     *
     * @return
     */
    protected TitleBarInterface getTitleBar() {
        return new ToolBarTitle(false);
    }


    /**
     * 标题名
     *
     * @return
     */
    protected abstract String getTitleName();

    public P getmPresenter() {
        return null;
    }

    ;

    protected abstract void onCreate();

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    /**
     * 设置布局
     */
    private void setViewContent() {
        if (titleBar != null) {
            //设置标题栏布局  默认是toolbar.xml
            fltitle = (FrameLayout) findViewById(R.id.fl_title);
            View titleView = ViewUtils.getView(context, getTitleView());
            fltitle.addView(titleView);
            titleBar.bindViewByRes(titleView);
        }

    }


    /**
     * 初始化toolBar
     */
    private void initToolBar() {
        if (titleBar == null) {
            return;
        }
        if (titleBar.isSetTitle()) {
            titleBar.setTitleName(getTitleName());
        } else {
            titleBar.setCenterView(getCenterView(DEFAULT_CENTERVIEW_VISIABLE_POSITION));
        }
        setLeftIconClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setRightIconClick(null);
    }

    protected
    @DrawableRes
    int getBackIconRes() {
        return R.mipmap.ic_left_back_block;
    }

    protected int getRightIconRes() {
        return 0;
    }

    protected
    @NonNull
    List<CenterViewInterface> getCenterViews() {
        return new ArrayList<>();
    }

    /**
     * 设置toolBar左侧按钮的点击事件
     *
     * @param leftIconOnclick
     */
    public void setLeftIconClick(View.OnClickListener leftIconOnclick) {
        setLeftIconClick(getBackIconRes(), leftIconOnclick);
    }

    public void setLeftIconClick(int iconRes, View.OnClickListener leftIconOnClick) {
        if (titleBar != null) {
            titleBar.setLeftButtton(iconRes, leftIconOnClick);
        }
    }

    public void setRightIconClick(int iconRes, View.OnClickListener rightIconOnClick) {
        if (titleBar != null) {
            titleBar.setRightButton(iconRes, rightIconOnClick);
        }
    }

    public void setRightIconClick(View.OnClickListener rightIconOnClick) {
        setRightIconClick(getRightIconRes(), rightIconOnClick);
    }

    public void setRightBtnClick(String btnName, View.OnClickListener btnClick) {
        if (titleBar != null) {
            titleBar.setRightButton(btnName, btnClick);
        }
    }

    /**
     * 初始化toolBar中间的内容
     */
    private void initCenterViewOfTitle() {
        centerViews = getCenterViews();
        if (centerViews.size() == 0) {
            centerViews.add(titleView);
        }
    }

    /**
     * 根据位置获取toolbar中间的布局
     *
     * @param centerViewPosition
     * @return
     */
    private CenterViewInterface getCenterView(int centerViewPosition) {
        if (centerViewPosition >= 0 && centerViewPosition < centerViews.size()) {
            return centerViews.get(centerViewPosition);
        } else {
            throw new IllegalArgumentException("centerViewPosition is error,list position 越界");
        }
    }


    /**
     * 显示一个Toast信息
     *
     * @param content
     */
    public void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

    /**
     * 跳转到下一个activity页面
     *
     * @param nextActivity
     * @param bundle
     */
    public void toNextActivity(Class nextActivity, Bundle bundle) {
        Intent intent = new Intent(context, nextActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转到下一个activity页面， 无参数
     *
     * @param nextActivity
     */
    public void toNextActivity(Class nextActivity) {
        toNextActivity(nextActivity, null);
    }


    public void toNextActivityForResult(Class nextActivity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(context, nextActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void toNextActivityForResult(Class nextActivity, int requestCode) {
        toNextActivityForResult(nextActivity, requestCode, null);
    }

    public void setResult(int resultCode, Bundle bundle) {
        Intent intent = getIntent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setResult(resultCode, intent);
        finish();
    }

    public interface CenterViewInterface {
        View getView();

        void setViewWidget(View view);
    }

    public static interface TitleBarInterface {
        void bindViewByRes(View view);

        void setLeftButtton(int iconRes, View.OnClickListener leftClick);

        void setTitleName(String titleName);

        void setCenterView(CenterViewInterface centerView);

        void setRightButton(int iconRes, View.OnClickListener rightClick);

        void setRightButton(String rightBtnText, View.OnClickListener rightClick);

        boolean isSetTitle();
    }

    private CenterViewInterface titleView = new CenterViewInterface() {
        @Override
        public View getView() {
            return ViewUtils.getView(context, R.layout._title);
        }

        @Override
        public void setViewWidget(View view) {
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(getTitleName());
        }
    };

    protected void showLoadingDialog() {
        if (mSwipeRefresh != null){
            mSwipeRefresh.setRefreshing(true);
        } else {
            if (loading == null) {
                loading = new DialogLoading(this);
            }
            loading.show();
        }
    }

    protected void hideLoadingDialog() {
        if (loading != null) {
            loading.dismiss();
        }

        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }

    }


    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && clickKeyCodeBackLisenter != null) {//物理返回键
            return clickKeyCodeBackLisenter.onClickKeyCodeBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setClickKeyCodeBackLisenter(OnClickKeyCodeBackLisenter clickKeyCodeBackLisenter) {
        this.clickKeyCodeBackLisenter = clickKeyCodeBackLisenter;
    }

    public interface OnClickKeyCodeBackLisenter {
        boolean onClickKeyCodeBack();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void defaultMethod(String s) {

    }

    /**
     * 检测是否有新版本的app
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadApp(final DownloadAppEvent event) {
        if (!this.equals(event.getActivity())) {
            return;
        }
        apiWrapper.checkAppVersion(AppUtils.getAppVersionCode(this))
                .subscribe(new DisposableSubscriber<AppVersionDto>() {
                    @Override
                    public void onNext(AppVersionDto appVersionDto) {
                        updateApp(appVersionDto);
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof RetrofitUtil.APIException){
                            RetrofitUtil.APIException exception = (RetrofitUtil.APIException) t;
                            if (!event.getActivity().getClass().equals(MainActivity.class)){
                                showToast(exception.message);
                            }
                        } else {
                            t.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 更新app
     * @param appVersionDto
     */
    private void updateApp(final AppVersionDto appVersionDto) {
        if (1 == appVersionDto.getFoce()){//强制更新
            downloadApp(appVersionDto);
        } else {//不强制更新
            DialogUtils.showDefaultAlertDialog("检测到新版本 " + appVersionDto.getVersionView() + "，是否更新？", new DialogUtils.ClickListener() {
                @Override
                public void clickConfirm() {
                    downloadApp(appVersionDto);
                }

            });
        }

    }

    private void downloadApp(final AppVersionDto appVersionDto){
        String path = AppUtils.getDownloadAppPath("Transport_" + appVersionDto.getVersion() + "_v" + appVersionDto.getVersionView() + "_release.apk");
        String url = appVersionDto.getAppUrl();
        new FinalDownFiles(false, context, url,
                path,
                new FinalDownFileResult() {

                    @Override
                    public void onSuccess(DownInfo downInfo) {
                        super.onSuccess(downInfo);
                        Logger.i("成功==",downInfo.toString());
                        installApp(downInfo.getSavePath());
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        DialogUtils.DismissLoadDialog();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        DialogUtils.showDownloadDialog(context, "正在下载新版本" + appVersionDto.getVersionView() + ",请稍后。。。", false);
                    }

                    @Override
                    public void onPause() {
                        super.onPause();
                    }

                    @Override
                    public void onStop() {
                        super.onStop();
                        DialogUtils.DismissLoadDialog();
                    }

                    @Override
                    public void onLoading(long readLength, long countLength) {
                        super.onLoading(readLength, countLength);
                        Logger.i("下载过程=="," countLength = "+countLength+"    readLength = " +readLength);
                        DialogUtils.setProgressValue((int) ((readLength * 100) / countLength));
                    }

                    @Override
                    public void onErroe(Throwable e) {
                        super.onErroe(e);
                        DialogUtils.DismissLoadDialog();
                    }
                });
    }

    /**
     * 安装app文件
     *
     * @param path
     */
    private void installApp(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File apk = new File(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, "com.km.transport.fileprovider", apk);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        }

        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
