package com.ps.androidlib.utils;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/1/17.
 */

public class MLog {

    static {
//        LogUtils.getLogConfig()
//                .configAllowLog(true)//是否允许打印日志
//                .configTagPrefix("ANTZ")//日志头标题
//                .configShowBorders(true)//是否显示边界
//                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}")//格式化日期
//                .configLevel(LogLevel.TYPE_VERBOSE);//日志等级
    }

    /**
     * 打印日志
     * @param info
     */
    public static void d(String info){
        Logger.d(info);
    }

    /**
     * 打印日志
     * @param TAG
     * @param info
     */
    public static void d(String TAG,String info){
        Logger.d(info);
    }

}
