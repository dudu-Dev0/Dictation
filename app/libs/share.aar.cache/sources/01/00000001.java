package com.xtc.shareapi.share.shareobject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.xtc.shareapi.share.communication.BaseResponse;
import com.xtc.shareapi.share.communication.ShowMessageFromXTC;
import com.xtc.shareapi.share.constant.OpenApiConstant;
import com.xtc.shareapi.share.interfaces.IShareObject;
import java.io.ByteArrayOutputStream;

/* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/shareobject/XTCShareMessage.class */
public class XTCShareMessage implements IShareObject {
    private static final String TAG = OpenApiConstant.TAG + XTCShareMessage.class.getSimpleName();
    private static final int MINI_PROGRAM_THUMB_LENGTH = 131072;
    private static final int TITLE_LENGTH_LIMIT = 512;
    private static final int DESCRIPTION_LENGTH_LIMIT = 1024;
    private static final int MESSAGE_ACTION_LENGTH_LIMIT = 2048;
    private static final int MESSAGE_EXT_LENGTH_LIMIT = 2048;
    private String description;
    private byte[] thumbData;
    private IShareObject shareObject;
    private String ext;
    private int actionType;
    private String action;

    public XTCShareMessage() {
    }

    public XTCShareMessage(IShareObject shareObject) {
        this.shareObject = shareObject;
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    public void toBundle(Bundle var1) {
        Builder.toBundle(this);
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    /* renamed from: fromBundle */
    public XTCShareMessage mo16fromBundle(Bundle var1) {
        return Builder.fromBundle(var1);
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    public BaseResponse checkArgs() {
        BaseResponse response = new ShowMessageFromXTC.Response();
        if (this.description != null && this.description.length() > DESCRIPTION_LENGTH_LIMIT) {
            Log.e(TAG, "checkArgs fail, description is invalid");
            response.setCode(6);
            response.setErrorDesc("checkArgs fail, description is invalid");
            return response;
        } else if (this.shareObject == null) {
            Log.e(TAG, "checkArgs fail, shareObject is null");
            response.setCode(6);
            response.setErrorDesc("checkArgs fail, shareObject is null");
            return response;
        } else if (this.action != null && this.action.length() > 2048) {
            Log.e(TAG, "checkArgs fail, messageAction is too long");
            response.setCode(6);
            response.setErrorDesc("checkArgs fail, messageAction is too long");
            return response;
        } else if (this.ext != null && this.ext.length() > 2048) {
            Log.e(TAG, "checkArgs fail, messageExt is too long");
            response.setCode(6);
            response.setErrorDesc("checkArgs fail, messageExt is too long");
            return response;
        } else {
            return this.shareObject.checkArgs();
        }
    }

    @Override // com.xtc.shareapi.share.interfaces.IShareObject
    public int type() {
        return 100;
    }

    public final int getType() {
        if (this.shareObject == null) {
            return 0;
        }
        return this.shareObject.type();
    }

    public final void setThumbImage(Bitmap var1) {
        try {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            var1.compress(Bitmap.CompressFormat.JPEG, 85, var2);
            this.thumbData = var2.toByteArray();
            var2.close();
        } catch (Exception var3) {
            Log.e(TAG, "bitmapToByteArray exception:" + var3.getMessage());
        }
    }

    public static String getTAG() {
        return TAG;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getThumbData() {
        return this.thumbData;
    }

    public int getActionType() {
        return this.actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public IShareObject getShareObject() {
        return this.shareObject;
    }

    public void setShareObject(IShareObject shareObject) {
        this.shareObject = shareObject;
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/shareobject/XTCShareMessage$Builder.class */
    public static class Builder {
        public static Bundle toBundle(XTCShareMessage shareMessage) {
            Bundle var1 = new Bundle();
            var1.putString(OpenApiConstant.XTCShareMessageConstant.BUNDLE_MESSAGE_EXT, shareMessage.ext);
            var1.putString(OpenApiConstant.XTCShareMessageConstant.BUNDLE_MESSAGE_DESC, shareMessage.description);
            var1.putByteArray(OpenApiConstant.XTCShareMessageConstant.BUNDLE_MESSAGE_THUMB, shareMessage.thumbData);
            var1.putString(OpenApiConstant.XTCShareMessageConstant.BUNDLE_MESSAGE_ACTION, shareMessage.action);
            var1.putInt(OpenApiConstant.XTCShareMessageConstant.BUNDLE_MESSAGE_ACTION_TYPE, shareMessage.actionType);
            if (shareMessage.shareObject != null) {
                Log.d(XTCShareMessage.TAG, "shareObject className = " + shareMessage.getShareObject().getClass().getName());
                var1.putString(OpenApiConstant.BuilderConstant.KEY_IDENTIFIER, shareMessage.getShareObject().getClass().getName());
                shareMessage.shareObject.toBundle(var1);
            }
            return var1;
        }

        public static XTCShareMessage fromBundle(Bundle bundle) {
            XTCShareMessage message = new XTCShareMessage();
            message.action = bundle.getString(OpenApiConstant.XTCShareMessageConstant.BUNDLE_MESSAGE_ACTION);
            message.ext = bundle.getString(OpenApiConstant.XTCShareMessageConstant.BUNDLE_MESSAGE_EXT);
            message.description = bundle.getString(OpenApiConstant.XTCShareMessageConstant.BUNDLE_MESSAGE_DESC);
            message.thumbData = bundle.getByteArray(OpenApiConstant.XTCShareMessageConstant.BUNDLE_MESSAGE_THUMB);
            message.actionType = bundle.getInt(OpenApiConstant.XTCShareMessageConstant.BUNDLE_MESSAGE_ACTION_TYPE);
            String objectName = bundle.getString(OpenApiConstant.BuilderConstant.KEY_IDENTIFIER);
            Log.d(XTCShareMessage.TAG, "shareObject className = " + objectName);
            if (!TextUtils.isEmpty(objectName)) {
                try {
                    Class var3 = Class.forName(objectName);
                    message.setShareObject((IShareObject) var3.newInstance());
                    message.setShareObject((IShareObject) message.getShareObject().fromBundle(bundle));
                    return message;
                } catch (Exception var4) {
                    Log.e(XTCShareMessage.TAG, "error = " + var4.getMessage());
                    return message;
                }
            }
            return message;
        }
    }

    public String toString() {
        return "XTCShareMessage{description='" + this.description + "', actionType=" + this.actionType + ", action='" + this.action + "', ext='" + this.ext + "', shareObject=" + this.shareObject + '}';
    }
}