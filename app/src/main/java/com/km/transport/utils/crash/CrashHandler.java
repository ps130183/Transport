package com.km.transport.utils.crash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.km.transport.api.ApiWrapper;
import com.km.transport.module.MainActivity;
import com.orhanobut.logger.Logger;
import com.ps.androidlib.utils.DialogUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by kamangkeji on 17/8/28.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;

    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "RMBank" + File.separator + "app" + File.separator + "CrashHandler" + File.separator + "log" + File.separator;
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".log";

    private static CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }


    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        if (!handleException(ex) && mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }

//            Intent intent = new Intent(mContext, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            mContext.startActivity(intent);
            MobclickAgent.onKillProcess(mContext);
            Process.killProcess(Process.myPid());
        }

    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //导出异常信息到SD卡中
//            dumpExceptionToSDCard(ex);
        //在这里可以将异常信息上传到服务器
        uploadCrashExceptionToService(ex);

        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        return true;
    }

    /**
     * 上传异常信息到服务器
     * @param ex
     */
    private void uploadCrashExceptionToService(Throwable ex) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = null;
            pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

            String appVersion = pi.versionName + "_" + pi.versionCode;
            String osVersion = Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT;
            String vendor = Build.MANUFACTURER;
            String model = Build.MODEL;
            String cpuAbi = Build.CPU_ABI;

//            ApiWrapper.getInstance().uploadAppCrashQuestion(appVersion, osVersion, vendor, model, cpuAbi, getExceptionAllInfo(ex))
//                    .subscribe(new Consumer<String>() {
//                        @Override
//                        public void accept(@NonNull String s) throws Exception {
//                            Logger.d(TAG, "crash 信息已上传到服务器");
//                        }
//                    });

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取异常的详细信息
     * @param e
     * @return
     */
    private String getExceptionAllInfo(Throwable e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }

    /**
     * 将crash信息导入到SD卡中
     *
     * @param ex
     * @throws IOException
     */
    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        //如果SD卡不存在 或 无法使用，则无法把异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Logger.d(TAG, "SD 卡不能存在或无法使用，跳过保存异常信息");
                return;
            }
        }

        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(current));
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);

        try {
            PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
        } catch (Exception e) {
            Logger.e(TAG, "dump crash info failed,将crash信息导入SD卡失败");
        }
    }

    /**
     * 将设备信息 写入 SD卡中
     *
     * @param pw
     * @throws PackageManager.NameNotFoundException
     */
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

        //app版本号
        pw.print("APP Version: ");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);


        //系统版本
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);

        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);

        //CPU 架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }


}
