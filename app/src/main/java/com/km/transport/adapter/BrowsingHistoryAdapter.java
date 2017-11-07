package com.km.transport.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.dto.BrowsingHistoryDto;
import com.ps.androidlib.utils.glide.GlideUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/22.
 */

public class BrowsingHistoryAdapter extends BaseAdapter<BrowsingHistoryDto> implements BaseAdapter.IAdapter<BrowsingHistoryAdapter.ViewHolder> {

    private OnClickHireListener onClickHireListener;


    public BrowsingHistoryAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_browsing_history);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        final BrowsingHistoryDto browsingHistoryDto = getItemData(position);
        StringBuffer fastPathBuffer = new StringBuffer();
        fastPathBuffer.append("起点：")
                .append(browsingHistoryDto.getSourceProvince())
                .append(browsingHistoryDto.getSourceCity())
                .append(browsingHistoryDto.getSourceZoning())
                .append("\n")
                .append("终点：")
                .append(browsingHistoryDto.getBournProvince())
                .append(browsingHistoryDto.getBournCity());
        holder.tvDestination.setText(fastPathBuffer.toString());

        StringBuffer demandTypeBuf = new StringBuffer();
        demandTypeBuf.append(browsingHistoryDto.getMaterial()).append("/")
                .append(browsingHistoryDto.getTunnage()).append("吨/")
                .append(browsingHistoryDto.getCarWidth()).append("米/")
                .append(browsingHistoryDto.getCarType());
        holder.tvGoodsInfo.setText(demandTypeBuf.toString());
        GlideUtils.loadCircleImage(holder.ivUserPortrait,browsingHistoryDto.getHeadImg());
        holder.tvNikeName.setText(browsingHistoryDto.getUserName());

        holder.btnHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickHireListener != null){
                    onClickHireListener.clickHire(browsingHistoryDto);
                }
            }
        });
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_destination)
        TextView tvDestination;
        @BindView(R.id.tv_goods_info)
        TextView tvGoodsInfo;
        @BindView(R.id.iv_user_portrait)
        ImageView ivUserPortrait;
        @BindView(R.id.tv_nike_name)
        TextView tvNikeName;

        @BindView(R.id.btn_hire)
        Button btnHire;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnClickHireListener{
        void clickHire(BrowsingHistoryDto browsingHistoryDto);
    }

    public void setOnClickHireListener(OnClickHireListener onClickHireListener) {
        this.onClickHireListener = onClickHireListener;
    }
}
