package com.km.transport.utils.retrofit;

import android.content.Context;
import android.content.Intent;

import com.km.transport.api.ApiWrapper;
import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.event.UserIsEmptyEvent;
import com.km.transport.module.login.LoginActivity;
import com.ps.androidlib.utils.AppUtils;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.MToast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by kamangkeji on 17/4/8.
 */

public abstract class PresenterWrapper<V extends BaseView> implements BasePresenter {

    protected V mView;
    protected Context mContext;
    public CompositeDisposable mCompositeSubscription;
    protected ApiWrapper mApiwrapper;

    public PresenterWrapper(V mView) {
        this.mView = mView;
        mContext = mView.getMContext();
        mCompositeSubscription = new CompositeDisposable();
        mApiwrapper = ApiWrapper.getInstance();
    }

    /**
     * 创建观察者
     *
     * @param onNext
     * @param <T>
     * @return
     */
    protected  <T> ResourceSubscriber<T> newSubscriber(final Consumer<? super T> onNext) {
        return new ResourceSubscriber<T>() {

            @Override
            protected void onStart() {
                super.onStart();
                if (!AppUtils.isNetworkReachable(mContext)){
                    MToast.showToast(mContext,"当前网络不可用，请稍后再试");
                    onComplete();
                    return;
                }
            }

            @Override
            public void onComplete() {
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof RetrofitUtil.APIException) {
                    RetrofitUtil.APIException exception = (RetrofitUtil.APIException) e;
                        MToast.showToast(mContext,exception.message);
                    if (RetCode.USER_IS_NOT_LOGIN.getStatus().equals(exception.code)){
                        EventBusUtils.post(new UserIsEmptyEvent());
                    }
                } else if (e instanceof SocketTimeoutException) {
                    MToast.showToast(mContext,"请求超时，请稍后再试");
                } else if (e instanceof ConnectException) {
                    MToast.showToast(mContext,"连接服务器失败，请稍后再试");
                } else if (e instanceof NullPointerException){
                    try {
                        onNext.accept((T) "");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                }
//                e.printStackTrace();
                mView.hideLoading();
            }

            @Override
            public void onNext(T t) {
                mView.hideLoading();
//                Logger.d(mCompositeSubscription + "");
                if (!mCompositeSubscription.isDisposed()) {
                    try {
                        onNext.accept(t);
                    } catch (Exception e) {// 返回值 为null
                        e.printStackTrace();
                    }
                }
            }

        };
    }

    /**
     * 创建观察者
     *
     * @param onNext
     * @param <T>
     * @return
     */
    protected  <T> ResourceSubscriber<T> newSubscriber(final Consumer<? super T> onNext, final Action backNull) {
        return new ResourceSubscriber<T>() {

            @Override
            protected void onStart() {
                super.onStart();
                if (!AppUtils.isNetworkReachable(mContext)){
                    MToast.showToast(mContext,"当前网络不可用，请稍后再试");
                    onComplete();
                    return;
                }
            }

            @Override
            public void onComplete() {
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof RetrofitUtil.APIException) {
                    RetrofitUtil.APIException exception = (RetrofitUtil.APIException) e;
                    MToast.showToast(mContext,exception.message);
                    if (RetCode.USER_IS_NOT_LOGIN.getStatus().equals(exception.code)){
                        EventBusUtils.post(new UserIsEmptyEvent());
                    }
                } else if (e instanceof SocketTimeoutException) {
                    MToast.showToast(mContext,"请求超时，请稍后再试");
                } else if (e instanceof ConnectException) {
                    MToast.showToast(mContext,"连接服务器失败，请稍后再试");
                } else if (e instanceof NullPointerException){
                    try {
                        backNull.run();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
//                e.printStackTrace();
                mView.hideLoading();
            }

            @Override
            public void onNext(T t) {
                mView.hideLoading();
                if (!mCompositeSubscription.isDisposed()) {
                    try {
                        onNext.accept(t);
                    } catch (Exception e) {// 返回值 为null
//                        e.printStackTrace();

                    }
                }
            }

        };
    }

    /**
     * 创建观察者
     *
//     * @param <T>
     * @return
     */
//    protected  <T> ResourceSubscriber<T> newFileUploadSubscriber(final Consumer<? super T> onUploadSuccess,
//                                                                 final Consumer<Integer> onProgress) {
//        return new FileUploadingListener<T>() {
//            @Override
//            public void onUpLoadSuccess(T t) {
//                try {
//                    onUploadSuccess.accept(t);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onUpLoadFail(Throwable e) {
//
//            }
//
//            @Override
//            public void onProgress(int progress) {
//                try {
//                    onProgress.accept(progress);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//    }



    @Override
    public void clearSubscription() {
        mCompositeSubscription.clear();
    }


}
