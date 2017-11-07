package com.km.transport.module.home.path;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.transport.R;
import com.km.transport.adapter.HomeFastPathAdapter;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.BaseFragment;
import com.km.transport.basic.RVUtils;
import com.km.transport.dto.HomeFastPathDto;
import com.km.transport.event.AddFastPathSuccessEvent;
import com.km.transport.module.home.path.newpath.AddFastPathActivity;
import com.km.transport.utils.SwipeRefreshUtils;
import com.ps.androidlib.utils.DialogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FastPathFragment extends BaseFragment<FastPathPresenter> implements FastPathContract.View {

    @BindView(R.id.rv_paths)
    RecyclerView rvPaths;

    public static FastPathFragment newInstance(Bundle bundle) {
        FastPathFragment fragment = new FastPathFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public FastPathPresenter getmPresenter() {
        return new FastPathPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_fast_path;
    }

    @Override
    protected void createView() {
        initFastPathList();
    }

    private void initFastPathList(){
        RVUtils.setLinearLayoutManage(rvPaths, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvPaths,RVUtils.DIVIDER_COLOR_DEFAULT,2);
        final HomeFastPathAdapter adapter = new HomeFastPathAdapter(getContext());
        rvPaths.setAdapter(adapter);

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<HomeFastPathDto>() {
            @Override
            public void onItemClick(HomeFastPathDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("homeFastPath",itemData);
                toNextActivity(GoodsSearchActivity.class,bundle);
            }
        });

        adapter.setOnDeleteFastPath(new HomeFastPathAdapter.OnDeleteFastPath() {
            @Override
            public void deleteFastPath(final HomeFastPathDto homeFastPathDto) {
                DialogUtils.showDefaultAlertDialog("是否要删除该快捷路线？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        mPresenter.deleteFastPath(homeFastPathDto);
                    }
                });
            }
        });

        adapter.addLoadMore(rvPaths, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getFastPathList(adapter.getNextPage());
            }
        });

        mPresenter.getFastPathList(1);

        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getFastPathList(1);
            }
        });
    }

    @OnClick(R.id.btn_add_fast_Path)
    public void addFastPath(View view){
        toNextActivity(AddFastPathActivity.class);
    }

    @Override
    public void showFastPathList(List<HomeFastPathDto> fastPathDtos) {
        HomeFastPathAdapter adapter = (HomeFastPathAdapter) rvPaths.getAdapter();
        adapter.addData(fastPathDtos);
    }

    @Override
    public void deleteSuccess(HomeFastPathDto homeFastPathDto) {
        HomeFastPathAdapter adapter = (HomeFastPathAdapter) rvPaths.getAdapter();
        adapter.removeData(homeFastPathDto);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addFastPathSuccess(AddFastPathSuccessEvent event){
        mPresenter.getFastPathList(1);
    }
}
