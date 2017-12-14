package com.km.transport.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.km.transport.R;

/**
 * Created by PengSong on 17/12/14.
 */
public class CustomMF extends MarqueeFactory<View, String> {
    private LayoutInflater inflater;

    public CustomMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    protected View generateMarqueeItemView(String data) {
        View view = inflater.inflate(R.layout.custom_marquee_view, null, false);
        TextView content = (TextView) view.findViewById(R.id.tv_content);
        content.setText(data);
        return view;
    }
}
