package com.km.transport.module.home.path.newpath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.km.transport.R;
import com.km.transport.adapter.SelectTruckTypeAdapter;
import com.km.transport.basic.BaseActivity;
import com.km.transport.basic.RVUtils;
import com.km.transport.entity.TruckTypeEntity;
import com.km.transport.event.CheckTruckEvent;
import com.ps.androidlib.utils.EventBusUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectTruckTypeActivity extends BaseActivity {

    @BindView(R.id.rv_truck_length)
    RecyclerView rvTruckLength;
    @BindView(R.id.rv_truck_type)
    RecyclerView rvTrcukType;
    private float[] truckLengths = {4.2f,4.5f,5,5.2f,6.2f,6.8f,7.2f,7.6f,8.2f,8.6f,9.6f,11.7f,12.5f,13,13.5f,14,15,16,17,17.5f,18};
    private String[] truckTypes = {"六轴仓栏","六轴自卸","六轴罐车","四轴翻斗","四轴平板"};

    @Override
    protected int getContentView() {
        return R.layout.activity_select_truck_type;
    }

    @Override
    protected String getTitleName() {
        return "车长车型";
    }

    @Override
    protected void onCreate() {
        initTrcukType();
    }

    /**
     * 初始化 选择 车型车长
     */
    private SelectTruckTypeAdapter truckLengthAdapter;
    private SelectTruckTypeAdapter trcukTypeAdapter;
    private void initTrcukType(){
        RVUtils.setGridLayoutManage(rvTruckLength,4);
        truckLengthAdapter = new SelectTruckTypeAdapter(this, SelectTruckTypeAdapter.TruckType.LEGNTH);
        rvTruckLength.setAdapter(truckLengthAdapter);
        List<TruckTypeEntity> truckLengthEntitys = new ArrayList<>();
        for (float length : truckLengths){
            truckLengthEntitys.add(new TruckTypeEntity(length,""));
        }
        truckLengthAdapter.addData(truckLengthEntitys,2);

        RVUtils.setGridLayoutManage(rvTrcukType,3);
        trcukTypeAdapter = new SelectTruckTypeAdapter(this, SelectTruckTypeAdapter.TruckType.TYPE);
        rvTrcukType.setAdapter(trcukTypeAdapter);
        List<TruckTypeEntity> truckTypeEntities = new ArrayList<>();
        for (String type : truckTypes){
            truckTypeEntities.add(new TruckTypeEntity(-1,type));
        }
        trcukTypeAdapter.addData(truckTypeEntities,2);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm(View view){
        EventBusUtils.post(new CheckTruckEvent(truckLengthAdapter.getCheckedEntity(),trcukTypeAdapter.getCheckedEntity()));
        finish();
    }
}
