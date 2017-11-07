package com.ps.androidlib.utils;

import android.content.Context;
import android.widget.ImageView;

import com.ps.androidlib.utils.glide.GlideUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class BannerUtils {


    public static void initBannerFromRes(Banner banner, List<Integer> images, OnBannerListener bannerListener){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(bannerListener);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public static void initBannerFromUrl(Banner banner, List<String> images, OnBannerListener bannerListener){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(bannerListener);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             常用的图片加载库：
             Universal Image Loader：一个强大的图片加载库，包含各种各样的配置，最老牌，使用也最广泛。
             Picasso: Square出品，必属精品。和OkHttp搭配起来更配呦！
             Volley ImageLoader：Google官方出品，可惜不能加载本地图片~
             Fresco：Facebook出的，天生骄傲！不是一般的强大。
             Glide：Google推荐的图片加载库，专注于流畅的滚动。
             */

            if (path instanceof String){
                //Glide 加载图片简单用法
                GlideUtils.loadImage(imageView, (String) path);
            } else {
                GlideUtils.loadImage(imageView, (Integer) path);
            }


            //Picasso 加载图片简单用法
//            Picasso.with(context).load(path).into(imageView)

            //用fresco加载图片简单用法
//            Uri uri = Uri.parse((String) path);
//            imageView.setImageURI(uri);
        }
        //提供createImageView 方法，如果不用可以不重写这个方法，方便fresco自定义ImageView
//        @Override
//        public ImageView createImageView(Context context) {
////            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
//            return null;
//        }
    }
}
