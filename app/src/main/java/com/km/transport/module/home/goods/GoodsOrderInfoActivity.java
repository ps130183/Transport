package com.km.transport.module.home.goods;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.km.transport.event.ShipmentEvent;
import com.km.transport.module.MainActivity;
import com.km.transport.module.order.unfinished.PutStorageActivity;
import com.km.transport.module.order.unfinished.ShipMentActivity;
import com.km.transport.utils.QRCodeUtils;
import com.ps.androidlib.utils.DateUtils;
import com.ps.androidlib.utils.DialogUtils;
import com.ps.androidlib.utils.EventBusUtils;

import java.util.Date;

import butterknife.BindView;

public class GoodsOrderInfoActivity extends BaseActivity<GoodsOrderInfoPresenter> implements GoodsOrderInfoContract.View {

    @BindView(R.id.tv_destination)
    TextView tvDestination;
    @BindView(R.id.tv_login_time)
    TextView tvLoginTime;
    @BindView(R.id.tv_mast_price)
    TextView tvMastPrice;
    @BindView(R.id.tv_car_type)
    TextView tvCarType;
    @BindView(R.id.tv_demand_number_day)
    TextView tvDemandNumberDay;
    @BindView(R.id.tv_receive_time)
    TextView tvReceiveTime;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_from_address)
    TextView tvFromAddress;
    @BindView(R.id.tv_to_address)
    TextView tvToAddress;

    @BindView(R.id.btn_send_goods)
    Button btnSendGoods;

    @BindView(R.id.iv_order_qr_code)
    ImageView ivOrderQRCode;

    @BindView(R.id.ll_order_info)
    LinearLayout llOrderInfo;

    @BindView(R.id.ll_stock_info)
    LinearLayout llStockInfo;
    @BindView(R.id.tv_stock_banks)
    TextView tvStockBanks;
    @BindView(R.id.tv_client_name)
    TextView tvClientName;
    @BindView(R.id.tv_manufacture)
    TextView tvManufacture;
    @BindView(R.id.tv_stock_units)
    TextView tvStockUnits;
    @BindView(R.id.tv_license_plate)
    TextView tvLicensePlate;
    @BindView(R.id.tv_out_tunnage)
    TextView tvOutTunnage;

    private boolean isOrderDetails;

    @Override
    protected int getContentView() {
        return R.layout.activity_goods_order_info;
    }

    @Override
    protected String getTitleName() {
        return "货单信息";
    }

    @Override
    public GoodsOrderInfoPresenter getmPresenter() {
        return new GoodsOrderInfoPresenter(this);
    }

    @Override
    protected void onCreate() {
        String orderId = getIntent().getStringExtra("orderId");
        isOrderDetails = getIntent().getBooleanExtra("orderDetails",true);
        mPresenter.getGoodsOrderInfo(orderId);

    }

    @Override
    public void showGoodsOrderInfo(final GoodsOrderDetailDto goodsOrderDetailDto) {
        if (isOrderDetails){
            llOrderInfo.setVisibility(View.VISIBLE);
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

            tvMastPrice.setText(goodsOrderDetailDto.getDealQuote() + "元/吨");
            StringBuffer demandTypeBuf = new StringBuffer();
            demandTypeBuf.append(goodsOrderDetailDto.getMaterial()).append("/")
                    .append(TextUtils.isEmpty(goodsOrderDetailDto.getTunnage()) ? "" : goodsOrderDetailDto.getTunnage()+"吨/")
                    .append(goodsOrderDetailDto.getCarWidth()).append("米/")
                    .append(goodsOrderDetailDto.getCarType());
            tvCarType.setText(demandTypeBuf.toString());
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

            tvReceiveTime.setText(DateUtils.getInstance().dateToString(new Date(goodsOrderDetailDto.getAcceptTime()), DateUtils.YMDHM));
        } else {
            if (goodsOrderDetailDto.getStatus() == 2 || goodsOrderDetailDto.getStatus() == 5){
                //二维码
                ivOrderQRCode.setVisibility(View.VISIBLE);
                ivOrderQRCode.setImageBitmap(QRCodeUtils.createQRCode(GoodsOrderInfoActivity.this, goodsOrderDetailDto.getCodeUrl()));

                llStockInfo.setVisibility(View.VISIBLE);
                tvStockBanks.setText(goodsOrderDetailDto.getStockBanks());
                tvClientName.setText(goodsOrderDetailDto.getClientName());
                tvManufacture.setText(goodsOrderDetailDto.getManufacturer());
                tvStockUnits.setText(goodsOrderDetailDto.getStockUnits());
                tvLicensePlate.setText(goodsOrderDetailDto.getLicensePlate());
                tvOutTunnage.setText(goodsOrderDetailDto.getTunnage() + "吨");
            }
        }

        if (goodsOrderDetailDto.getStatus() == 1 || goodsOrderDetailDto.getStatus() == 2){
            setRightBtnClick("取消订单", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.showDefaultAlertDialog("取消订单前，请务必联系货主！", new DialogUtils.ClickListener() {
                        @Override
                        public void clickConfirm() {
                            mPresenter.cancelOrder(goodsOrderDetailDto.getId());
                        }
                    });
                }
            });
        }

        if (goodsOrderDetailDto.getStatus() == 2) {
            btnSendGoods.setVisibility(View.VISIBLE);
            btnSendGoods.setText("开始发货");
            btnSendGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId",goodsOrderDetailDto.getId());
                    toNextActivity(ShipMentActivity.class,bundle);
                }
            });

        } else if (goodsOrderDetailDto.getStatus() == 5) {
            btnSendGoods.setVisibility(View.VISIBLE);
            btnSendGoods.setText("完成送货");
            btnSendGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toNextActivity(PutStorageActivity.class);
                }
            });
        } else {
            btnSendGoods.setVisibility(View.GONE);
        }



    }

    @Override
    public void cancelOrderSuccess() {
        EventBusUtils.post(new ShipmentEvent());
        toNextActivity(MainActivity.class);
    }
}
