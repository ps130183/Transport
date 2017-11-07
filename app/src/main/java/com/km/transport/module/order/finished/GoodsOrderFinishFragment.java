package com.km.transport.module.order.finished;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.transport.R;
import com.km.transport.adapter.GoodsOrderFinishInfoAdapter;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.BaseFragment;
import com.km.transport.basic.RVUtils;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.module.home.goods.GoodsOrderFinishInfoActivity;
import com.km.transport.module.home.goods.GoodsOrderInfoActivity;
import com.km.transport.module.order.GoodsOrderContract;
import com.km.transport.module.order.GoodsOrderPresenter;
import com.km.transport.utils.SwipeRefreshUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsOrderFinishFragment extends BaseFragment<GoodsOrderPresenter> implements GoodsOrderContract.View {

    private int type = 2;

    @BindView(R.id.rv_goods_order_finished)
    RecyclerView rvGoodsOrderFinished;

    public static GoodsOrderFinishFragment newInstance(Bundle bundle) {
        GoodsOrderFinishFragment fragment = new GoodsOrderFinishFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int getContentView() {
        return R.layout.fragment_goods_order_finish;
    }

    @Override
    public GoodsOrderPresenter getmPresenter() {
        return new GoodsOrderPresenter(this);
    }

    @Override
    protected void createView() {
        initGoodsOrderFinished();
    }

    private void initGoodsOrderFinished(){
        RVUtils.setLinearLayoutManage(rvGoodsOrderFinished, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvGoodsOrderFinished);

        final GoodsOrderFinishInfoAdapter adapter = new GoodsOrderFinishInfoAdapter(getContext());
        rvGoodsOrderFinished.setAdapter(adapter);

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<GoodsOrderDetailDto>() {
            @Override
            public void onItemClick(GoodsOrderDetailDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId",itemData.getId());
                toNextActivity(GoodsOrderFinishInfoActivity.class,bundle);
            }

        });
        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getGoodsOrder(type,1);
            }
        });
        adapter.addLoadMore(rvGoodsOrderFinished, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getGoodsOrder(type,adapter.getNextPage());
            }
        });
        mPresenter.getGoodsOrder(type,1);
    }

    @Override
    public void showGoodsOrders(List<GoodsOrderDetailDto> goodsOrderDetailDtos, int pageNo) {
        GoodsOrderFinishInfoAdapter adapter = (GoodsOrderFinishInfoAdapter) rvGoodsOrderFinished.getAdapter();
        adapter.addData(goodsOrderDetailDtos,pageNo);
    }
}
