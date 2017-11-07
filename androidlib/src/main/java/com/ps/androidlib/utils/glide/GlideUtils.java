package com.ps.androidlib.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.nineoldandroids.animation.ObjectAnimator;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.R;
import com.yancy.gallerypick.utils.ScreenUtils;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class GlideUtils {
    /**
     * Glide特点
     * 使用简单
     * 可配置度高，自适应程度高
     * 支持常见图片格式 Jpg png gif webp
     * 支持多种数据源  网络、本地、资源、Assets 等
     * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
     * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
     * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
     * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
     */


    public static void loadImage(ImageView imageView, int imageRes) {
        Glide.with(imageView.getContext())
                .load(imageRes)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImageBlur(ImageView imageView, int imageRes) {
        Glide.with(imageView.getContext())
                .load(imageRes)
                .centerCrop()
                .bitmapTransform(new BlurTransformation(imageView.getContext(), 1, 1)) // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into(imageView);
    }

    public static void loadImage(final ImageView imageView, String imagePath) {
        if (imageView == null) {
            Logger.e("imageview is null");
            return;
        }
        final ObjectAnimator anim = ObjectAnimator.ofInt(imageView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        Glide.with(imageView.getContext())
                .load(imagePath)
                .placeholder(R.drawable.glide_placeholder_rotate)
                .error(R.drawable.ic_load_fail)
                .crossFade(1000)
//                .thumbnail(0.1f)
//                .into(new MyBitmapImageViewTarget(imageView));
                .into(imageView);
    }

    /**
     * 根据imageView的宽度等比缩放图片的高度
     *
     * @param imageView
     * @param imagePath
     */
    public static void loadImageByFitWidth(final ImageView imageView, final String imagePath) {
        if (imageView == null) {
            Logger.e("imageview is null");
            return;
        }
        final ObjectAnimator anim = ObjectAnimator.ofInt(imageView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        Glide.with(imageView.getContext())
                .load(imagePath)
                .asBitmap()
                .placeholder(R.drawable.glide_placeholder_rotate)
                .error(R.drawable.ic_load_fail)
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();
                        int height = ScreenUtils.getScreenWidth(imageView.getContext()) * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = imageView.getLayoutParams();
                        para.height = height;
                        para.width = ScreenUtils.getScreenWidth(imageView.getContext());
                        imageView.setImageBitmap(resource);

                    }
                });
    }

    public static void loadImageCenterCrop(ImageView imageView, String imagePath) {
        if (imageView == null) {
            Logger.e("imageview is null");
            return;
        }
        final ObjectAnimator anim = ObjectAnimator.ofInt(imageView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        Glide.with(imageView.getContext())
                .load(imagePath)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.glide_placeholder_rotate)
                .error(R.drawable.ic_load_fail)
//                .crossFade(1000)
                .dontAnimate()
                .into(new MyBitmapImageViewTarget(imageView));
//                .into(imageView);
    }

    public static void loadCircleImage(final ImageView imageView, String imagePath) {
        if (imageView == null) {
            Logger.e("imageview is null");
            return;
        }
        final Context context = imageView.getContext();
        final ObjectAnimator anim = ObjectAnimator.ofInt(imageView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();

        Glide.with(imageView.getContext())
                .load(imagePath)
                .asBitmap()
                .placeholder(R.drawable.glide_placeholder_rotate)
                .error(R.drawable.ic_home_default_protrait)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }

}
