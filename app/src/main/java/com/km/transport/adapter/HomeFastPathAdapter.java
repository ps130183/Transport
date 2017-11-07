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
import com.km.transport.dto.HomeFastPathDto;
import com.ps.androidlib.utils.ViewUtils;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/22.
 */

public class HomeFastPathAdapter extends BaseAdapter<HomeFastPathDto> implements BaseAdapter.IAdapter<HomeFastPathAdapter.ViewHolder> {

    private OnDeleteFastPath onDeleteFastPath;

    public HomeFastPathAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_home_fast_path);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        final HomeFastPathDto fastPathDto = getItemData(position);
        StringBuffer fastPathBuffer = new StringBuffer();
        fastPathBuffer.append("起点：")
                .append(fastPathDto.getSourceProvince())
                .append(fastPathDto.getSourceCity())
                .append(fastPathDto.getSourceZoning())
                .append("\n")
                .append("终点：")
                .append(fastPathDto.getBournProvince())
                .append(fastPathDto.getBournCity())
                .append(fastPathDto.getBournZoning())
                .append("\n")
                .append("车型：")
                .append(TextUtils.isEmpty(fastPathDto.getCarWidth()) ? "不限" : fastPathDto.getCarWidth())
                .append("/")
                .append(TextUtils.isEmpty(fastPathDto.getCarType()) ? "不限" : fastPathDto.getCarType());
        holder.tvDestination.setText(fastPathBuffer.toString());

        holder.tvNumber.setText(fastPathDto.getDemandCount()+"");

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteFastPath != null){
                    onDeleteFastPath.deleteFastPath(fastPathDto);
                }
            }
        });
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_destination)
        TextView tvDestination;
        @BindView(R.id.iv_right)
        ImageView ivRight;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class EmptyViewHolder extends BaseAdapter.EmptyViewHolder{

        @BindView(R.id.tv_empty_hint)
        TextView tvEmptyHint;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            tvEmptyHint.setText("没有快捷路线");
        }
    }

    /**
     * 返回空数据
     *
     * @param inflater
     * @param parent
     * @return
     */
    protected EmptyViewHolder getEmptyViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new EmptyViewHolder(ViewUtils.getView(inflater, parent, R.layout.rc_item_empty));
    }


    class FooterViewHolder extends BaseFooterViewHolder {

        @BindView(R.id.btn_add_fast_Path)
        Button btnAddFastPath;

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnDeleteFastPath{
        void deleteFastPath(HomeFastPathDto homeFastPathDto);
    }

    public void setOnDeleteFastPath(OnDeleteFastPath onDeleteFastPath) {
        this.onDeleteFastPath = onDeleteFastPath;
    }
}
