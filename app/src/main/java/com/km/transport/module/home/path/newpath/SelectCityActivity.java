package com.km.transport.module.home.path.newpath;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.adapter.SelectCityAdapter;
import com.km.transport.basic.BaseActivity;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.RVUtils;
import com.km.transport.event.CheckCityEvent;
import com.km.transport.greendao.City;
import com.km.transport.greendao.CityManager;
import com.ps.androidlib.utils.EventBusUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectCityActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_hint)
    TextView tvHint;

    @BindView(R.id.rv_site)
    RecyclerView rvSite;
    @BindView(R.id.tv_back_previous)
    TextView tvBackPrevious;

    private int cityType;

    @Override
    protected int getContentView() {
        return R.layout.activity_select_city;
    }

    @Override
    protected String getTitleName() {
        return "";
    }

    @Override
    protected void onCreate() {
        cityType = getIntent().getIntExtra("cityType",-1);
        String title = "";
        if (cityType == 0){
            title = "选择出发地";
        } else if (cityType == 1){
            title = "选择目的地";
        } else {
            title = "选择城市";
        }
        mTitle.setText(title);
        tvHint.setText(title);
        initSite();
    }

    @OnClick(R.id.btn_unlimited)
    public void unLimited(View view){
        if (cityParentAdapter.getItemData(0).getType() == 2){
            curCheckCites[0] = null;
            curCheckCites[1] = null;
            curCheckCites[2] = null;
        }
        checkFinalStartCity();
    }

    /**
     * 返回上一级
     * @param view
     */
    @OnClick(R.id.tv_back_previous)
    public void backPrevious(View view){
        City city = cityParentAdapter.getItemData(0);
        if (city.getType() == 4) {//区
            curCheckCites[2] = null;
            curCheckCites[1] = null;
            cityParentAdapter.addData(CityManager.getInstance().getPreCitys(city.getParentId()));
        } else if (city.getType() == 3) {//市
            curCheckCites[0] = null;
            tvBackPrevious.setVisibility(View.GONE);
            cityParentAdapter.addData(CityManager.getInstance().getProvinces());
        }

        refreshCheckStartCity();
    }

    /**
     * 初始化 选择起点城市
     */
    private SelectCityAdapter cityParentAdapter;
    private City[] curCheckCites = new City[3];
    private City[] curCheckedCites = new City[3];

    private void initSite(){
        RVUtils.setGridLayoutManage(rvSite,4);
        cityParentAdapter = new SelectCityAdapter(this);
        rvSite.setAdapter(cityParentAdapter);

        cityParentAdapter.addData(CityManager.getInstance().getProvinces());
        cityParentAdapter.setItemClickListener(new BaseAdapter.ItemClickListener<City>() {
            @Override
            public void onItemClick(City itemData, int position) {
                List<City> nextCitys;
                if (itemData.getType() == 2) {//点击的是省
                    curCheckCites[0] = itemData;
                    curCheckCites[1] = null;
                    curCheckCites[2] = null;
                    nextCitys = CityManager.getInstance().getCitys(itemData.getId());
                    refreshCheckStartCity();
                } else if (itemData.getType() == 3) {//点击的是市
                    curCheckCites[1] = itemData;
                    nextCitys = CityManager.getInstance().getAreas(itemData.getId());
                    refreshCheckStartCity();
                } else { //点击的是区
                    curCheckCites[2] = itemData;
                    checkFinalStartCity();
                    return;
                }

                if (nextCitys != null && nextCitys.size() > 0) {
                    tvBackPrevious.setVisibility(View.VISIBLE);
                    cityParentAdapter.addData(nextCitys);
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
//        showSiteStart(false);
        curCheckedCites[0] = curCheckCites[0];
        curCheckedCites[1] = curCheckCites[1];
        curCheckedCites[2] = curCheckCites[2];
        EventBusUtils.post(new CheckCityEvent(cityType,curCheckedCites));
        finish();
    }

    /**
     * 刷新选中的起点  当前城市
     */
    private void refreshCheckStartCity() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < curCheckCites.length; i++) {
            City item = curCheckCites[i];
            if (item == null) {
                break;
            }
            if (i > 0) {
                buffer.append(" - ");
            }
            buffer.append(curCheckCites[i].getName());
        }
        tvHint.setText(mTitle.getText().toString() + "：" + buffer.toString());
    }
}
