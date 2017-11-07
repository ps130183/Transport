package com.km.transport.module.personal.approve;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;

import com.km.transport.R;
import com.km.transport.adapter.ChooseCarAddressAdapter;
import com.km.transport.basic.BaseActivity;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.RVUtils;
import com.km.transport.event.ChooseCarAddressEvent;
import com.km.transport.event.ChooseCarTypeEvent;
import com.km.transport.greendao.City;
import com.km.transport.greendao.CityManager;
import com.km.transport.utils.Constant;
import com.ps.androidlib.utils.EventBusUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChooseCarAddressActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private City[] mCheckCitys = new City[3];

    @Override
    protected int getContentView() {
        return R.layout.activity_choose_car_address;
    }

    @Override
    protected String getTitleName() {
        return "车辆所在地";
    }

    @Override
    protected void onCreate() {
        setClickKeyCodeBackLisenter(new OnClickKeyCodeBackLisenter() {
            @Override
            public boolean onClickKeyCodeBack() {
                ChooseCarAddressAdapter adapter = (ChooseCarAddressAdapter) recyclerView.getAdapter();
                if (mCheckCitys[2] != null){
                    adapter.addData(CityManager.getInstance().getPreCitys(mCheckCitys[2].getParentId()));
                    mCheckCitys[2] = null;
                } else if (mCheckCitys[1] != null){
                    adapter.addData(CityManager.getInstance().getProvinces());
                    mCheckCitys[1] = null;
                } else {
                    finish();
                }
                return false;
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        RVUtils.setLinearLayoutManage(recyclerView, LinearLayoutCompat.VERTICAL);
        RVUtils.addDivideItemForRv(recyclerView, RVUtils.DIVIDER_COLOR_DEFAULT, 2);
        final ChooseCarAddressAdapter adapter = new ChooseCarAddressAdapter(this);
        recyclerView.setAdapter(adapter);

        adapter.addData(CityManager.getInstance().getProvinces());

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<City>() {
            @Override
            public void onItemClick(City itemData, int position) {
                if (itemData.getType() == 2){//省
                    adapter.addData(CityManager.getInstance().getCitys(itemData.getId()));
                    mCheckCitys[0] = itemData;
                } else if (itemData.getType() == 3){//市
                    adapter.addData(CityManager.getInstance().getAreas(itemData.getId()));
                    mCheckCitys[1] = itemData;
                } else {//区
                    mCheckCitys[2] = itemData;
                    EventBusUtils.post(new ChooseCarAddressEvent(mCheckCitys));
                    finish();
                }
            }

        });
    }
}
