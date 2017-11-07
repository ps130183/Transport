package com.km.transport.utils;


import android.os.Environment;

import com.km.transport.dto.UserDto;
import com.km.transport.dto.UserInfoDto;
import com.km.transport.utils.retrofit.SecretConstant;

import java.io.File;

/**
 * Created by Sunflower on 2016/1/11.
 */
public class Constant {

    public static final UserDto user = new UserDto();
//
    public static UserInfoDto userInfo = null;

    public static boolean isPay = false;

    public static boolean isAllowUserCard = true;

    //获取名片 接口
    public static final String QRCODE_URL = SecretConstant.API_HOST + SecretConstant.API_HOST_PATH + "/user/saoUserCard/info/send?mobilePhone=";

    public static final String COMPRESS_PICTURE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "Transport" + File.separator + "image" + File.separator;


    public static final float[] TRUCK_LENGTHS = {4.2f, 4.5f, 5, 5.2f, 6.2f, 6.8f, 7.2f, 7.6f, 8.2f, 8.6f, 9.6f, 11.7f, 12.5f, 13, 13.5f, 14, 15, 16, 17, 17.5f, 18};
    public static final String[] TRUCK_TYPES = {"六轴仓栏", "六轴自卸", "六轴罐车", "四轴翻斗", "四轴平板"};



}
