package com.km.transport.basic;

import android.content.Context;

/**
 * Created by kamangkeji on 17/1/20.
 */

public interface BaseView<P extends BasePresenter> {
    Context getMContext();
    P getmPresenter();
    void showLoading();
    void hideLoading();
}
