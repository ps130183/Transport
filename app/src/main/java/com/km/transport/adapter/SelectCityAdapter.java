package com.km.transport.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.entity.CityEntity;
import com.km.transport.greendao.City;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/22.
 */

public class SelectCityAdapter extends BaseAdapter<City> implements BaseAdapter.IAdapter<SelectCityAdapter.ViewHolder> {

    public SelectCityAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_select_city);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        City entity = getItemData(position);
        holder.tvCity.setText(entity.getName());
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_city)
        TextView tvCity;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
