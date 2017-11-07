package com.km.transport.module.personal.account.withdraw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.transport.R;
import com.km.transport.adapter.WithDrawAccountAdapter;
import com.km.transport.basic.BaseActivity;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.RVUtils;
import com.km.transport.dto.WithDrawAccountDto;
import com.ps.androidlib.utils.DialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawListActivity extends BaseActivity<WithDrawPresenter> implements WithDrawContract.View, WithDrawAccountAdapter.OnEditWithdrawListener, WithDrawAccountAdapter.OnDeleteWithdrawListener{

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_with_draw_list;
    }

    @NonNull
    @Override
    protected String getTitleName() {
        return "提现管理";
    }

    @Override
    public WithDrawPresenter getmPresenter() {
        return new WithDrawPresenter(this);
    }

    @Override
    protected void onCreate() {
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getWithDrawList();
    }

    private void initRecyclerView() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        WithDrawAccountAdapter adapter = new WithDrawAccountAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnEditWithdrawListener(this);
        adapter.setOnDeleteWithdrawListener(this);
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<WithDrawAccountDto>() {
            @Override
            public void onItemClick(WithDrawAccountDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("withDrawAccountDto",itemData);
                toNextActivity(WithDrawActivity.class,bundle);
            }
        });
    }

    @OnClick(R.id.btn_add_account)
    public void createAccount(View view) {
        toNextActivity(CreateWithDrawAccountActivity.class);
    }

    @Override
    public void creatOrUpdateSuccess() {

    }

    @Override
    public void showWithDrawList(List<WithDrawAccountDto> withDrawAccountDtos) {
        WithDrawAccountAdapter adapter = (WithDrawAccountAdapter) mRecyclerView.getAdapter();
        adapter.addData(withDrawAccountDtos);
    }

    @Override
    public void deleteSuccess(WithDrawAccountDto withDrawAccountDto) {
        WithDrawAccountAdapter adapter = (WithDrawAccountAdapter) mRecyclerView.getAdapter();
        adapter.removeData(withDrawAccountDto);
    }

    @Override
    public void editWithdraw(WithDrawAccountDto withDrawAccountDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("withDrawAccountDto",withDrawAccountDto);
        toNextActivity(CreateWithDrawAccountActivity.class,bundle);
    }

    @Override
    public void deleteWithdraw(final WithDrawAccountDto withDrawAccountDto) {
        DialogUtils.showDefaultAlertDialog("是否要删除该提现账号？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                mPresenter.deleteWithdrawAccount(withDrawAccountDto);
            }
        });
    }

}
