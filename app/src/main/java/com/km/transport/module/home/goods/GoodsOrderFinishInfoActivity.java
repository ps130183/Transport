package com.km.transport.module.home.goods;

import android.text.TextUtils;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseActivity;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.ps.androidlib.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;

public class GoodsOrderFinishInfoActivity extends BaseActivity<GoodsOrderInfoPresenter> implements GoodsOrderInfoContract.View {

    @BindView(R.id.tv_destination)
    TextView tvDestination;
    @BindView(R.id.tv_from_address)
    TextView tvFromAddress;
    @BindView(R.id.tv_to_address)
    TextView tvToAddress;
    @BindView(R.id.tv_from_unit)
    TextView tvFromUnit;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_unit_price)
    TextView tvUnitPrice;
    @BindView(R.id.tv_user_price)
    TextView tvUserPrice;
    @BindView(R.id.tv_weight_out)
    TextView tvWeightOut;
    @BindView(R.id.tv_time_out)
    TextView tvTimeOut;
    @BindView(R.id.tv_weight_in)
    TextView tvWeightIn;
    @BindView(R.id.tv_time_in)
    TextView tvTimeIn;
    @BindView(R.id.tv_client_Name)
    TextView tvClientName;
    @BindView(R.id.tv_all_money)
    TextView tvAllMoney;

    @Override
    protected int getContentView() {
        return R.layout.activity_goods_order_finish_info;
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
        mPresenter.getGoodsOrderInfo(orderId);
    }

    @Override
    public void showGoodsOrderInfo(GoodsOrderDetailDto goodsOrderDetailDto) {
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

        tvClientName.setText(goodsOrderDetailDto.getClientName());
        tvFromUnit.setText(goodsOrderDetailDto.getManufacturer());
        tvOrderNumber.setText(goodsOrderDetailDto.getOrderNo());

        tvUnitPrice.setText(goodsOrderDetailDto.getQuote() + "元/吨");
        tvUserPrice.setText(goodsOrderDetailDto.getDealQuote() + "元/吨");

        tvWeightOut.setText(TextUtils.isEmpty(goodsOrderDetailDto.getOutTunnage()) ? "" : goodsOrderDetailDto.getOutTunnage() + "吨");
        tvTimeOut.setText(goodsOrderDetailDto.getSourceTime() == 0 ? "" : DateUtils.getInstance().dateToString(new Date(goodsOrderDetailDto.getSourceTime()), DateUtils.YMDHM));

        tvWeightIn.setText(TextUtils.isEmpty(goodsOrderDetailDto.getBournTunnage()) ? "" : goodsOrderDetailDto.getBournTunnage() + "吨");
        tvTimeIn.setText(goodsOrderDetailDto.getBournTime() == 0 ? "" : DateUtils.getInstance().dateToString(new Date(goodsOrderDetailDto.getBournTime()), DateUtils.YMDHM));

        tvAllMoney.setText(TextUtils.isEmpty(goodsOrderDetailDto.getSumPrice()) ? "" : goodsOrderDetailDto.getSumPrice() + "元");
    }

    @Override
    public void cancelOrderSuccess() {

    }
}
