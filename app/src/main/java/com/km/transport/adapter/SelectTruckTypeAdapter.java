package com.km.transport.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.entity.TruckTypeEntity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/8/22.
 */

public class SelectTruckTypeAdapter extends BaseAdapter<TruckTypeEntity> implements BaseAdapter.IAdapter<SelectTruckTypeAdapter.ViewHolder> {

    private TruckType truckType;
    public SelectTruckTypeAdapter(Context mContext, TruckType truckType) {
        super(mContext, R.layout.item_rv_select_truck_type);
        this.truckType = truckType;
        addData(new TruckTypeEntity(0,""));
        getItemData(0).setChecked(true);
        setiAdapter(this);
//        setItemClickListener(new ItemClickListener<TruckTypeEntity>() {
//            @Override
//            public void onItemClick(TruckTypeEntity itemData, int position) {
//                checkTruck(itemData);
//            }
//        });
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        final TruckTypeEntity entity = getItemData(position);
        if (truckType == TruckType.LEGNTH){
            holder.tvTruckType.setText(entity.getLength() > 0 ? entity.getLength()+"米" : "不限");
        } else {
            holder.tvTruckType.setText(TextUtils.isEmpty(entity.getType()) ? "不限" : entity.getType());
        }

        holder.tvTruckType.setChecked(entity.isChecked());
        holder.tvTruckType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTruck(entity);
            }
        });
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_truck_type)
        CheckBox tvTruckType;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public enum TruckType {
        LEGNTH,TYPE;
    }

    /**
     * 设置选中
     * @param entity
     */
    private void checkTruck(TruckTypeEntity entity){
        List<TruckTypeEntity> truckTypeEntities = getAllData();
        for (TruckTypeEntity typeEntity : truckTypeEntities){
            typeEntity.setChecked(false);
            if (entity.equals(typeEntity)){
                typeEntity.setChecked(true);
            }
        }
        notifyDataSetChanged();
    }

    public TruckTypeEntity getCheckedEntity(){
        for (TruckTypeEntity entity : getAllData()){
            if (entity.isChecked()){
                return entity;
            }
        }
        return getItemData(0);
    }

    public void checkEntityByWidth(float carWidth){
        for (TruckTypeEntity entity : getAllData()){
            if (carWidth == entity.getLength()){
                entity.setChecked(true);
            } else {
                entity.setChecked(false);
            }
        }
        notifyDataSetChanged();
    }

    public void checkEntityByType(String carType){
        for (int i = 0; i < getItemCount(); i++){
            TruckTypeEntity entity = getItemData(i);
            if (TextUtils.isEmpty(carType) && i == 0){
                entity.setChecked(true);
                break;
            } else if (!TextUtils.isEmpty(carType) && carType.equals(entity.getType())){
                entity.setChecked(true);
            } else {
                entity.setChecked(false);
            }
        }
        notifyDataSetChanged();
    }
}
