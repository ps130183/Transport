package com.km.transport.utils.retrofit;

import android.graphics.Bitmap;

import com.km.transport.api.ApiService;
import com.km.transport.basic.BaseApplication;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.interceptor.CacheInterceptor;
import com.ps.androidlib.interceptor.HttpLoggingInterceptor;
import com.ps.androidlib.utils.ClippingPicture;
import com.ps.androidlib.utils.ImageUtils;

import org.reactivestreams.Publisher;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Sunflower on 2015/11/4.
 */
public class RetrofitUtil {

    /**
     * 服务器地址
     */
    private static final String API_HOST = SecretConstant.API_HOST;

    private static ApiService service;
    private static Retrofit retrofit;

    private static BasicParams params = new BasicParams();

    /**
     * 缓存大小 10M
     */
    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    public static ApiService getService() {
        if (service == null) {
            service = getRetrofit().create(ApiService.class);
        }
        return service;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            //公共参数
//             BasicParamsInterceptor basicParamsInterceptor =
//                    new BasicParamsInterceptor.Builder()
//                            .addQueryParam("token", params.token)
//                            .build();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Logger.d(message);
                }
            });
            //网络缓存路径文件
             File httpCacheDirectory = new File(BaseApplication.getInstance().getExternalCacheDir(), "HttpResponseCache");
            //通过拦截器设置缓存，暂未实现
            CacheInterceptor cacheInterceptor = new CacheInterceptor(BaseApplication.getInstance());

            OkHttpClient client = new OkHttpClient.Builder()
                    //log请求参数
                    .addInterceptor(interceptor)//日志拦截器
//                    .addInterceptor(basicParamsInterceptor)//公共请求参数
                    //设置缓存
                    .cache(new Cache(httpCacheDirectory, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE))
                    .addInterceptor(cacheInterceptor) //缓存拦截器
//                    .addNetworkInterceptor(cacheInterceptor)//网络拦截器

                    .retryOnConnectionFailure(true)//错误重连
                    .connectTimeout(15, TimeUnit.SECONDS)//连接超时 15秒
                    //网络拦截器，可以用于重试或重写
//                    .addNetworkInterceptor(authorizationInterceptor)
                    //网络请求缓存，未实现
                    .build();
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(client)
                    .baseUrl(API_HOST)
                    .build();
//            ScalarsConverterFactory.create();

        }
        return retrofit;
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Flowable<T> flatResponse(final Response<T> response) {
        Logger.d(response.toString());
        return Flowable.create(new FlowableOnSubscribe<T>() {

            @Override
            public void subscribe(FlowableEmitter<T> e) throws Exception {
                long request = e.requested();
                if (response.isSuccess()) {
                    if (!e.isCancelled() && request > 0) {
//                        if (response.result == null){
//                            e.onError(new APIException("0001","isnull"));
////                            e.onNext((T) "");
//                        } else {
                            e.onNext(response.result);
//                        }
                    }
                } else {
                    if (!e.isCancelled()) {
                        e.onError(new APIException(response.status, response.message));
                    }
                }
                if (!e.isCancelled()) {
                    e.onComplete();
                }
            }
        }, BackpressureStrategy.ERROR);
    }

    /**
     * 自定义异常，当接口返回的{@link Response#status}不为{@link com.km.transport.utils.retrofit.RetCode#SUCCESS}时，需要跑出此异常
     * eg：登陆时验证码错误；参数为传递等
     */
    public static class APIException extends Throwable {
        public String code;
        public String message;

        public APIException(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }


    /**
     * http://www.jianshu.com/p/e9e03194199e
     * <p/>
     * Transformer实际上就是一个Func1<Observable<T>, Observable<R>>，
     * 换言之就是：可以通过它将一种类型的Observable转换成另一种类型的Observable，
     * 和调用一系列的内联操作符是一模一样的。
     *
     * @param <T>
     * @return
     */
    protected <T> FlowableTransformer<Response<T>, T> applySchedulers() {
        return (FlowableTransformer<Response<T>, T>) transformer;
    }

    final FlowableTransformer transformer = new FlowableTransformer() {
        @Override
        public Publisher apply(Flowable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Function() {
                        @Override
                        public Object apply(@NonNull Object o) throws Exception {
                            return flatResponse((Response<? extends Object>) o);
                        }
                    });
        }
    };




    /**
     * 当{@link ApiService}中接口的注解为{@link retrofit2.http.Multipart}时，参数为{@link RequestBody}
     * 生成对应的RequestBody
     *
     * @param param
     * @return
     */
    public RequestBody createRequestBody(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public RequestBody createRequestBody(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    public RequestBody createRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    public RequestBody createRequestBody(File param) {
        return RequestBody.create(MediaType.parse("image/*"), param);
    }

    /**
     * 已二进制传递图片文件，对图片文件进行了压缩
     *
     * @param path 文件路径
     * @return
     */
    public RequestBody createPictureRequestBody(String path) {
//        Bitmap bitmap = ClippingPicture.decodeResizeBitmapSd(path, 400, 800);
        Bitmap bitmap = ImageUtils.compressByQuality(ImageUtils.getBitmap(path),(long) (1024*1024),true);
//        Bitmap bitmap = ClippingPicture.decodeBitmapSd(path);
        return RequestBody.create(MediaType.parse("image/*"), ClippingPicture.bitmapToBytes(bitmap));
    }

    public static class BasicParams{
        private String token = "";

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    /**
     * 设置公共参数
     * @param token
     */
    public static void setBasicParams(String token){
        params.token = token;
        service = null;
        retrofit = null;
    }


}
