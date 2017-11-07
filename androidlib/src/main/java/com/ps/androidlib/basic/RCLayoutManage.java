package com.ps.androidlib.basic;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by kamangkeji on 17/1/19.
 */

public class RCLayoutManage {

    public static void setLinearLayoutManage(RecyclerView rc,int orientation){
        LinearLayoutManager llm = new LinearLayoutManager(rc.getContext(),orientation,false);
        rc.setLayoutManager(llm);
    }
}
