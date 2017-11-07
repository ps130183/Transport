package com.km.transport.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.km.transport.R;
import com.km.transport.basic.BaseAdapter;
import com.km.transport.dto.MessageDto;
import com.ps.androidlib.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PengSong on 17/9/27.
 */

public class MessageAdapter extends BaseAdapter<MessageDto> implements BaseAdapter.IAdapter<MessageAdapter.ViewHolder> {

    public MessageAdapter(Context mContext) {
        super(mContext, R.layout.item_rv_message);
        setiAdapter(this);
    }

    @Override
    public ViewHolder createViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    public void createView(ViewHolder holder, int position) {
        MessageDto messageDto = getItemData(position);
        if (messageDto.getType() == 1){
            holder.tvMessageSource.setText("这是一条来自货运订单的消息");
        } else if (messageDto.getType() == 2){
            holder.tvMessageSource.setText("这是一条来自平台的消息");
        } else {
            holder.tvMessageSource.setText("消息");
        }

        holder.tvMessageTime.setText(DateUtils.getInstance().dateToString(new Date(messageDto.getCreateDate()),DateUtils.YMDHMS));
        holder.tvMessageContent.setText(messageDto.getContent());


    }

    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.tv_message_source)
        TextView tvMessageSource;
        @BindView(R.id.tv_message_time)
        TextView tvMessageTime;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
