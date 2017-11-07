package com.km.transport.module.personal.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.adapter.UserAccountDetailAdapter;
import com.km.transport.basic.BaseActivity;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.RVUtils;
import com.km.transport.dto.UserAccountDetailDto;
import com.km.transport.dto.UserInfoDto;
import com.km.transport.event.WithDrawSuccessEvent;
import com.km.transport.module.personal.account.withdraw.WithDrawActivity;
import com.km.transport.module.personal.account.withdraw.WithDrawListActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAccountActivity extends BaseActivity<MyAccountPresenter> implements MyAccountContract.View {

    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.rv_account)
    RecyclerView rvAccount;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_account;
    }

    @Override
    protected String getTitleName() {
        return "我的账户";
    }

    @Override
    public MyAccountPresenter getmPresenter() {
        return new MyAccountPresenter(this);
    }

    @Override
    protected void onCreate() {
        mPresenter.getUserInfo();
        initAccount();
    }

    private void initAccount() {
        RVUtils.setLinearLayoutManage(rvAccount, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvAccount, RVUtils.DIVIDER_COLOR_DEFAULT, 2);
        final UserAccountDetailAdapter adapter = new UserAccountDetailAdapter(this);
        rvAccount.setAdapter(adapter);
        adapter.addLoadMore(rvAccount, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getAccountDetails(adapter.getNextPage());
            }
        });
        mPresenter.getAccountDetails(1);
    }

    /**
     * 提现
     * @param view
     */
    @OnClick(R.id.tv_withdraw_deposit)
    public void withDrawDeposit(View view){
        toNextActivity(WithDrawListActivity.class);
    }

    @Override
    public void showAccountDetails(List<UserAccountDetailDto> accountDetailDtos, int pageNo) {
        UserAccountDetailAdapter accountDetailAdapter = (UserAccountDetailAdapter) rvAccount.getAdapter();
        accountDetailAdapter.addData(accountDetailDtos,pageNo);
    }

    @Override
    public void showUserInfo(UserInfoDto userInfoDto) {
        tvBalance.setText(userInfoDto.getBalance());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void withDrawSuccess(WithDrawSuccessEvent event){
        mPresenter.getUserInfo();
        mPresenter.getAccountDetails(1);
    }
}
