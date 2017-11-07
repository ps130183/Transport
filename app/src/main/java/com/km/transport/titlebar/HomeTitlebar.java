package com.km.transport.titlebar;

import android.view.View;

import com.km.transport.basic.BaseActivity;

/**
 * Created by kamangkeji on 17/3/13.
 */

public class HomeTitlebar implements BaseActivity.TitleBarInterface {

    @Override
    public void bindViewByRes(View view) {

    }

    @Override
    public void setLeftButtton(int iconRes, View.OnClickListener leftClick) {

    }

    @Override
    public void setTitleName(String titleName) {

    }

    @Override
    public void setCenterView(BaseActivity.CenterViewInterface centerView) {

    }

    @Override
    public void setRightButton(int iconRes, View.OnClickListener rightClick) {

    }

    @Override
    public void setRightButton(String rightBtnText, View.OnClickListener rightClick) {

    }

    @Override
    public boolean isSetTitle() {
        return false;
    }
}
