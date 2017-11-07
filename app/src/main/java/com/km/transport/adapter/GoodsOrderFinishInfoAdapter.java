package com.km.transport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.dto.GoodsOrderDetailDto;
import com.ps.androidlib.utils.MToast;
import com.ps.androidlib.utils.ViewUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/23.
 */

public class GoodsOrderFinishInfoAdapter extends BaseAdapter<GoodsOrderDetailDto> implements BaseAdapter.IAdapter<GoodsOrderFinishInfoAdapter.ViewHolder> {

    private OnClickGoodsFunctionListener onClickGoodsFunctionListener;

    public GoodsOrderFinishInfoAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_goods_order_finish_info);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(final ViewHolder holder, final int position) {
        final GoodsOrderDetailDto data = getItemData(position);
        holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_tangerine_level_1));
        if (data.getStatus() == 1) {
            holder.llBottom.setVisibility(View.GONE);
            holder.tvStatus.setText("竞价中");
        } else if (data.getStatus() == 2) {
            holder.llBottom.setVisibility(View.VISIBLE);
            holder.btnFunction.setText("去取货");
            holder.tvStatus.setText("待取货");
        } else if (data.getStatus() == 3) {
            holder.llBottom.setVisibility(View.GONE);
            holder.tvStatus.setText("竞价失败");
        } else if (data.getStatus() == 4) {//取消订单
            holder.llBottom.setVisibility(View.GONE);
            holder.tvStatus.setText("已取消");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_text_gray));
        } else if (data.getStatus() == 5){
            holder.llBottom.setVisibility(View.VISIBLE);
            holder.btnFunction.setText("确认送达");
            holder.tvStatus.setText("待送达");
        } else if (data.getStatus() == 6) {//后台操作
            holder.llBottom.setVisibility(View.VISIBLE);
            holder.btnFunction.setText("确认送达");
            holder.tvStatus.setText("待送达");
        } else if (data.getStatus() == 7){
            holder.llBottom.setVisibility(View.GONE);
            holder.tvStatus.setText("已完成");
        }

        if (onClickGoodsFunctionListener != null) {
            holder.btnFunction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (data.getStatus()){
                        case 2:
                            onClickGoodsFunctionListener.clickGoodsShipment(data, position);
                            break;
                        case 4:
                            MToast.showToast(mContext,"您已上传取货单据，请等待后台确认");
                            break;
                        case 5:
                            onClickGoodsFunctionListener.clickGoodsTake(data, position);
                            break;
                        case 6:
                            MToast.showToast(mContext,"您已上传送货单据，请等待后台确认");
                            break;
                    }
                }
            });
        }


        holder.tvCompany.setText(data.getDeliverCompany());
        StringBuffer fastPathBuffer = new StringBuffer();
        fastPathBuffer.append("起点：")
                .append(data.getSourceProvince())
                .append(data.getSourceCity())
                .append(data.getSourceZoning())
                .append("\n")
                .append("终点：")
                .append(data.getBournProvince())
                .append(data.getBournCity())
                .append(data.getBournZoning());
        holder.tvDestination.setText(fastPathBuffer.toString());

        StringBuffer demandTypeBuf = new StringBuffer();
        demandTypeBuf.append(data.getMaterial()).append("/")
                .append(data.getDayTunnage()).append("吨/")
                .append(data.getCarWidth()).append("米/")
                .append(data.getCarType());
        holder.tvGoodsInfo.setText(demandTypeBuf.toString());

        GlideUtils.loadCircleImage(holder.ivUserPortrait, data.getHeadImg());
        holder.tvNikeName.setText(data.getName());
        holder.tvRegisterTime.setText(data.getAccess());
    }

    class EmptyHolder extends EmptyViewHolder{

        @BindView(R.id.tv_empty_hint)
        TextView tvEmptyHint;

        public EmptyHolder(View itemView) {
            super(itemView);
            tvEmptyHint.setText("没有订单");
        }
    }

    @Override
    protected EmptyViewHolder getEmptyViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new GoodsOrderFinishInfoAdapter.EmptyHolder(ViewUtils.getView(inflater, parent, R.layout.rc_item_empty));
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_destination)
        TextView tvDestination;
        @BindView(R.id.tv_goods_info)
        TextView tvGoodsInfo;
        @BindView(R.id.iv_user_portrait)
        ImageView ivUserPortrait;
        @BindView(R.id.tv_nike_name)
        TextView tvNikeName;
        @BindView(R.id.tv_register_time)
        TextView tvRegisterTime;
        @BindView(R.id.btn_function)
        Button btnFunction;
        @BindView(R.id.ll_bottom)
        LinearLayout llBottom;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnClickGoodsFunctionListener {
        //出货
        void clickGoodsShipment(GoodsOrderDetailDto itemData, int position);
        //收货
        void clickGoodsTake(GoodsOrderDetailDto itemData, int position);
    }

    public void setOnClickGoodsFunctionListener(OnClickGoodsFunctionListener onClickGoodsFunctionListener) {
        this.onClickGoodsFunctionListener = onClickGoodsFunctionListener;
    }
}
