package com.km.transport.utils;

import android.support.v4.widget.SwipeRefreshLayout;

import com.km.transport.R;


/**
 * Created by kamangkeji on 17/6/2.
 */

public class SwipeRefreshUtils {

    public static void initSwipeRefresh(SwipeRefreshLayout mSwipeLayout, SwipeRefreshLayout.OnRefreshListener onRefreshListener){
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(R.color.color_white);
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeLayout.setOnRefreshListener(onRefreshListener);
    }

}
