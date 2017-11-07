package com.km.transport.adapter;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PengSong on 17/9/22.
 */

public class ChooseCarTypeAdapter extends BaseAdapter<String> implements BaseAdapter.IAdapter<ChooseCarTypeAdapter.ViewHolder> {

    public ChooseCarTypeAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_choose_car_type);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        String type = getItemData(position);
        holder.tvTypeName.setText(type);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_type_name)
        TextView tvTypeName;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
