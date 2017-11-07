package com.km.transport.module.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.transport.R;
import com.km.transport.adapter.BrowsingHistoryAdapter;
import com.km.transport.basic.BaseActivity;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.RVUtils;
import com.km.transport.dto.BrowsingHistoryDto;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.module.home.goods.GoodsSourceInfoActivity;
import com.ps.androidlib.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BrowsingHistoryActivity extends BaseActivity<BrowsingHistoryPresenter> implements BrowsingHistoryContract.View {

    @BindView(R.id.rv_browsing_history)
    RecyclerView rvBrowsingHistory;

    @Override
    protected int getContentView() {
        return R.layout.activity_browsing_history;
    }

    @Override
    protected String getTitleName() {
        return "浏览记录";
    }

    @Override
    public BrowsingHistoryPresenter getmPresenter() {
        return new BrowsingHistoryPresenter(this);
    }

    @Override
    protected void onCreate() {
        setRightBtnClick("清空", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDefaultAlertDialog("是否要清空浏览记录？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        mPresenter.clearBrowsingHistory();
                    }
                });
            }
        });
        initBrowsingHistory();
    }

    private void initBrowsingHistory(){
        RVUtils.setLinearLayoutManage(rvBrowsingHistory, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvBrowsingHistory);
        final BrowsingHistoryAdapter adapter = new BrowsingHistoryAdapter(this);
        rvBrowsingHistory.setAdapter(adapter);

        adapter.addLoadMore(rvBrowsingHistory, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getBrowsingHistoryList(adapter.getNextPage());
            }
        });
        adapter.setOnClickHireListener(new BrowsingHistoryAdapter.OnClickHireListener() {
            @Override
            public void clickHire(BrowsingHistoryDto browsingHistoryDto) {
                mPresenter.hireGoods(browsingHistoryDto.getDemandId());
            }
        });
        mPresenter.getBrowsingHistoryList(1);
    }

    @Override
    public void showBrowsingHistroyList(List<BrowsingHistoryDto> browsingHistoryDtos, int pageNo) {
        BrowsingHistoryAdapter adapter = (BrowsingHistoryAdapter) rvBrowsingHistory.getAdapter();
        adapter.addData(browsingHistoryDtos,pageNo);
    }

    @Override
    public void showGoodsOrderInfo(GoodsOrderDetailDto goodsOrderDetailDto) {
        if (goodsOrderDetailDto == null){
            showToast("找不到相关信息，请稍后再试");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("goodsOrderDetail",goodsOrderDetailDto);
        toNextActivity(GoodsSourceInfoActivity.class,bundle);
    }

    @Override
    public void clearSuccess() {
        BrowsingHistoryAdapter adapter = (BrowsingHistoryAdapter) rvBrowsingHistory.getAdapter();
        adapter.clearAllData();
    }
}
