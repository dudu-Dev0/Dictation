package com.xtc.shareapi.share.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.xtc.shareapi.share.communication.BaseRequest;
import com.xtc.shareapi.share.communication.BaseResponse;
import com.xtc.shareapi.share.communication.SendMessageToXTC;
import com.xtc.shareapi.share.constant.OpenApiConstant;
import com.xtc.shareapi.share.utils.BitmapUtil;
import com.xtc.shareapi.share.utils.ShareUtil;
import com.xtc.shareapi.share.view.PopWindowManager;
import java.util.UUID;

/* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/manager/ShareMessageManager.class */
public class ShareMessageManager {
    private static final String TAG = OpenApiConstant.TAG + ShareMessageManager.class.getSimpleName();
    private Context context;
    private byte[] appIcon;
    private String appName;

    public ShareMessageManager(Context context) {
        this.context = context;
    }

    public void sendRequestToXTC(BaseRequest baseRequest, String appKey) {
        if (this.context == null) {
            Log.e(TAG, "share to xtc the context is null!");
        } else if (baseRequest == null) {
            Log.d(TAG, "baseRequest must not null!");
        } else if (!(baseRequest instanceof SendMessageToXTC.Request)) {
            Log.d(TAG, "baseRequest must instanceof SendMessageToXTC.Request!");
        } else if (!(this.context instanceof Activity) || TextUtils.isEmpty(this.context.getPackageName()) || TextUtils.isEmpty(this.context.getClass().getName())) {
            Log.e(TAG, "you should use an activity context here!");
        } else if (!ShareUtil.isConnected(this.context)) {
            Log.d(TAG, "network is not connected");
            ShareUtil.startTargetActivity(this.context, 8, BaseResponse.Desc.NETWORK_ERROR);
        } else {
            BaseResponse response = baseRequest.checkArgs();
            if (response == null || response.getCode() != 1) {
                Log.d(TAG, "the argument request check fail");
                ShareUtil.startTargetActivity(this.context, 6, "the argument request check fail");
                return;
            }
            baseRequest.setTransaction(String.valueOf(System.currentTimeMillis()) + UUID.randomUUID());
            int sdkVersion = ShareUtil.getHostSdkVersion(this.context, OpenApiConstant.App.LAUNCHER);
            if (sdkVersion == 0) {
                Log.d(TAG, "check sdk version fail");
                ShareUtil.startTargetActivity(this.context, 5, "current host not support share!");
            } else if (sdkVersion == 1) {
                StrategyVersionOne strategyVersionOne = new StrategyVersionOne(this.context, this.appName, this.appIcon);
                strategyVersionOne.share((SendMessageToXTC.Request) baseRequest, appKey);
            } else {
                StrategyVersionTwo strategyVersionTwo = new StrategyVersionTwo(this.context, this.appName, this.appIcon);
                strategyVersionTwo.share((SendMessageToXTC.Request) baseRequest, appKey);
            }
        }
    }

    public void sendRequestToXTC(BaseRequest chat, BaseRequest moment, String appKey) {
        if (this.context == null) {
            Log.e(TAG, "share to xtc the context is null!");
        } else if (chat == null || moment == null) {
            Log.d(TAG, "baseRequest must not null!");
        } else if (!(chat instanceof SendMessageToXTC.Request) || !(moment instanceof SendMessageToXTC.Request)) {
            Log.d(TAG, "baseRequest must instanceof SendMessageToXTC.Request!");
        } else if (!(this.context instanceof Activity) || TextUtils.isEmpty(this.context.getPackageName()) || TextUtils.isEmpty(this.context.getClass().getName())) {
            Log.e(TAG, "you should use an activity context here!");
        } else if (!ShareUtil.isConnected(this.context)) {
            Log.d(TAG, "network is not connected");
            ShareUtil.startTargetActivity(this.context, 8, BaseResponse.Desc.NETWORK_ERROR);
        } else {
            BaseResponse chatCheckResult = chat.checkArgs();
            BaseResponse momentCheckResult = moment.checkArgs();
            if (chatCheckResult == null || chatCheckResult.getCode() != 1 || momentCheckResult == null || momentCheckResult.getCode() != 1) {
                Log.d(TAG, "the argument request check fail");
                ShareUtil.startTargetActivity(this.context, 6, "the argument request check fail");
                return;
            }
            chat.setTransaction(String.valueOf(System.currentTimeMillis()) + UUID.randomUUID());
            moment.setTransaction(String.valueOf(System.currentTimeMillis()) + UUID.randomUUID());
            int sdkVersion = ShareUtil.getHostSdkVersion(this.context, OpenApiConstant.App.LAUNCHER);
            if (sdkVersion == 0) {
                Log.d(TAG, "check sdk version fail");
                ShareUtil.startTargetActivity(this.context, 5, "current host not support share!");
            } else if (sdkVersion == 1) {
                StrategyVersionOne strategyVersionOne = new StrategyVersionOne(this.context, this.appName, this.appIcon);
                PopWindowManager.getInstance(this.context).showChooseSceneWindow(appKey, (SendMessageToXTC.Request) chat, (SendMessageToXTC.Request) moment, strategyVersionOne);
            } else {
                StrategyVersionTwo strategyVersionTwo = new StrategyVersionTwo(this.context, this.appName, this.appIcon);
                PopWindowManager.getInstance(this.context).showChooseSceneWindow(appKey, (SendMessageToXTC.Request) chat, (SendMessageToXTC.Request) moment, strategyVersionTwo);
            }
        }
    }

    public void sendRequestToXTC(BaseRequest baseRequest, String appKey, int accountType, String accountId, IShareCallback shareCallback) throws RemoteException {
        if (this.context == null || baseRequest == null || TextUtils.isEmpty(appKey)) {
            shareCallback.onResult(6, "context and baseRequest and appKey add accountId must not null!");
        } else if (!(baseRequest instanceof SendMessageToXTC.Request)) {
            shareCallback.onResult(6, "baseRequest argument must instanceof SendMessageToXTC.Request!");
        } else if (!ShareUtil.isConnected(this.context)) {
            shareCallback.onResult(8, "network not connect!");
        } else {
            BaseResponse response = baseRequest.checkArgs();
            if (response == null || response.getCode() != 1) {
                shareCallback.onResult(6, "argument check error!");
                return;
            }
            int sdkVersion = ShareUtil.getHostSdkVersion(this.context, OpenApiConstant.App.LAUNCHER);
            if (sdkVersion <= 1) {
                Log.d(TAG, "check sdk version fail");
                shareCallback.onResult(6, "current host not support!");
                return;
            }
            baseRequest.setTransaction(String.valueOf(System.currentTimeMillis()) + UUID.randomUUID());
            StrategyVersionTwo strategyVersionTwo = new StrategyVersionTwo(this.context, this.appName, this.appIcon);
            strategyVersionTwo.silentlyShare((SendMessageToXTC.Request) baseRequest, accountType, accountId, shareCallback, appKey);
        }
    }

    public boolean checkBaseVersion(int type) {
        int sdkVersion = ShareUtil.getHostSdkVersion(this.context, OpenApiConstant.App.LAUNCHER);
        if (sdkVersion == 0) {
            Log.d(TAG, "check sdk version fail");
            return false;
        } else if (sdkVersion == 1) {
            StrategyVersionOne strategyVersionOne = new StrategyVersionOne(this.context, this.appName, this.appIcon);
            return strategyVersionOne.checkBaseVersion(type);
        } else {
            StrategyVersionTwo strategyVersionTwo = new StrategyVersionTwo(this.context, this.appName, this.appIcon);
            return strategyVersionTwo.checkBaseVersion(type);
        }
    }

    public void setAppIcon(Bitmap icon) {
        Bitmap bitmap = BitmapUtil.scaleIcon(this.context, icon);
        this.appIcon = BitmapUtil.bitmapToByteArray(bitmap);
    }

    public void setAppName(String name) {
        this.appName = name;
    }
}