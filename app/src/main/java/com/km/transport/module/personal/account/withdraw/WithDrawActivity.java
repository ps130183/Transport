package com.km.transport.module.personal.account.withdraw;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.dto.UserBalanceDto;
import com.km.transport.dto.UserInfoDto;
import com.km.transport.dto.WithDrawAccountDto;
import com.km.transport.event.WithDrawSuccessEvent;
import com.km.transport.module.personal.account.MyAccountActivity;
import com.km.transport.utils.InputFilterUtils;
import com.ps.androidlib.utils.EventBusUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawActivity extends BaseActivity<WithDrawAPresenter> implements WithDrawAContract.View {

    @BindView(R.id.et_money)
    EditText etMoney;

    @BindView(R.id.btn_withdraw)
    Button btnWithdraw;

    @BindView(R.id.tv_type_name)
    TextView tvTypeName;
    @BindView(R.id.tv_account)
    TextView tvAccount;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    private WithDrawAccountDto withDrawAccountDto;
    private UserInfoDto mUserInfo;

    @Override
    protected int getContentView() {
        return R.layout.activity_with_draw;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "提现";
    }

    @Override
    public WithDrawAPresenter getmPresenter() {
        return new WithDrawAPresenter(this);
    }

    @Override
    protected void onCreate() {

        withDrawAccountDto = getIntent().getParcelableExtra("withDrawAccountDto");
        tvTypeName.setText("银行卡".equals(withDrawAccountDto.getAccountType()) ? withDrawAccountDto.getBankName() : withDrawAccountDto.getAccountType());
        tvAccount.setText(withDrawAccountDto.getCardNumber());


        etMoney.setFilters(InputFilterUtils.filters2);
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && !"0".equals(s)){//提现金额 为空 或为0
                    btnWithdraw.setBackgroundResource(R.drawable.shape_withdraw_btn_click);
                } else {
                    btnWithdraw.setBackgroundResource(R.drawable.shape_withdraw_btn_unclick);
                }
//                btnWithdraw.setBackground(R.drawable.shape_withdraw_btn_click);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 全部提现
     * @param view
     */
    @OnClick(R.id.tv_all_withdraw)
    public void allWithDraw(View view){
        etMoney.setText(mUserInfo.getBalance()+"");
    }

    @OnClick(R.id.btn_withdraw)
    public void withDraw(View view){
        String witndrawMoney = etMoney.getText().toString();
        if (TextUtils.isEmpty(witndrawMoney)){
            showToast("请输入提现金额");
            return;
        }
        if ("0".equals(witndrawMoney)){
            showToast("提现金额必须大于0");
            return;
        }
        mPresenter.submitWithdraw(withDrawAccountDto,witndrawMoney);
    }

    @Override
    public void showBalance(UserInfoDto userInfoDto) {
        mUserInfo = userInfoDto;
        tvBalance.setText("可用余额 " + userInfoDto.getBalance() + " 元");
    }

    @Override
    public void withdrawSuccess() {
        showToast("提现申请已提交，请等待后台审核");
        EventBusUtils.post(new WithDrawSuccessEvent());
        toNextActivity(MyAccountActivity.class);
    }
}
