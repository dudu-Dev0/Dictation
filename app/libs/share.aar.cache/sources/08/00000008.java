package com.xtc.shareapi.share.shareobject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import com.xtc.shareapi.share.communication.BaseResponse;
import com.xtc.shareapi.share.communication.ShowMessageFromXTC;
import com.xtc.shareapi.share.constant.OpenApiConstant;
import com.xtc.shareapi.share.interfaces.IShareObject;
import java.io.ByteArrayOutputStream;
import java.io.File;

/* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/shareobject/XTCImageObject.class */
public class XTCImageObject implements IShareObject {
    private static final String TAG = OpenApiConstant.TAG + XTCImageObject.class.getSimpleName();
    private static final int CONTENT_LENGTH_LIMIT = 307200;
    private static final int PATH_LENGTH_LIMIT = 512;
    private byte[] imageData;
    private String imagePath;

    public XTCImageObject() {
    }

    public XTCImageObject(String imagePath) {
        this.imagePath = imagePath;
    }

    public XTCImageObject(Bitmap var1) {
        try {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            var1.compress(Bitmap.CompressFormat.JPEG, 85, var2);
            this.imageData = var2.toByteArray();
            var2.close();
        } catch (Exception var3) {
            Log.e(TAG, "WXImageObject <init>, exception:" + var3.getMessage());
        }
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    public void toBundle(Bundle var1) {
        var1.putByteArray(OpenApiConstant.XTCImageConstant.BUNDLE_IMAGE_DATA, this.imageData);
        var1.putString(OpenApiConstant.XTCImageConstant.BUNDLE_IMAGE_PATH, this.imagePath);
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    /* renamed from: fromBundle */
    public XTCImageObject mo16fromBundle(Bundle var1) {
        XTCImageObject xtcImageObject = new XTCImageObject();
        xtcImageObject.imageData = var1.getByteArray(OpenApiConstant.XTCImageConstant.BUNDLE_IMAGE_DATA);
        xtcImageObject.imagePath = var1.getString(OpenApiConstant.XTCImageConstant.BUNDLE_IMAGE_PATH);
        return xtcImageObject;
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    public BaseResponse checkArgs() {
        BaseResponse response = new ShowMessageFromXTC.Response();
        if ((this.imageData != null && this.imageData.length != 0) || (this.imagePath != null && this.imagePath.length() != 0)) {
            if (this.imageData != null && this.imageData.length > CONTENT_LENGTH_LIMIT) {
                Log.e(TAG, "checkArgs fail, content is too large");
                response.setCode(6);
                response.setErrorDesc("share_argument_error,content is too large");
                return response;
            } else if (this.imagePath != null && this.imagePath.length() > PATH_LENGTH_LIMIT) {
                Log.e(TAG, "checkArgs fail, path is invalid");
                response.setCode(6);
                response.setErrorDesc("share_argument_error,path is invalid");
                return response;
            } else if (this.imagePath != null && getFileSize(this.imagePath) > PATH_LENGTH_LIMIT) {
                Log.e(TAG, "checkArgs fail, image content is too large");
                response.setCode(6);
                response.setErrorDesc("share_argument_error,image content is too large");
                return response;
            } else {
                response.setCode(1);
                return response;
            }
        }
        Log.e(TAG, "checkArgs fail, all arguments are null");
        response.setCode(6);
        response.setErrorDesc("share_argument_errorall arguments are null");
        return response;
    }

    private int getFileSize(String var1) {
        Log.d(TAG, "image path = " + var1);
        File image = new File(var1);
        if (image.exists() && image.isFile()) {
            return var1.length();
        }
        Log.e(TAG, "image not exist or not file!");
        return Integer.MAX_VALUE;
    }

    @Override // com.xtc.shareapi.share.interfaces.IShareObject
    public int type() {
        return 2;
    }

    public void setBitmap(Bitmap var1) {
        try {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            var1.compress(Bitmap.CompressFormat.JPEG, 85, var2);
            this.imageData = var2.toByteArray();
            var2.close();
        } catch (Exception var3) {
            Log.e(TAG, "WXImageObject <init>, exception:" + var3.getMessage());
        }
    }

    public byte[] getImageData() {
        return this.imageData;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String toString() {
        return "XTCImageObject{imagePath='" + this.imagePath + "'}";
    }
}