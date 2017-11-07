package com.km.transport.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.km.transport.R;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.dto.UserAccountDetailDto;
import com.ps.androidlib.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class UserAccountDetailAdapter extends BaseAdapter<UserAccountDetailDto> implements BaseAdapter.IAdapter<UserAccountDetailAdapter.ViewHolder> {

    public UserAccountDetailAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_account_details);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        UserAccountDetailDto userAccountDetailDto = getItemData(position);
        holder.tvExplain.setText(userAccountDetailDto.getContent());
        StringBuffer account = new StringBuffer();
        if ("1".equals(userAccountDetailDto.getTradeType()) || "2".equals(userAccountDetailDto.getTradeType())){
            account.append("- ");
            holder.tvMoney.setTextColor(mContext.getResources().getColor(R.color.color_tangerine_level_1));
        } else if ("3".equals(userAccountDetailDto.getTradeType()) || "4".equals(userAccountDetailDto.getTradeType())){
            account.append("+ ");
            holder.tvMoney.setTextColor(mContext.getResources().getColor(R.color.color_text_account));
        }
        account.append(userAccountDetailDto.getAmount());
        holder.tvMoney.setText(account.toString());
        holder.tvTime.setText(DateUtils.getInstance().dateToString(new Date(userAccountDetailDto.getCreateDate()),DateUtils.YMDHM));
    }

    class ViewHolder extends BaseViewHolder{

        @BindView(R.id.tv_explain)
        TextView tvExplain;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
