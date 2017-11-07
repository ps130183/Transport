package com.km.transport.module.personal;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.transport.R;
import com.km.transport.adapter.MessageAdapter;
import com.km.transport.basic.BaseActivity;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.RVUtils;
import com.km.transport.dto.MessageDto;
import com.km.transport.utils.SwipeRefreshUtils;

import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View {

    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.swiper_refresh)
    SwipeRefreshLayout swiperRefresh;

    @Override
    protected int getContentView() {
        return R.layout.activity_message;
    }

    @Override
    protected String getTitleName() {
        return "消息中心";
    }

    @Override
    public MessagePresenter getmPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    protected void onCreate() {
        initMyMessage();
    }

    private void initMyMessage(){
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiper_refresh);
        RVUtils.setLinearLayoutManage(rvMessage, LinearLayoutManager.VERTICAL);

        final MessageAdapter adapter = new MessageAdapter(this);
        rvMessage.setAdapter(adapter);

        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMessageList(1);
            }
        });

        adapter.addLoadMore(rvMessage, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getMessageList(adapter.getNextPage());
            }
        });

        mPresenter.getMessageList(1);
    }

    @Override
    public void showMessage(List<MessageDto> messageDtos,int pageNo) {
        MessageAdapter adapter = (MessageAdapter) rvMessage.getAdapter();
        adapter.addData(messageDtos,pageNo);
    }
}
