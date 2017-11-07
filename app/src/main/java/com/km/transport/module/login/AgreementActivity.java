package com.km.transport.module.login;

import android.webkit.WebView;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.utils.retrofit.SecretConstant;

import butterknife.BindView;

public class AgreementActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView tvTitle;
    private String titleName = "";

    @BindView(R.id.webView)
    WebView webView;
    @Override
    protected int getContentView() {
        return R.layout.activity_agreement;
    }

    @Override
    protected String getTitleName() {
        return "";
    }

    @Override
    protected void onCreate() {
        titleName = getIntent().getStringExtra("titleName");
        tvTitle.setText(titleName);

        String agreementUrl = getIntent().getStringExtra("agreementUrl");
        webView.loadUrl(SecretConstant.API_HOST + SecretConstant.API_HOST_PATH +agreementUrl);
    }
}
