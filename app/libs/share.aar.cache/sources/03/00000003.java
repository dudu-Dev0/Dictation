package com.xtc.shareapi.share.manager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.xtc.shareapi.share.bean.AppInfo;
import com.xtc.shareapi.share.bean.ModuleSwitch;
import com.xtc.shareapi.share.communication.SendMessageToXTC;
import com.xtc.shareapi.share.constant.OpenApiConstant;
import com.xtc.shareapi.share.interfaces.Scene;
import com.xtc.shareapi.share.utils.BitmapUtil;
import com.xtc.shareapi.share.utils.ShareUtil;

/* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/manager/ShareStrategy.class */
public abstract class ShareStrategy {
    public final String TAG = OpenApiConstant.TAG + getClass().getSimpleName();
    public String appName;
    public byte[] appIcon;
    public Context context;

    public abstract void share(SendMessageToXTC.Request request, String str);

    public abstract boolean setClassName(Intent intent, Scene scene);

    public abstract ModuleSwitch getModuleSwitch(Context context, int i);

    public abstract boolean checkBaseVersion(int i);

    public abstract String getTargetClassName(int i);

    public abstract AppInfo getTokenFromXTC(Context context, Scene scene);

    public ShareStrategy(Context context, String appName, byte[] appIcon) {
        this.context = context;
        this.appName = appName;
        this.appIcon = appIcon;
    }

    public Intent getShareIntentFromRequest(String appKey, SendMessageToXTC.Request request) {
        String appName = this.appName;
        if (TextUtils.isEmpty(appName)) {
            appName = ShareUtil.getAppName(this.context);
        }
        byte[] bitmapData = this.appIcon;
        if (bitmapData == null || bitmapData.length <= 0) {
            Bitmap bmp = ShareUtil.getBitmap(this.context);
            bitmapData = BitmapUtil.bitmapToByteArray(bmp);
        }
        if (bitmapData == null) {
            Log.e(this.TAG, "app icon is null!");
            return null;
        }
        Log.d(this.TAG, "app icon size is = " + bitmapData.length);
        if (bitmapData.length > 300) {
            Log.e(this.TAG, "app icon data too large!");
            return null;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        request.toBundle(bundle);
        Scene scene = request.getScene();
        if (scene != null && !setClassName(intent, scene)) {
            Log.e(this.TAG, "set class name error!");
            return null;
        }
        intent.putExtras(bundle);
        intent.putExtra(OpenApiConstant.IntentConstant.INTENT_VERSION, OpenApiConstant.SdkVersionCode.SDK_VERSION_CODE);
        intent.putExtra(OpenApiConstant.IntentConstant.INTENT_PACKAGE, this.context.getPackageName());
        intent.putExtra(OpenApiConstant.IntentConstant.INTENT_CLASSNAME, this.context.getClass().getName());
        intent.putExtra(OpenApiConstant.IntentConstant.INTENT_APP_NAME, appName);
        intent.putExtra(OpenApiConstant.IntentConstant.INTENT_APP_ICON, bitmapData);
        intent.putExtra(OpenApiConstant.IntentConstant.INTENT_APP_KEY, appKey);
        return intent;
    }

    public void setAppInfo(AppInfo appInfo, Cursor cursor) {
        int sharePackageIndex = cursor.getColumnIndex(OpenApiConstant.TokenConstant.SHARE_APP_PACKAGE);
        int shareAllowIndex = cursor.getColumnIndex(OpenApiConstant.TokenConstant.SHARE_APP_ALLOW);
        int shareTokenIndex = cursor.getColumnIndex(OpenApiConstant.TokenConstant.SHARE_APP_TOKEN);
        appInfo.setPackageName(cursor.getString(sharePackageIndex));
        appInfo.setAllow(cursor.getInt(shareAllowIndex));
        appInfo.setToken(cursor.getString(shareTokenIndex));
    }
}