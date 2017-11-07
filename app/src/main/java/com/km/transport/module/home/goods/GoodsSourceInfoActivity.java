package com.km.transport.module.home.goods;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.module.personal.PersonalPresenter;
import com.km.transport.utils.PickerUtils;
import com.lvfq.pickerview.TimePickerView;
import com.ps.androidlib.utils.DateUtils;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.KeyboardUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class GoodsSourceInfoActivity extends BaseActivity<GoodsSourceInfoPresenter> implements GoodsSourceInfoContract.View {

//    @BindView(R.id.rl_low_price)
//    RelativeLayout rlLowPrice;
//    @BindView(R.id.ll_low_price)
//    LinearLayout llLowPrice;
    @BindView(R.id.et_price)
    EditText etPrice;

    @BindView(R.id.tv_receive_time)
    TextView tvReceiveTime;
    @BindView(R.id.tv_destination)
    TextView tvDestination;
    @BindView(R.id.tv_login_time)
    TextView tvLoginTime;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_demand_type)
    TextView tvDemandType;
    @BindView(R.id.tv_demand_number_day)
    TextView tvDemandNumberDay;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_from_address)
    TextView tvFromAddress;
    @BindView(R.id.tv_to_address)
    TextView tvToAddress;
    @BindView(R.id.iv_boss_portrait)
    ImageView ivBossPortrait;
    @BindView(R.id.tv_boss_name)
    TextView tvBossName;
    @BindView(R.id.tv_register_time)
    TextView tvRegisterTime;
    @BindView(R.id.tv_boss_phone)
    TextView tvBossPhone;

    @BindView(R.id.tv_cur_price)
    TextView tvCurPirce;
//    @BindView(R.id.et_goods_weight_plan)
//    EditText etGoodsWeightPlan;
//    @BindView(R.id.btn_low_price)
//    Button btnLowPrice;
//    @BindView(R.id.tv_rmb)
//    TextView tvRmb;
//    @BindView(R.id.btn_low_price1)
//    Button btnLowPrice1;

    private GoodsOrderDetailDto goodsOrderDetailDto;
    @Override
    protected int getContentView() {
        return R.layout.activity_goods_source_info;
    }

    @Override
    protected String getTitleName() {
        return "货源信息";
    }

    @Override
    public GoodsSourceInfoPresenter getmPresenter() {
        return new GoodsSourceInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        loadGoodsOrderDatas();
    }

    private void loadGoodsOrderDatas(){
        goodsOrderDetailDto = getIntent().getParcelableExtra("goodsOrderDetail");
        String curPrice = getIntent().getStringExtra("curPrice");
        if (TextUtils.isEmpty(curPrice)){
            curPrice = "暂无竞价";
        } else {
            curPrice = curPrice + "元/吨";
        }
        tvCurPirce.setText(curPrice);
        if (goodsOrderDetailDto != null){
            StringBuffer fastPathBuffer = new StringBuffer();
            fastPathBuffer.append("起点：")
                    .append(goodsOrderDetailDto.getSourceProvince())
                    .append(goodsOrderDetailDto.getSourceCity())
                    .append(goodsOrderDetailDto.getSourceZoning())
                    .append("\n")
                    .append("终点：")
                    .append(goodsOrderDetailDto.getBournProvince())
                    .append(goodsOrderDetailDto.getBournCity())
                    .append(goodsOrderDetailDto.getBournZoning());
            tvDestination.setText(fastPathBuffer.toString());

            tvLoginTime.setText(goodsOrderDetailDto.getPaccess());

            tvPrice.setText(goodsOrderDetailDto.getPrice() + "元/吨");
            StringBuffer demandTypeBuf = new StringBuffer();
            demandTypeBuf.append(goodsOrderDetailDto.getMaterial()).append("/")
                    .append(goodsOrderDetailDto.getCarWidth()).append("米/")
                    .append(goodsOrderDetailDto.getCarType());
            tvDemandType.setText(demandTypeBuf.toString());
            tvDemandNumberDay.setText(goodsOrderDetailDto.getDayTunnage() + "吨");
            tvRemark.setText(goodsOrderDetailDto.getComment());

            StringBuffer fromBuf = new StringBuffer();
            fromBuf.append(goodsOrderDetailDto.getSourceProvince())
                    .append(goodsOrderDetailDto.getSourceCity())
                    .append(goodsOrderDetailDto.getSourceZoning())
                    .append(goodsOrderDetailDto.getSourceAdressDetail());
            tvFromAddress.setText(fromBuf.toString());

            StringBuffer toBuf = new StringBuffer();
            toBuf.append(goodsOrderDetailDto.getBournProvince())
                    .append(goodsOrderDetailDto.getBournCity())
                    .append(goodsOrderDetailDto.getBournZoning())
                    .append(goodsOrderDetailDto.getBournAdressDetail());
            tvToAddress.setText(toBuf.toString());

            GlideUtils.loadCircleImage(ivBossPortrait,goodsOrderDetailDto.getHeadImg());

            tvBossName.setText(goodsOrderDetailDto.getName());
            tvRegisterTime.setText(goodsOrderDetailDto.getAccess());
            tvBossPhone.setText(goodsOrderDetailDto.getPhone());

            tvReceiveTime.setText(DateUtils.getInstance().dateToString(new Date(goodsOrderDetailDto.getAcceptTime()),DateUtils.YMDHM));

        }
    }


    /**
     * 显示 竞价输入框
     *
     * @param view
     */
    @OnClick(R.id.btn_low_price)
    public void clickLowPrice(View view) {
        final String lowPrice = etPrice.getText().toString();
        if (TextUtils.isEmpty(lowPrice)){
            showToast("请填写接货价格/每吨");
            return;
        }

        float mastPrice = Float.parseFloat(goodsOrderDetailDto.getPrice());
        float userPrice = Float.parseFloat(lowPrice);
        if (userPrice > mastPrice){
            showToast("您输入的价格大于厂家给的价格");
            return;
        }


        DialogUtils.showDefaultAlertDialog("是否以每吨 " + lowPrice + " 元的价格接收这批货物？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                mPresenter.confirmOrder(goodsOrderDetailDto.getId(),lowPrice);
            }
        });

    }

    @OnClick(R.id.tv_boss_phone)
    public void clickPhone(View view){
        final String phone = tvBossPhone.getText().toString();
        if (!TextUtils.isEmpty(phone)){
            DialogUtils.showDefaultAlertDialog("是否拨打 " + phone + "？", new DialogUtils.ClickListener() {
                @Override
                public void clickConfirm() {
                    PermissionGen.needPermission(GoodsSourceInfoActivity.this,1,
                            Manifest.permission.CALL_PHONE);

                }
            });
        }
    }

    @PermissionSuccess(requestCode = 1)
    public void callPhone(){
        String phone = tvBossPhone.getText().toString();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }


//    /**
//     * 确认竞价
//     * @param view
//     */
//    @OnClick(R.id.btn_low_price1)
//    public void clickLowPrice1(View view) {
//        String goodsWeightPlan = etGoodsWeightPlan.getText().toString();
//        String lowPrice = etPrice.getText().toString();
//        if (TextUtils.isEmpty(lowPrice)){
//            showToast("请输入最低价格");
//            return;
//        }
//        if (rlLowPrice.getVisibility() == View.VISIBLE) {
//            rlLowPrice.setVisibility(View.GONE);
//        }
//        mPresenter.confirmOrder(goodsOrderDetailDto.getId(),goodsWeightPlan,lowPrice);
//    }

//    /**
//     * 隐藏 竞价输入框
//     *
//     * @param view
//     */
//    @OnClick(R.id.rl_low_price)
//    public void clickRlLowPrice(View view) {
//        if (rlLowPrice.getVisibility() == View.VISIBLE) {
//            rlLowPrice.setVisibility(View.GONE);
//            KeyboardUtils.hideSoftInput(GoodsSourceInfoActivity.this, etPrice);
//        }
//    }

//    @OnClick(R.id.tv_receive_time)
//    public void chooseReceiveTime(View view) {
//        PickerUtils.alertTimerPicker(GoodsSourceInfoActivity.this, TimePickerView.Type.ALL,
//                DateUtils.getInstance().dateToString(new Date(System.currentTimeMillis()), DateUtils.YMDHM),
//                DateUtils.YMDHM, new PickerUtils.TimerPickerCallBack() {
//                    @Override
//                    public void onTimeSelect(String date) {
//                        tvReceiveTime.setText(date);
//                    }
//                });
//    }


    @Override
    public void confirmOrderSuccess(String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString("orderId",orderId);
        toNextActivity(GoodsOrderInfoActivity.class,bundle);
    }
}
