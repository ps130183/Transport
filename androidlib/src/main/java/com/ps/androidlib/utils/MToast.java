package com.ps.androidlib.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.MainThread;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/17.
 */

public class MToast {

    public static void showToast(Context mContext,String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }

   public static void showSnackBar(View view, String info){
       Snackbar.make(view,info,Snackbar.LENGTH_SHORT)
               .show();
   }

    public static void showSnackBar(Activity activity, String info){
        showSnackBar(activity.getWindow().getDecorView(),info);
    }

}
