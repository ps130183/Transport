package com.km.transport.module.order.unfinished;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.transport.R;
import com.km.transport.adapter.GoodsOrderFinishInfoAdapter;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.BaseFragment;
import com.km.transport.basic.RVUtils;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.event.ShipmentEvent;
import com.km.transport.module.home.goods.GoodsOrderInfoActivity;
import com.km.transport.module.order.GoodsOrderContract;
import com.km.transport.module.order.GoodsOrderPresenter;
import com.km.transport.utils.SwipeRefreshUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class GoodsOrderUnFinishFragment extends BaseFragment<GoodsOrderPresenter> implements GoodsOrderContract.View {

    private int type = 1;
    @BindView(R.id.rv_goods_order_unfinished)
    RecyclerView rvGoodsOrderUnfinished;

    public static GoodsOrderUnFinishFragment newInstance(Bundle bundle) {
        GoodsOrderUnFinishFragment fragment = new GoodsOrderUnFinishFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_goods_order_un_finish;
    }

    @Override
    public GoodsOrderPresenter getmPresenter() {
        return new GoodsOrderPresenter(this);
    }

    @Override
    protected void createView() {
        initGoodsOrderUnfinished();
    }

    private void initGoodsOrderUnfinished(){
        RVUtils.setLinearLayoutManage(rvGoodsOrderUnfinished, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvGoodsOrderUnfinished);

        final GoodsOrderFinishInfoAdapter adapter = new GoodsOrderFinishInfoAdapter(getContext());
        rvGoodsOrderUnfinished.setAdapter(adapter);

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<GoodsOrderDetailDto>() {
            @Override
            public void onItemClick(GoodsOrderDetailDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId",itemData.getId());
                toNextActivity(GoodsOrderInfoActivity.class,bundle);
            }

        });

        adapter.setOnClickGoodsFunctionListener(new GoodsOrderFinishInfoAdapter.OnClickGoodsFunctionListener() {
            @Override
            public void clickGoodsShipment(GoodsOrderDetailDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId",itemData.getId());
                bundle.putBoolean("orderDetails",false);
                toNextActivity(GoodsOrderInfoActivity.class,bundle);
            }

            @Override
            public void clickGoodsTake(GoodsOrderDetailDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("orderId",itemData.getId());
                bundle.putBoolean("orderDetails",false);
                toNextActivity(PutStorageActivity.class,bundle);
            }
        });

        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getGoodsOrder(type,1);
            }
        });

        adapter.addLoadMore(rvGoodsOrderUnfinished, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getGoodsOrder(type,adapter.getNextPage());
            }
        });
        mPresenter.getGoodsOrder(type,1);
    }

    @Override
    public void showGoodsOrders(List<GoodsOrderDetailDto> goodsOrderDetailDtos, int pageNo) {
        GoodsOrderFinishInfoAdapter adapter = (GoodsOrderFinishInfoAdapter) rvGoodsOrderUnfinished.getAdapter();
        adapter.addData(goodsOrderDetailDtos,pageNo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshRecyclerView(ShipmentEvent event){
        Logger.d("GoodsOrderUnFinishFragment RefreshData");
        mPresenter.getGoodsOrder(type,1);
    }

}
