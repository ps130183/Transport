package com.km.transport.basic;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.km.transport.R;
import com.ps.androidlib.utils.DialogLoading;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.ViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionGen;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private DialogLoading loading;//加载提示框
    protected P mPresenter;
    protected Toast mToast = null;//提示框

    protected SwipeRefreshLayout mSwipeRefresh;

    protected BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtils.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = ViewUtils.getView(inflater, container, getContentView());
        ButterKnife.bind(this, view);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiper_refresh);
        mPresenter = getmPresenter();
        createView();
        if (mPresenter != null) {
            mPresenter.onCreateView();
        }
        return view;
    }

    protected abstract
    @LayoutRes
    int getContentView();

    protected abstract void createView();

    public P getmPresenter() {
        return null;
    }

    @Override
    public Context getMContext() {
        return getContext();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
        if (loading != null && loading.isShowing()){
            loading.dismiss();
        }
    }

    /**
     * 显示一个Toast信息
     *
     * @param content
     */
    public void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), content, Toast.LENGTH_SHORT);
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
        Intent intent = new Intent(getContext(), nextActivity);
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

    public void toNextActivityForResult(Class nextActivity,int requestCode,Bundle bundle){
        Intent intent = new Intent(getContext(),nextActivity);
        if (bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    public void toNextActivityForResult(Class nextActivity,int requestCode){
        toNextActivityForResult(nextActivity,requestCode,null);
    }


    @Override
    public void showLoading() {
        if (mSwipeRefresh != null){
            mSwipeRefresh.setRefreshing(true);
        } else {
            if (loading == null){
                loading= new DialogLoading(getContext());
            }
            loading.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loading != null){
            loading.dismiss();
        }
        if (mSwipeRefresh != null && mSwipeRefresh.isRefreshing()){
            mSwipeRefresh.setRefreshing(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void defaultMethod(String s) {

    }
}
