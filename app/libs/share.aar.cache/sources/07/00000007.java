package com.xtc.shareapi.share.shareobject;

import android.os.Bundle;
import android.util.Log;
import com.xtc.shareapi.share.communication.BaseResponse;
import com.xtc.shareapi.share.communication.ShowMessageFromXTC;
import com.xtc.shareapi.share.constant.OpenApiConstant;
import com.xtc.shareapi.share.interfaces.IShareObject;

/* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/shareobject/XTCVideoObject.class */
public class XTCVideoObject implements IShareObject {
    private static final String TAG = OpenApiConstant.TAG + XTCVideoObject.class.getSimpleName();
    private static final int LENGTH_LIMIT = 1024;
    private String videoUrl;
    private String videoLowUrl;
    private String videoHighUrl;
    private String extInfo;
    private String startActivity;
    private String videoName;
    private String author;
    private long duration;

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    public void toBundle(Bundle var1) {
        var1.putString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_DEFAULT_URL, this.videoUrl);
        var1.putString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_HIGH_URL, this.videoHighUrl);
        var1.putString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_LOW_URL, this.videoLowUrl);
        var1.putString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_AUTHOR, this.author);
        var1.putLong(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_DURATION, this.duration);
        var1.putString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_EXTINFO, this.extInfo);
        var1.putString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_NAME, this.videoName);
        var1.putString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_START_ACTIVITY, this.startActivity);
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    /* renamed from: fromBundle */
    public XTCVideoObject mo16fromBundle(Bundle var1) {
        XTCVideoObject xtcVideoObject = new XTCVideoObject();
        xtcVideoObject.setVideoUrl(var1.getString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_DEFAULT_URL));
        xtcVideoObject.setVideoHighUrl(var1.getString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_HIGH_URL));
        xtcVideoObject.setVideoLowUrl(var1.getString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_LOW_URL));
        xtcVideoObject.setAuthor(var1.getString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_AUTHOR));
        xtcVideoObject.setDuration(var1.getLong(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_DURATION));
        xtcVideoObject.setVideoName(var1.getString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_NAME));
        xtcVideoObject.setExtInfo(var1.getString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_EXTINFO));
        xtcVideoObject.setStartActivity(var1.getString(OpenApiConstant.XTCVideoConstant.BUNDLE_VIDEO_START_ACTIVITY));
        return xtcVideoObject;
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    public BaseResponse checkArgs() {
        BaseResponse response = new ShowMessageFromXTC.Response();
        if ((this.videoUrl != null && this.videoUrl.length() != 0) || ((this.videoLowUrl != null && this.videoLowUrl.length() != 0) || (this.videoHighUrl != null && this.videoHighUrl.length() != 0))) {
            if (this.videoUrl != null && this.videoUrl.length() > LENGTH_LIMIT) {
                Log.e(TAG, "checkArgs fail, videoUrl is too long");
                response.setCode(6);
                response.setErrorDesc("share_argument_error,videoUrl is too long");
                return response;
            } else if (this.videoLowUrl != null && this.videoLowUrl.length() > LENGTH_LIMIT) {
                Log.e(TAG, "checkArgs fail, videoLowBandUrl is too long");
                response.setCode(6);
                response.setErrorDesc("share_argument_error, videoLowBandUrl is too long");
                return response;
            } else if (this.videoHighUrl != null && this.videoHighUrl.length() > LENGTH_LIMIT) {
                Log.e(TAG, "checkArgs fail,videoHighBandUrl is too long");
                response.setCode(6);
                response.setErrorDesc("share_argument_error,videoHighBandUrl is too long");
                return response;
            } else if (this.videoName != null && this.videoName.length() > LENGTH_LIMIT) {
                Log.e(TAG, "checkArgs fail, videoName is too long");
                response.setCode(6);
                response.setErrorDesc("share_argument_error,videoName is too long");
                return response;
            } else if (this.author != null && this.author.length() > LENGTH_LIMIT) {
                Log.e(TAG, "checkArgs fail, author name is too long");
                response.setCode(6);
                response.setErrorDesc("share_argument_error,author name is too long");
                return response;
            } else if (this.startActivity != null && this.startActivity.length() > LENGTH_LIMIT) {
                Log.e(TAG, "checkArgs fail, startActivity is too long");
                response.setCode(6);
                response.setErrorDesc("share_argument_error,startActivity is too long");
                return response;
            } else if (this.extInfo != null && this.extInfo.length() > LENGTH_LIMIT) {
                Log.e(TAG, "checkArgs fail, extInfo is too long");
                response.setCode(6);
                response.setErrorDesc("share_argument_error,extInfo is too long");
                return response;
            } else {
                response.setCode(1);
                return response;
            }
        }
        Log.e(TAG, "both arguments are null");
        response.setCode(6);
        response.setErrorDesc("share_argument_error,both arguments are null");
        return response;
    }

    @Override // com.xtc.shareapi.share.interfaces.IShareObject
    public int type() {
        return 4;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoLowUrl() {
        return this.videoLowUrl;
    }

    public void setVideoLowUrl(String videoLowUrl) {
        this.videoLowUrl = videoLowUrl;
    }

    public String getVideoHighUrl() {
        return this.videoHighUrl;
    }

    public void setVideoHighUrl(String videoHighUrl) {
        this.videoHighUrl = videoHighUrl;
    }

    public String getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public String getStartActivity() {
        return this.startActivity;
    }

    public void setStartActivity(String startActivity) {
        this.startActivity = startActivity;
    }

    public String getVideoName() {
        return this.videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String toString() {
        return "XTCVideoObject{videoUrl='" + this.videoUrl + "', videoLowUrl='" + this.videoLowUrl + "', videoHighUrl='" + this.videoHighUrl + "', extInfo='" + this.extInfo + "', startActivity='" + this.startActivity + "', videoName='" + this.videoName + "', author='" + this.author + "', duration=" + this.duration + '}';
    }
}