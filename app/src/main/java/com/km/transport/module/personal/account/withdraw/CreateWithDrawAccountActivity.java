package com.km.transport.module.personal.account.withdraw;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.dto.WithDrawAccountDto;
import com.km.transport.utils.PickerUtils;
import com.ps.androidlib.widget.nicespinner.NiceSpinner;
import com.ps.androidlib.widget.nicespinner.NiceSpinnerAdapter;
import com.ps.androidlib.widget.nicespinner.SpinnerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateWithDrawAccountActivity extends BaseActivity<WithDrawPresenter> implements WithDrawContract.View {

    private WithDrawAccountDto withDrawAccountDto;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_bank_name)
    EditText etBankName;
    @BindView(R.id.ll_bank_name)
    LinearLayout llBankName;
//    @BindView(R.id.et_type_name)
//    EditText etTypeName;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.tv_title_account)
    TextView tvTitleAccount;

    @BindView(R.id.nice_spinner)
    NiceSpinner mNiceSpinner;
    private List<SpinnerModel> spinnerModels;

    private ArrayList<String> bankList = null;
    private String[] banks = {"中国工商银行","中国农业银行","中国建设银行","中国邮政储蓄银行","交通银行","中信银行","招商银行","兴业银行"};

    private boolean isCreate;
    @Override
    protected int getContentView() {
        return R.layout.activity_create_with_draw_account;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        withDrawAccountDto = getIntent().getParcelableExtra("withDrawAccountDto");
        if (withDrawAccountDto == null){
            withDrawAccountDto = new WithDrawAccountDto();
            isCreate = true;
            return "新建账户";
        } else {
            isCreate = false;
            return "编辑信息";
        }

    }

    @Override
    public WithDrawPresenter getmPresenter() {
        return new WithDrawPresenter(this);
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawAccountDto.setUserName(etName.getText().toString());
                withDrawAccountDto.setUserPhone(etPhone.getText().toString());
                withDrawAccountDto.setAccountType(spinnerModels.get(mNiceSpinner.getSelectedIndex() + 1).getName());
                String bankName = etBankName.getText().toString();
                if (mNiceSpinner.getSelectedIndex() == 2){
                    if (TextUtils.isEmpty(bankName)){
                        showToast("请填写完整的银行名称");
                        return;
                    }
                    withDrawAccountDto.setBankName(etBankName.getText().toString());
                }
                withDrawAccountDto.setCardNumber(etAccount.getText().toString());
                if (withDrawAccountDto.isEmpty()){
                    showToast("请将信息补充完整");
                    return;
                }
                if (isCreate){
                    mPresenter.createWithDrawAccount(withDrawAccountDto);
                } else {
                    mPresenter.updateWithDrawAccount(withDrawAccountDto);
                }
            }
        });

        initSpinner();
        initBnakList();
        if (!isCreate){//编辑
            etName.setText(withDrawAccountDto.getUserName());
            etPhone.setText(withDrawAccountDto.getUserPhone());
            String accountType = withDrawAccountDto.getAccountType();
            if ("支付宝".equals(accountType)){
                mNiceSpinner.setSelectedIndex(0);
            } else if ("微信".equals(accountType)){
                mNiceSpinner.setSelectedIndex(1);
            } else if ("银行卡".equals(accountType)){
                mNiceSpinner.setSelectedIndex(2);
                llBankName.setVisibility(View.VISIBLE);
                etBankName.setText(withDrawAccountDto.getBankName());
            }


            etAccount.setText(withDrawAccountDto.getCardNumber());
        }
    }

    private void initBnakList(){
        bankList = new ArrayList<>();
        for (String bank : banks){
            bankList.add(bank);
        }
    }

    @Override
    public void creatOrUpdateSuccess() {
        showToast("保存成功");
        finish();
    }

    @Override
    public void showWithDrawList(List<WithDrawAccountDto> withDrawAccountDtos) {

    }

    @Override
    public void deleteSuccess(WithDrawAccountDto withDrawAccountDto) {

    }

    private void initSpinner(){
        spinnerModels = new ArrayList<>();
        spinnerModels.add(new SpinnerModel("0","请选择"));
        spinnerModels.add(new SpinnerModel("1","支付宝"));
        spinnerModels.add(new SpinnerModel("2","微信"));
        spinnerModels.add(new SpinnerModel("3","银行卡"));
        NiceSpinnerAdapter adapter = new NiceSpinnerAdapter(this,spinnerModels,0x000000,0x33000000);
        mNiceSpinner.setAdapter(adapter);
        mNiceSpinner.setSelectedIndex(0);
        mNiceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        llBankName.setVisibility(View.GONE);
                        tvTitleAccount.setText("支付宝账号：");
                        etAccount.setHint("请填写支付宝账号");
                        break;
                    case 1:
                        llBankName.setVisibility(View.GONE);
                        tvTitleAccount.setText("微 信 账 号 ：");
                        etAccount.setHint("请填写微信账号");
                        break;
                    case 2:
                        llBankName.setVisibility(View.VISIBLE);
                        tvTitleAccount.setText("银行卡账号：");
                        etAccount.setHint("请填写银行卡账号");
                        break;
                }
            }
        });
    }

    @OnClick(R.id.et_bank_name)
    public void selectBankName(View view){
        PickerUtils.alertBottomWheelOption(CreateWithDrawAccountActivity.this, bankList, new PickerUtils.OnWheelViewClick() {
            @Override
            public void onClick(View view, int postion) {
                etBankName.setText(bankList.get(postion));
            }
        });
    }
}
