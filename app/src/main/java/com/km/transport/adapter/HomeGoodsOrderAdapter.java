package com.km.transport.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.dto.HomeGoodsOrderDto;
import com.ps.androidlib.utils.DateUtils;
import com.ps.androidlib.utils.ViewUtils;
import com.ps.androidlib.utils.glide.GlideUtils;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/22.
 */

public class HomeGoodsOrderAdapter extends BaseAdapter<HomeGoodsOrderDto> implements BaseAdapter.IAdapter<HomeGoodsOrderAdapter.ViewHolder> {


    private OnClickHireListener onClickHireListener;

    public HomeGoodsOrderAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_home_goods_order);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, final int position) {
        final HomeGoodsOrderDto content = getItemData(position);
        holder.btnHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickHireListener != null) {
                    onClickHireListener.hireOrder(content, position);
                }
            }
        });

        holder.tvStart.setText(content.getSourceProvince() + content.getSourceCity() + content.getSourceZoning());
        holder.tvEnd.setText(content.getBournProvince() + content.getBournCity() + content.getBournZoning());
        StringBuffer goodsInfoBuffer = new StringBuffer();
        goodsInfoBuffer.append(content.getMaterial()).append("/");
        goodsInfoBuffer.append(content.getDayTunnage()).append("/");
        goodsInfoBuffer.append(content.getCarWidth());
        if ("不限".equals(content.getCarWidth())){
            goodsInfoBuffer.append("车长");
        } else {
            goodsInfoBuffer.append("米");
        }
        goodsInfoBuffer.append("/").append(content.getCarType());
        if ("不限".equals(content.getCarType())){
            goodsInfoBuffer.append("车型");
        }
        holder.tvGoodsInfo.setText(goodsInfoBuffer.toString());

        GlideUtils.loadCircleImage(holder.ivUserPortrait,content.getHeadImg());
        holder.tvNikeName.setText(content.getName());
        holder.tvRegisterTime.setText(content.getAccess());
        holder.tvLoginTime.setText(content.getPaccess());

//        if (TextUtils.isEmpty(content.getDownPrice())){
//            holder.tvDownPrice.setVisibility(View.GONE);
//        } else {
//            holder.tvDownPrice.setVisibility(View.VISIBLE);
//            holder.tvDownPrice.setText(content.getDownPrice() + "/吨");
//        }

        holder.tvBiddingTime.setText(DateUtils.getInstance().dateToString(new Date(content.getBiddingTime()),DateUtils.YMDHM));
    }


    class EmptyHolder extends EmptyViewHolder{

        @BindView(R.id.tv_empty_hint)
        TextView tvEmptyHint;

        public EmptyHolder(View itemView) {
            super(itemView);
            tvEmptyHint.setText("没有货源信息");
        }
    }

    @Override
    protected EmptyViewHolder getEmptyViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new EmptyHolder(ViewUtils.getView(inflater, parent, R.layout.rc_item_empty));
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_start)
        TextView tvStart;
        @BindView(R.id.tv_end)
        TextView tvEnd;
        @BindView(R.id.tv_goods_info)
        TextView tvGoodsInfo;
        @BindView(R.id.iv_user_portrait)
        ImageView ivUserPortrait;
        @BindView(R.id.tv_nike_name)
        TextView tvNikeName;
        @BindView(R.id.tv_register_time)
        TextView tvRegisterTime;
        @BindView(R.id.tv_login_time)
        TextView tvLoginTime;
        @BindView(R.id.btn_hire)
        Button btnHire;

        @BindView(R.id.tv_down_price)
        TextView tvDownPrice;
        @BindView(R.id.tv_bidding_time)
        TextView tvBiddingTime;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnClickHireListener {
        void hireOrder(HomeGoodsOrderDto itemData, int position);
    }

    public void setOnClickHireListener(OnClickHireListener onClickHireListener) {
        this.onClickHireListener = onClickHireListener;
    }
}
