package com.ps.androidlib.interceptor;

import android.content.Context;


import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.ps.androidlib.utils.AppUtils.isNetworkReachable;


/**
 * Created by Sunflower on 2016/1/19.
 */
public class CacheInterceptor implements Interceptor {
    private Context mContext;

    public CacheInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!isNetworkReachable(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (isNetworkReachable(mContext)) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
//            Logger.i("has network ,cacheControl=" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            Logger.i("network error ,maxStale="+maxStale);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale="+maxStale)
                    .build();
        }

    }
}
