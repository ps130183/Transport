package com.km.transport.module.home.goods;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gongwen.marqueen.SimpleMarqueeView;
import com.km.transport.R;
import com.km.transport.adapter.HomeGoodsOrderAdapter;
import com.km.transport.adapter.SelectCityAdapter;
import com.km.transport.adapter.SelectTruckTypeAdapter;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.BaseFragment;
import com.km.transport.basic.RVUtils;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.dto.HomeGoodsOrderDto;
import com.km.transport.entity.TruckTypeEntity;
import com.km.transport.event.RefreshSearchGoodsEvent;
import com.km.transport.event.UpdateMarqueeDataEvent;
import com.km.transport.greendao.City;
import com.km.transport.greendao.CityManager;
import com.km.transport.ui.CustomMF;
import com.km.transport.utils.Constant;
import com.km.transport.utils.SwipeRefreshUtils;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ps.androidlib.animator.ShowViewAnimator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsSearchFragment extends BaseFragment<GoodsSearchPresenter> implements GoodsSearchContract.View {

    @BindView(R.id.ll_site_start)
    LinearLayout llSiteStart;
    @BindView(R.id.tv_site_start)
    TextView tvSiteStart;
    @BindView(R.id.iv_site_start)
    ImageView ivSiteStart;

    @BindView(R.id.marquee)
    SimpleMarqueeView simpleMarqueeView;

    @BindView(R.id.ll_site_end)
    LinearLayout llSiteEnd;
    @BindView(R.id.tv_site_end)
    TextView tvSiteEnd;
    @BindView(R.id.iv_site_end)
    ImageView ivSiteEnd;

    @BindView(R.id.ll_truck_type)
    LinearLayout llTruckType;
    @BindView(R.id.tv_truck_type)
    TextView tvTruckType;
    @BindView(R.id.iv_truck_type)
    ImageView ivTruckType;

    //起点选择
    @BindView(R.id.rv_site_start)
    RecyclerView rvSiteStart;
    @BindView(R.id.ll_site_start1)
    LinearLayout llSiteStart1;
    @BindView(R.id.fl_site_start)
    FrameLayout flSiteStart;
    @BindView(R.id.tv_back_previous_start)
    TextView tvBackPreviousStart;
    @BindView(R.id.tv_cur_city_start)
    TextView tvCurCityStart;

    //终点选择
    @BindView(R.id.rv_site_end)
    RecyclerView rvSiteEnd;
    @BindView(R.id.ll_site_end1)
    LinearLayout llSiteEnd1;
    @BindView(R.id.fl_site_end)
    FrameLayout flSiteEnd;
    @BindView(R.id.tv_back_previous_end)
    TextView tvBackPreviousEnd;
    @BindView(R.id.tv_cur_city_end)
    TextView tvCurCityEnd;

    @BindView(R.id.nsv_truck_type)
    NestedScrollView nsvTruckType;
    @BindView(R.id.ll_truck_type1)
    LinearLayout llTruckType1;
    @BindView(R.id.rv_truck_length)
    RecyclerView rvTruckLength;
    @BindView(R.id.rv_truck_type)
    RecyclerView rvTrcukType;


    @BindView(R.id.rv_goods_orders)
    RecyclerView rvGoodsOrders;

    private String sourceProvince;
    private String sourceCity;
    private String sourceZoning;
    private String bournProvince;
    private String bournCity;
    private String bournZoning;
    private String carType;
    private String carWidth;

    private String curPirce;

    private boolean showStart;
    private boolean showEnd;
    private boolean showTruckType;

    public static GoodsSearchFragment newInstance(Bundle bundle) {
        GoodsSearchFragment fragment = new GoodsSearchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public GoodsSearchPresenter getmPresenter() {
        return new GoodsSearchPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_goods_search;
    }

    @Override
    protected void createView() {
        initTriangleOrientation();
        initRvSiteStart();
        initRvSiteEnd();
        initTrcukType();
        initGoodsOrderList();
        initMarquee();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (showStart){
            showSiteStart(false);
        } else if (showEnd){
            showSiteEnd(false);
        } else if (showTruckType){
            showTruckType(false);
        }
    }


    private void initMarquee(){
        mPresenter.getMarqueeDatas();
    }

    @OnClick({R.id.ll_site_start1,R.id.ll_site_end1,R.id.ll_truck_type1})
    public void clickDownList(View view){
    }

    /**
     * 初始化 选择 起始点  终点  车型  三角的方向
     */
    private void initTriangleOrientation() {
        ivSiteStart.setRotation(180f);
        ivSiteEnd.setRotation(180f);
        ivTruckType.setRotation(180f);
    }

    /**
     * 旋转起始点的 三角  角度
     */
    private void rotrationSiteStart() {
        float curGtRotration = ivSiteStart.getRotation() == 360 ? 0 : 180;
        float targetGtRotration = curGtRotration == 0 ? 180 : 360;
        ObjectAnimator gtAnima = ObjectAnimator.ofFloat(ivSiteStart, "rotation", curGtRotration, targetGtRotration);
        gtAnima.setDuration(300);
        gtAnima.start();
    }

    /**
     * 旋转终点的 三角 角度
     */
    private void rotrationSitEnd() {
        float curGtRotration = ivSiteEnd.getRotation() == 360 ? 0 : 180;
        float targetGtRotration = curGtRotration == 0 ? 180 : 360;
        ObjectAnimator gtAnima = ObjectAnimator.ofFloat(ivSiteEnd, "rotation", curGtRotration, targetGtRotration);
        gtAnima.setDuration(300);
        gtAnima.start();
    }

    /**
     * 旋转车型的 三角  角度
     */
    private void rotrationTruckType() {
        float curGtRotration = ivTruckType.getRotation() == 360 ? 0 : 180;
        float targetGtRotration = curGtRotration == 0 ? 180 : 360;
        ObjectAnimator gtAnima = ObjectAnimator.ofFloat(ivTruckType, "rotation", curGtRotration, targetGtRotration);
        gtAnima.setDuration(300);
        gtAnima.start();
    }

    /**
     * 关闭所有下拉菜单
     *
     * @param view
     */
    @OnClick({R.id.fl_site_start, R.id.fl_site_end, R.id.nsv_truck_type})
    public void closeAllFl(View view) {
        if (flSiteStart.getVisibility() == View.VISIBLE) {
            showSiteStart(false);
        }
        if (flSiteEnd.getVisibility() == View.VISIBLE) {
            showSiteEnd(false);
        }
        if (nsvTruckType.getVisibility() == View.VISIBLE) {
            showTruckType(false);
        }
    }

    private ShowViewAnimator mSiteStartAnimator = new ShowViewAnimator();

    /**
     * 起始点 点击事件
     *
     * @param view
     */
    @OnClick({R.id.ll_site_start, R.id.tv_site_start, R.id.iv_site_start})
    public void selectSiteStart(View view) {
        if (flSiteStart.getVisibility() == View.GONE) {
            showSiteStart(true);
            if (flSiteEnd.getVisibility() == View.VISIBLE) {
                showSiteEnd(false);
            }
            if (nsvTruckType.getVisibility() == View.VISIBLE) {
                showTruckType(false);
            }

        } else {
            showSiteStart(false);
        }
    }

    private ShowViewAnimator mSiteEndAnimator = new ShowViewAnimator();

    /**
     * 终点点击事件
     *
     * @param view
     */
    @OnClick({R.id.ll_site_end, R.id.tv_site_end, R.id.iv_site_end})
    public void selectSiteEnd(View view) {
        if (flSiteEnd.getVisibility() == View.GONE) {
            showSiteEnd(true);
            if (flSiteStart.getVisibility() == View.VISIBLE) {
                showSiteStart(false);
            }
            if (nsvTruckType.getVisibility() == View.VISIBLE) {
                showTruckType(false);
            }
        } else {
            showSiteEnd(false);
        }
    }

    private ShowViewAnimator mTruckTypeAnimator = new ShowViewAnimator();

    /**
     * 车型点击事件
     *
     * @param view
     */
    @OnClick({R.id.ll_truck_type, R.id.tv_truck_type, R.id.iv_truck_type})
    public void selectTruckType(View view) {
        if (nsvTruckType.getVisibility() == View.GONE) {
            showTruckType(true);
            if (flSiteEnd.getVisibility() == View.VISIBLE) {
                showSiteEnd(false);
            }
            if (flSiteStart.getVisibility() == View.VISIBLE) {
                showSiteStart(false);
            }
        } else {
            showTruckType(false);
        }
    }


    /**
     * 是否显示起点 下拉选择框
     *
     * @param isShow
     */
    private void showSiteStart(boolean isShow) {
        showStart = isShow;
        flSiteStart.setVisibility(isShow ? View.VISIBLE : View.GONE);
        llSiteStart1.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mSiteStartAnimator.showViewByAnimator(llSiteStart1, new ShowViewAnimator.onHideListener() {
            @Override
            public void hide() {
                if (curCheckStartCites[2] == null) {
                    curCheckStartCites[0] = curCheckedStartCites[0];
                    curCheckStartCites[1] = curCheckedStartCites[1];
                    curCheckStartCites[2] = curCheckedStartCites[2];
                }
                tvBackPreviousStart.setVisibility(View.GONE);
                cityParentStartAdapter.addData(CityManager.getInstance().getProvinces());
                refreshCheckStartCity();
            }
        });
        rotrationSiteStart();
    }

    /**
     * 是否显示 终点 下拉选择框
     *
     * @param isShow
     */
    private void showSiteEnd(boolean isShow) {
        showEnd = isShow;
        flSiteEnd.setVisibility(isShow ? View.VISIBLE : View.GONE);
        llSiteEnd1.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mSiteEndAnimator.showViewByAnimator(llSiteEnd1, new ShowViewAnimator.onHideListener() {
            @Override
            public void hide() {
                if (curCheckEndCites[2] == null) {
                    curCheckEndCites[0] = curCheckedEndCites[0];
                    curCheckEndCites[1] = curCheckedEndCites[1];
                    curCheckEndCites[2] = curCheckedEndCites[2];
                }
                tvBackPreviousEnd.setVisibility(View.GONE);
                cityParentEndAdapter.addData(CityManager.getInstance().getProvinces());
                refreshCheckEndCity();
            }
        });
        rotrationSitEnd();
    }

    /**
     * 是否显示 车型 下拉选择框
     *
     * @param isShow
     */
    private void showTruckType(boolean isShow) {
        showTruckType = isShow;
        nsvTruckType.setVisibility(isShow ? View.VISIBLE : View.GONE);
        llTruckType1.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mTruckTypeAnimator.showViewByAnimator(llTruckType1, null);
        rotrationTruckType();
    }

    /**
     * 起点不限
     *
     * @param view
     */
    @OnClick(R.id.btn_start_unlimited)
    public void unLimitedStart(View view) {
        if (cityParentStartAdapter.getItemData(0).getType() == 2){
            curCheckStartCites[0] = null;
            curCheckStartCites[1] = null;
            curCheckStartCites[2] = null;
        }
        checkFinalStartCity();
    }

    /**
     * 终点不限
     *
     * @param view
     */
    @OnClick(R.id.btn_end_unlimited)
    public void unLimitedEnd(View view) {
        if (cityParentEndAdapter.getItemData(0).getType() == 2){
            curCheckEndCites[0] = null;
            curCheckEndCites[1] = null;
            curCheckEndCites[2] = null;
        }
        checkFinalEndCity();
    }



    /**
     * 选择 起点 地区 返回上一级
     *
     * @param view
     */
    @OnClick(R.id.tv_back_previous_start)
    public void backToThePreviousStart(View view) {
        City city = cityParentStartAdapter.getItemData(0);
        if (city.getType() == 4) {//区
            curCheckStartCites[2] = null;
            curCheckStartCites[1] = null;
            cityParentStartAdapter.addData(CityManager.getInstance().getPreCitys(city.getParentId()));
        } else if (city.getType() == 3) {//市
            curCheckStartCites[0] = null;
            tvBackPreviousStart.setVisibility(View.GONE);
            cityParentStartAdapter.addData(CityManager.getInstance().getProvinces());
        }

        refreshCheckStartCity();

    }

    /**
     * 选择 终点 地区 返回上一级
     *
     * @param view
     */
    @OnClick(R.id.tv_back_previous_end)
    public void backToThePreviousEnd(View view) {
        City city = cityParentEndAdapter.getItemData(0);
        if (city.getType() == 4) {//区
            curCheckEndCites[2] = null;
            curCheckEndCites[1] = null;
            cityParentEndAdapter.addData(CityManager.getInstance().getPreCitys(city.getParentId()));
        } else if (city.getType() == 3) {//市
            curCheckEndCites[0] = null;
            tvBackPreviousEnd.setVisibility(View.GONE);
            cityParentEndAdapter.addData(CityManager.getInstance().getProvinces());
        }

        refreshCheckEndCity();
    }

    /**
     * 初始化 选择起点城市
     */
    private SelectCityAdapter cityParentStartAdapter;
    private City[] curCheckStartCites = new City[3];
    private City[] curCheckedStartCites = new City[3];

    private void initRvSiteStart() {
        RVUtils.setGridLayoutManage(rvSiteStart, 4);
        cityParentStartAdapter = new SelectCityAdapter(getContext());
        rvSiteStart.setAdapter(cityParentStartAdapter);

        List<City> provinces = CityManager.getInstance().getProvinces();
        cityParentStartAdapter.addData(provinces);

        cityParentStartAdapter.setItemClickListener(new BaseAdapter.ItemClickListener<City>() {
            @Override
            public void onItemClick(City itemData, int position) {
                List<City> nextCitys;
                if (itemData.getType() == 2) {//点击的是省
                    curCheckStartCites[0] = itemData;
                    curCheckStartCites[1] = null;
                    curCheckStartCites[2] = null;
                    nextCitys = CityManager.getInstance().getCitys(itemData.getId());
                    refreshCheckStartCity();
                } else if (itemData.getType() == 3) {//点击的是市
                    curCheckStartCites[1] = itemData;
                    nextCitys = CityManager.getInstance().getAreas(itemData.getId());
                    refreshCheckStartCity();
                } else { //点击的是区
                    curCheckStartCites[2] = itemData;
                    checkFinalStartCity();
                    return;
                }

                if (nextCitys != null && nextCitys.size() > 0) {
                    tvBackPreviousStart.setVisibility(View.VISIBLE);
                    cityParentStartAdapter.addData(nextCitys);
                } else {
                    checkFinalStartCity();
                    return;
                }


            }
        });
    }

    /**
     * 获得最终选择的起点省市区
     */
    private void checkFinalStartCity() {
        showSiteStart(false);
        curCheckedStartCites[0] = curCheckStartCites[0];
        curCheckedStartCites[1] = curCheckStartCites[1];
        curCheckedStartCites[2] = curCheckStartCites[2];
        if (curCheckedStartCites[2] != null) {
            tvSiteStart.setText(curCheckedStartCites[2].getName());
        } else if (curCheckedStartCites[1] != null) {
            tvSiteStart.setText(curCheckedStartCites[1].getName());
        } else if (curCheckedStartCites[0] != null) {
            tvSiteStart.setText(curCheckedStartCites[0].getName());
        } else {
            tvSiteStart.setText("不限");
        }
        loadHomeGoodsOrders();
    }

    /**
     * 刷新选中的起点  当前城市
     */
    private void refreshCheckStartCity() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < curCheckStartCites.length; i++) {
            City item = curCheckStartCites[i];
            if (item == null) {
                break;
            }
            if (i > 0) {
                buffer.append(" - ");
            }
            buffer.append(curCheckStartCites[i].getName());
        }
        tvCurCityStart.setText("当前城市：" + buffer.toString());
    }

    /**
     * 初始化 选择终点城市
     */
    private SelectCityAdapter cityParentEndAdapter;
    private City[] curCheckEndCites = new City[3];
    private City[] curCheckedEndCites = new City[3];

    private void initRvSiteEnd() {
        RVUtils.setGridLayoutManage(rvSiteEnd, 4);
        cityParentEndAdapter = new SelectCityAdapter(getContext());
        rvSiteEnd.setAdapter(cityParentEndAdapter);

        cityParentEndAdapter.addData(CityManager.getInstance().getProvinces());
        cityParentEndAdapter.setItemClickListener(new BaseAdapter.ItemClickListener<City>() {
            @Override
            public void onItemClick(City itemData, int position) {
                List<City> nextCitys;
                if (itemData.getType() == 2) {//点击的是省
                    curCheckEndCites[0] = itemData;
                    curCheckEndCites[1] = null;
                    curCheckEndCites[2] = null;
                    nextCitys = CityManager.getInstance().getCitys(itemData.getId());
                    refreshCheckEndCity();
                } else if (itemData.getType() == 3) {//点击的是市
                    curCheckEndCites[1] = itemData;
                    nextCitys = CityManager.getInstance().getAreas(itemData.getId());
                    refreshCheckEndCity();
                } else { //点击的是区
                    curCheckEndCites[2] = itemData;
                    checkFinalEndCity();
                    return;
                }

                if (nextCitys != null && nextCitys.size() > 0) {
                    tvBackPreviousEnd.setVisibility(View.VISIBLE);
                    cityParentEndAdapter.addData(nextCitys);
                } else {
                    checkFinalEndCity();
                    return;
                }
            }
        });
    }

    /**
     * 获得最终选择的终点省市区
     */
    private void checkFinalEndCity() {
        showSiteEnd(false);
        curCheckedEndCites[0] = curCheckEndCites[0];
        curCheckedEndCites[1] = curCheckEndCites[1];
        curCheckedEndCites[2] = curCheckEndCites[2];
        if (curCheckedEndCites[2] != null) {
            tvSiteEnd.setText(curCheckedEndCites[2].getName());
        } else if (curCheckedEndCites[1] != null) {
            tvSiteEnd.setText(curCheckedEndCites[1].getName());
        } else if (curCheckedEndCites[0] != null) {
            tvSiteEnd.setText(curCheckedEndCites[0].getName());
        } else {
            tvSiteEnd.setText("不限");
        }
        loadHomeGoodsOrders();
    }

    /**
     * 刷新选中的终点  当前城市
     */
    private void refreshCheckEndCity() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < curCheckEndCites.length; i++) {
            City item = curCheckEndCites[i];
            if (item == null) {
                break;
            }
            if (i > 0) {
                buffer.append(" - ");
            }
            buffer.append(curCheckEndCites[i].getName());
        }
        tvCurCityEnd.setText("当前城市：" + buffer.toString());
    }

    /**
     * 初始化 选择 车型车长
     */
    private void initTrcukType() {
        RVUtils.setGridLayoutManage(rvTruckLength, 4);
        SelectTruckTypeAdapter truckLengthAdapter = new SelectTruckTypeAdapter(getContext(), SelectTruckTypeAdapter.TruckType.LEGNTH);
        rvTruckLength.setAdapter(truckLengthAdapter);
        List<TruckTypeEntity> truckLengthEntitys = new ArrayList<>();
        for (float length : Constant.TRUCK_LENGTHS) {
            truckLengthEntitys.add(new TruckTypeEntity(length, ""));
        }
        truckLengthAdapter.addData(truckLengthEntitys, 2);

        RVUtils.setGridLayoutManage(rvTrcukType, 3);
        SelectTruckTypeAdapter trcukTypeAdapter = new SelectTruckTypeAdapter(getContext(), SelectTruckTypeAdapter.TruckType.TYPE);
        rvTrcukType.setAdapter(trcukTypeAdapter);
        List<TruckTypeEntity> truckTypeEntities = new ArrayList<>();
        for (String type : Constant.TRUCK_TYPES) {
            truckTypeEntities.add(new TruckTypeEntity(-1, type));
        }
        trcukTypeAdapter.addData(truckTypeEntities, 2);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm(View view){
        SelectTruckTypeAdapter carWidthAdapter = (SelectTruckTypeAdapter) rvTruckLength.getAdapter();
        SelectTruckTypeAdapter carTypeAdapter = (SelectTruckTypeAdapter) rvTrcukType.getAdapter();

        TruckTypeEntity carWidthEntity = carWidthAdapter.getCheckedEntity();
        TruckTypeEntity carTypeEntity = carTypeAdapter.getCheckedEntity();

        if (carWidthEntity.getLength() > 0){
            carWidth = carWidthEntity.getLength() + "";
        } else {
            carWidth = "";
        }

        if (TextUtils.isEmpty(carTypeEntity.getType())){
            carType = "";
        } else {
            carType = carTypeEntity.getType();
        }
        showTruckType(false);
        loadHomeGoodsOrders();
    }

    private void initGoodsOrderList() {
        RVUtils.setLinearLayoutManage(rvGoodsOrders, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvGoodsOrders);
        final HomeGoodsOrderAdapter adapter = new HomeGoodsOrderAdapter(getContext());
        rvGoodsOrders.setAdapter(adapter);

        //我要承揽
        adapter.setOnClickHireListener(new HomeGoodsOrderAdapter.OnClickHireListener() {
            @Override
            public void hireOrder(HomeGoodsOrderDto itemData, int position) {
                curPirce = itemData.getDownPrice();
                mPresenter.hireGoods(itemData.getId());
            }
        });
        adapter.addLoadMore(rvGoodsOrders, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                mPresenter.getHomeGoodsOrders(adapter.getNextPage(),
                        sourceProvince,sourceCity,sourceZoning,
                        bournProvince,bournCity,bournZoning,
                        carType,carWidth);
            }
        });
        loadHomeGoodsOrders();

        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(2, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@NonNull Long aLong) throws Exception {
                                loadHomeGoodsOrders();
                            }
                        });

            }
        });
    }

    /**
     * 加载列表数据
     */
    private void loadHomeGoodsOrders(){
        if (curCheckedStartCites[2] != null){
            sourceZoning = curCheckedStartCites[2].getName();
        } else {
            sourceZoning = "";
        }
        if (curCheckedStartCites[1] != null){
            sourceCity = curCheckedStartCites[1].getName();
        } else {
            sourceCity = "";
        }
        if (curCheckedStartCites[0] != null){
            sourceProvince = curCheckedStartCites[0].getName();
        } else {
            sourceProvince = "";
        }

        if (curCheckedEndCites[2] != null){
            bournZoning = curCheckedEndCites[2].getName();
        } else {
            bournZoning = "";
        }
        if (curCheckedEndCites[1] != null){
            bournCity = curCheckedEndCites[1].getName();
        } else{
            bournCity = "";
        }
        if (curCheckedEndCites[0] != null){
            bournProvince = curCheckedEndCites[0].getName();
        } else {
            bournProvince = "";
        }
        mPresenter.getHomeGoodsOrders(1,
                sourceProvince,sourceCity,sourceZoning,
                bournProvince,bournCity,bournZoning,
                carType,carWidth);
    }

    @Override
    public void showHomeGoodsOrders(List<HomeGoodsOrderDto> homeGoodsOrderDtos, int pageNo) {
        HomeGoodsOrderAdapter adapter = (HomeGoodsOrderAdapter) rvGoodsOrders.getAdapter();
        adapter.addData(homeGoodsOrderDtos,pageNo);
    }

    @Override
    public void showGoodsOrderInfo(GoodsOrderDetailDto goodsOrderDetailDto) {
        if (goodsOrderDetailDto == null){
            showToast("找不到相关信息，请稍后再试");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("goodsOrderDetail",goodsOrderDetailDto);
        bundle.putString("curPrice",curPirce);
        toNextActivity(GoodsSourceInfoActivity.class,bundle);
    }

    private List<String> marqueeDatas;
    @Override
    public void showMarqueeDatas(List<String> marqueeDatas) {
        this.marqueeDatas = marqueeDatas;
        CustomMF marqueeFactory = new CustomMF(getActivity());
        marqueeFactory.setData(marqueeDatas);
        simpleMarqueeView.setMarqueeFactory(marqueeFactory);
        simpleMarqueeView.startFlipping();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshSearchGoodsList(RefreshSearchGoodsEvent event){
        loadHomeGoodsOrders();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateMarqueeData(UpdateMarqueeDataEvent event){
        if (marqueeDatas != null && marqueeDatas.size() > 0){
            simpleMarqueeView.stopFlipping();
            marqueeDatas.add(0,event.getContent());
            marqueeDatas.remove(marqueeDatas.size() - 1);

            CustomMF marqueeFactory = new CustomMF(getActivity());
            marqueeFactory.setData(marqueeDatas);
            simpleMarqueeView.setMarqueeFactory(marqueeFactory);
            simpleMarqueeView.startFlipping();
        }
    }
}
