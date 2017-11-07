package com.km.transport.module.personal;

import android.support.annotation.NonNull;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.ps.androidlib.utils.AppUtils;

import butterknife.BindView;

public class AboutMeActivity extends BaseActivity {

    @BindView(R.id.rl_logo)
    RelativeLayout rlLogo;

    @BindView(R.id.tv_cur_app_version)
    TextView tvCurAPPVersion;

    @Override
    protected int getContentView() {
        return R.layout.activity_about_me;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "关于我们";
    }

    @Override
    protected void onCreate() {
        rlLogo.getLayoutParams().height = AppUtils.getCurWindowWidth(this) - 200;
        tvCurAPPVersion.setText("当前版本  " + AppUtils.getAppVersionName(this));
    }
}
