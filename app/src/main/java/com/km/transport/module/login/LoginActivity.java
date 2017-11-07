package com.km.transport.module.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.transport.event.NoLoginEvent;
import com.km.transport.module.MainActivity;
import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.event.LoginSuccessEvent;
import com.ps.androidlib.utils.EventBusUtils;
import com.ps.androidlib.utils.StatusBarUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_toolbar_top)
    LinearLayout llToolbarTop;
    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
//
//    @BindView(R.id.cb_agree)
//    CheckBox cbAgree;

    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    private boolean isSendCode;
    private String mobilePhone;


    private Disposable codeDisposable;
    private long time = 60;
    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "土石物流";
    }

    @Override
    public LoginPresenter getmPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate() {
        StatusBarUtil.initState(this);
        mTitle.setTextColor(getResources().getColor(R.color.color_white));
        mToolbar.setBackgroundResource(R.color.color_blue_level_1);
        llToolbarTop.setBackgroundResource(R.color.color_blue_level_1);
        mobilePhone = getIntent().getStringExtra("mobilePhone");
        etPhone.setText(TextUtils.isEmpty(mobilePhone) ? "" : mobilePhone);
        setClickKeyCodeBackLisenter(new OnClickKeyCodeBackLisenter() {
            @Override
            public boolean onClickKeyCodeBack() {
                returnHome();
                return false;
            }
        });
        setLeftIconClick(0,null);
    }

    /**
     * 在登录页不去登录的 情况下 返回首页
     */
    private void returnHome(){
        Bundle bundle = new Bundle();
        bundle.putInt("position",0);
        toNextActivity(MainActivity.class,bundle);
    }

    /**
     * 登录
     * @param view
     */
    @OnClick(R.id.btn_login)
    public void login(View view){
        String phone = etPhone.getText().toString();
        String smsCode = etCode.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(smsCode)){
            showToast("请填写手机号或验证码");
            return;
        }
//        if (!cbAgree.isChecked()){
//            showToast("请同意玩转地球注册协议");
//            return;
//        }
        mPresenter.login(phone,smsCode);
    }

    @Override
    public void loginSuccess() {
//        showToast("登录成功");
//        JPushInterface.setAlias(this, Constant.user.getMobilePhone(), new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//                Logger.d("极光别名设置成功 = " + s + "    i =" + i);
//            }
//        });
        EventBusUtils.post(new LoginSuccessEvent());
        toNextActivity(MainActivity.class);
    }

    @Override
    public void createUserCard(String phone) {
        Bundle bundle = new Bundle();
        bundle.putString("phone",phone);
        toNextActivity(CreateUserCardOnLoginActivity.class,bundle);
    }

    @Override
    public void getPhoneCodeSuccess() {
        tvSendCode.setText(time+"");
        isSendCode = false;
        tvSendCode.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.color_text_gray2));
        codeDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        time--;
                        if (time > 0){
                            tvSendCode.setText(""+time);
                        } else {
                            if (!codeDisposable.isDisposed()){
                                codeDisposable.dispose();
                            }
                            tvSendCode.setText("重新发送");
                            isSendCode = true;
                            tvSendCode.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.color_blue3));
                        }
                    }
                });
    }

    @OnTextChanged(value = R.id.et_phone,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onPhoneLengthChange(Editable s){
        if (s.length() >= 11){
            isSendCode = true;
            tvSendCode.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.color_blue3));
        } else {
            isSendCode = false;
            tvSendCode.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.color_text_gray2));
        }
    }

    @OnClick(R.id.tv_send_code)
    public void sendCode(View view){
        if (isSendCode){
            time = 60;
            mPresenter.getPhoneCode(etPhone.getText().toString());
        }
    }

    @OnClick(R.id.tv_register_agreement)
    public void lookRegisterAgreement(View view){
        Bundle bundle = new Bundle();
        bundle.putString("titleName","注册协议");
        bundle.putString("agreementUrl","/agreement/detail");
        toNextActivity(AgreementActivity.class,bundle);
    }

}
