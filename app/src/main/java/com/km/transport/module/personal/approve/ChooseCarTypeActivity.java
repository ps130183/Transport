package com.km.transport.module.personal.approve;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.adapter.ChooseCarTypeAdapter;
import com.km.transport.basic.BaseActivity;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.basic.RVUtils;
import com.km.transport.event.ChooseCarTypeEvent;
import com.km.transport.utils.Constant;
import com.ps.androidlib.utils.EventBusUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChooseCarTypeActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getContentView() {
        return R.layout.activity_choose_car_type;
    }

    @Override
    protected String getTitleName() {
        return "车辆类型";
    }

    @Override
    protected void onCreate() {
        initRecyclerView();
    }

    private void initRecyclerView(){
        RVUtils.setLinearLayoutManage(recyclerView, LinearLayoutCompat.VERTICAL);
        RVUtils.addDivideItemForRv(recyclerView,RVUtils.DIVIDER_COLOR_DEFAULT,2);
        ChooseCarTypeAdapter adapter = new ChooseCarTypeAdapter(this);
        recyclerView.setAdapter(adapter);

        List<String> typeList = new ArrayList<>();
        for (String type : Constant.TRUCK_TYPES){
            typeList.add(type);
        }
        adapter.addData(typeList);

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<String>() {
            @Override
            public void onItemClick(String itemData, int position) {
                EventBusUtils.post(new ChooseCarTypeEvent(itemData));
                finish();
            }

        });
    }
}
