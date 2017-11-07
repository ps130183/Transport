package com.km.transport.module.home.path.newpath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.event.AddFastPathSuccessEvent;
import com.km.transport.event.CheckCityEvent;
import com.km.transport.event.CheckTruckEvent;
import com.km.transport.utils.retrofit.SecretConstant;
import com.ps.androidlib.utils.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFastPathActivity extends BaseActivity<AddFastPathPresenter> implements AddFastPathContract.View {

    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_end)
    TextView tvEnd;

    @BindView(R.id.tv_truck_type)
    TextView tvTruckType;

    private String sourceProvince;
    private String sourceCity;
    private String sourceZoning;
    private String bournProvince;
    private String bournCity;
    private String bournZoning;
    private String carType;
    private String carWidth;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_fast_path;
    }

    @Override
    protected String getTitleName() {
        return "添加快捷路线";
    }

    @Override
    public AddFastPathPresenter getmPresenter() {
        return new AddFastPathPresenter(this);
    }

    @Override
    protected void onCreate() {

    }

    /**
     * 选择出发地
     *
     * @param view
     */
    @OnClick({R.id.rl_start, R.id.tv_start, R.id.iv_start_down_arrow})
    public void clickSelectStart(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("cityType", 0);
        toNextActivity(SelectCityActivity.class, bundle);
    }

    /**
     * 选择目的地
     *
     * @param view
     */
    @OnClick({R.id.rl_end, R.id.tv_end, R.id.iv_end_down_arrow})
    public void clickSelectEnd(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("cityType", 1);
        toNextActivity(SelectCityActivity.class, bundle);
    }

    /**
     * 选择车长车型
     *
     * @param view
     */
    @OnClick({R.id.rl_truck_type, R.id.tv_truck_type, R.id.iv_truck_type_down_arrow})
    public void clickSelectTruckType(View view) {
        toNextActivity(SelectTruckTypeActivity.class);
    }

    @OnClick(R.id.btn_confirm)
    public void confirm(View view) {
        if (TextUtils.isEmpty(sourceProvince) || TextUtils.isEmpty(sourceCity) || TextUtils.isEmpty(sourceZoning)) {
            showToast("请选择起点城市");
            return;
        } else if (TextUtils.isEmpty(bournProvince) || TextUtils.isEmpty(bournCity) || TextUtils.isEmpty(bournZoning)) {
            showToast("请选择终点城市");
            return;
        }
        mPresenter.addFastPath(sourceProvince, sourceCity, sourceZoning, bournProvince, bournCity, bournZoning, carType, carWidth);
    }

    /**
     * 接受 起点 和终点的 选择
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCheckedCity(CheckCityEvent event) {
        if (event.getCityType() == 0) {//起点
            if (event.getCheckedCites()[0] != null) {
                sourceProvince = event.getCheckedCites()[0].getName();
            } else {
                sourceProvince = "";
            }
            if (event.getCheckedCites()[1] != null) {
                sourceCity = event.getCheckedCites()[1].getName();
            } else {
                sourceCity = "";
            }
            if (event.getCheckedCites()[2] != null) {
                sourceZoning = event.getCheckedCites()[2].getName();
            } else {
                sourceZoning = "";
            }

            if (TextUtils.isEmpty(sourceProvince + sourceCity + sourceZoning)) {
                tvStart.setText("不限");
            } else {
                tvStart.setText(sourceProvince + sourceCity + sourceZoning);
            }

        } else if (event.getCityType() == 1) {//终点
            if (event.getCheckedCites()[0] != null) {
                bournProvince = event.getCheckedCites()[0].getName();
            } else {
                bournProvince = "";
            }
            if (event.getCheckedCites()[1] != null) {
                bournCity = event.getCheckedCites()[1].getName();
            } else {
                bournCity = "";
            }
            if (event.getCheckedCites()[2] != null) {
                bournZoning = event.getCheckedCites()[2].getName();
            } else {
                bournZoning = "";
            }

            if (TextUtils.isEmpty(bournProvince + bournCity + bournZoning)) {
                tvEnd.setText("不限");
            } else {
                tvEnd.setText(bournProvince + bournCity + bournZoning);
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCheckTruck(CheckTruckEvent event) {
        carType = event.getTruckType().getType();
        carWidth = event.getTruckLength().getLength() > 0 ? event.getTruckLength().getLength() + "" : "";

        StringBuffer truckType = new StringBuffer();
        if (!TextUtils.isEmpty(carWidth)) {
            truckType.append(carWidth);
        } else {
            truckType.append("不限");
        }
        truckType.append("/");
        if (!TextUtils.isEmpty(carType)) {
            truckType.append(carType);
        } else {
            truckType.append("不限");
        }

        tvTruckType.setText(truckType.toString());

    }

    @Override
    public void addFastPathSuccess() {
        showToast("添加成功");
        EventBusUtils.post(new AddFastPathSuccessEvent());
        finish();
    }
}
