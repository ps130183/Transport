package com.km.transport.utils;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by kamangkeji on 17/2/20.
 */

public class CustomGsonConverterFactory extends Converter.Factory {

    private Gson gson;

    public static CustomGsonConverterFactory create(){
        return create(new Gson());
    }

    public static CustomGsonConverterFactory create(Gson gson){
        return new CustomGsonConverterFactory(gson);
    }

    public CustomGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
