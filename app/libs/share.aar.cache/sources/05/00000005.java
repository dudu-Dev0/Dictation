package com.xtc.shareapi.share.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.xtc.shareapi.share.constant.OpenApiConstant;

/* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/utils/ShareUtil.class */
public class ShareUtil {
    private static final String TAG = OpenApiConstant.TAG + ShareUtil.class.getSimpleName();

    public static int getHostSdkVersion(Context context, String packageName) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(OpenApiConstant.App.LAUNCHER, 128);
            int hostSdkVersion = appInfo.metaData.getInt(OpenApiConstant.App.META_DATA_VERSION);
            Log.d(TAG, "get host sdk version = " + hostSdkVersion);
            return hostSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "get host sdk version error = " + e.getMessage());
            return -1;
        }
    }

    public static String getTargetClassName(Context context, String targetPackageName, String targetKey) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(targetPackageName, 128);
            String msg = appInfo.metaData.getString(targetKey);
            Log.d(TAG, "get meta info " + msg);
            return msg;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void startTargetActivity(Context context, int errorCode, String errorDesc) {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName(context.getPackageName(), context.getClass().getName());
        intent.putExtra(OpenApiConstant.ResponseConstant.BUNDLE_ERROR_CODE, errorCode);
        intent.putExtra(OpenApiConstant.ResponseConstant.BUNDLE_ERROR_DESC, errorDesc);
        intent.setComponent(cn);
        context.startActivity(intent);
    }

    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "get app name error = " + e);
            return null;
        }
    }

    public static synchronized Bitmap getBitmap(Context context) {
        try {
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            Drawable d = packageManager.getApplicationIcon(applicationInfo);
            BitmapDrawable bd = (BitmapDrawable) d;
            return BitmapUtil.scaleIcon(context, bd.getBitmap());
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "get app icon error = " + e);
            return null;
        }
    }

    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || (networkInfo = connectivityManager.getActiveNetworkInfo()) == null || !networkInfo.isConnected()) ? false : true;
    }
}