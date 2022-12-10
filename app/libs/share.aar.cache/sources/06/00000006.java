package com.xtc.shareapi.share.shareobject;

import android.os.Bundle;
import android.util.Log;
import com.xtc.shareapi.share.communication.BaseResponse;
import com.xtc.shareapi.share.communication.ShowMessageFromXTC;
import com.xtc.shareapi.share.constant.OpenApiConstant;
import com.xtc.shareapi.share.interfaces.IShareObject;

/* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/shareobject/XTCTextObject.class */
public class XTCTextObject implements IShareObject {
    private static final String TAG = OpenApiConstant.TAG + XTCTextObject.class.getSimpleName();
    private String text;
    private static final int LENGTH_LIMIT = 1024;

    public XTCTextObject() {
    }

    public XTCTextObject(String var1) {
        this.text = var1;
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    public void toBundle(Bundle var1) {
        var1.putString(OpenApiConstant.XTCTextConstant.BUNDLE_TEXT, this.text);
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    /* renamed from: fromBundle */
    public XTCTextObject mo16fromBundle(Bundle var1) {
        XTCTextObject xtcTextObject = new XTCTextObject();
        xtcTextObject.text = var1.getString(OpenApiConstant.XTCTextConstant.BUNDLE_TEXT);
        return xtcTextObject;
    }

    @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
    public BaseResponse checkArgs() {
        BaseResponse response = new ShowMessageFromXTC.Response();
        if (this.text != null && this.text.length() != 0 && this.text.length() <= LENGTH_LIMIT) {
            response.setCode(1);
            return response;
        }
        Log.e(TAG, "checkArgs fail, text is invalid");
        response.setCode(6);
        response.setErrorDesc("share_argument_error, text is invalid");
        return response;
    }

    @Override // com.xtc.shareapi.share.interfaces.IShareObject
    public int type() {
        return 1;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return "XTCTextObject{text='" + this.text + "'}";
    }
}