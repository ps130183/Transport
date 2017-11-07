package com.ps.androidlib.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/2/13.
 */

public class Utils {

    /**
     * 将字符创数组转换为list
     *
     * @param datas
     * @return
     */
    public static List<String> parseStringToList(String[] datas) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            strings.add(datas[i]);
        }
        return strings;
    }

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

}
