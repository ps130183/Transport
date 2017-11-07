package com.ps.androidlib.utils.imageselector;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ps.androidlib.R;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

/**
 * Created by kamangkeji on 17/3/28.
 */

public class GlideLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {
        Glide.with(context)
                .load(path)
                .placeholder(R.mipmap.gallery_pick_photo)
                .centerCrop()
                .into(galleryImageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
