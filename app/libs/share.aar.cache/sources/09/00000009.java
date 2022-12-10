package com.xtc.shareapi.share.communication;

import android.os.Bundle;
import android.util.Log;
import com.xtc.shareapi.share.communication.ShowMessageFromXTC;
import com.xtc.shareapi.share.constant.OpenApiConstant;
import com.xtc.shareapi.share.interfaces.Scene;
import com.xtc.shareapi.share.shareobject.XTCShareMessage;
import com.xtc.shareapi.share.sharescene.Chat;
import com.xtc.shareapi.share.sharescene.Moment;

/* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/communication/SendMessageToXTC.class */
public class SendMessageToXTC {

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/communication/SendMessageToXTC$Request.class */
    public static class Request extends BaseRequest {
        private XTCShareMessage message;
        private Scene scene;

        @Override // com.xtc.shareapi.share.communication.BaseRequest, com.xtc.shareapi.share.interfaces.IBundleSerialize
        public void toBundle(Bundle var1) {
            super.toBundle(var1);
            var1.putAll(XTCShareMessage.Builder.toBundle(this.message));
            if (null != this.scene) {
                this.scene.toBundle(var1);
            }
            var1.putInt("_xtc_api_share_type", this.message.getType());
        }

        @Override // com.xtc.shareapi.share.communication.BaseRequest, com.xtc.shareapi.share.interfaces.IBundleSerialize
        /* renamed from: fromBundle */
        public Request mo16fromBundle(Bundle var1) {
            super.mo16fromBundle(var1);
            Log.d(OpenApiConstant.TAG, "come to fromBundle1");
            int type = var1.getInt(OpenApiConstant.SceneConstant.BUNDLE_SCENE_SHARE_TYPE);
            Log.d(OpenApiConstant.TAG, "come to fromBundle2" + type);
            if (type == 1) {
                Chat chat = (Chat) new Chat().mo16fromBundle(var1);
                Log.d(OpenApiConstant.TAG, "come to fromBundle" + chat.toString() + ",var1 = " + var1.toString());
                setScene(chat);
            } else if (type == 2) {
                Moment moment = new Moment();
                setScene((Moment) moment.mo16fromBundle(var1));
            }
            this.message = XTCShareMessage.Builder.fromBundle(var1);
            this.message.setActionType(var1.getInt("_xtc_api_share_type"));
            Log.d(OpenApiConstant.TAG, "come to fromBundle3 " + this.message);
            return this;
        }

        @Override // com.xtc.shareapi.share.communication.BaseRequest, com.xtc.shareapi.share.interfaces.IBundleSerialize
        public BaseResponse checkArgs() {
            if (this.message == null) {
                Log.d(OpenApiConstant.TAG, "checkArgs fail ,message is null");
                BaseResponse response = new ShowMessageFromXTC.Response();
                response.setCode(6);
                response.setErrorDesc("XTCShareMessage is null");
                return response;
            }
            return this.message.checkArgs();
        }

        @Override // com.xtc.shareapi.share.communication.BaseRequest
        public int getType() {
            return 2;
        }

        public XTCShareMessage getMessage() {
            return this.message;
        }

        public void setMessage(XTCShareMessage message) {
            this.message = message;
        }

        public Scene getScene() {
            return this.scene;
        }

        public void setScene(Scene scene) {
            this.scene = scene;
        }
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/communication/SendMessageToXTC$Response.class */
    public static class Response extends BaseResponse {
        public Response() {
        }

        public Response(int code, String errorDesc) {
            super(code, errorDesc);
        }

        public Response(int code, String errorDesc, String transaction) {
            super(code, errorDesc, transaction);
        }

        @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
        public void toBundle(Bundle var1) {
            var1.putInt(OpenApiConstant.ResponseConstant.BUNDLE_ERROR_CODE, getCode());
            var1.putString(OpenApiConstant.ResponseConstant.BUNDLE_ERROR_DESC, getErrorDesc());
            var1.putString(OpenApiConstant.ResponseConstant.BUNDLE_ERROR_TRANSACTION, getTransaction());
            var1.putString(OpenApiConstant.ResponseConstant.BUNDLE_CONVERSATION_ID, getConversationId());
        }

        @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
        /* renamed from: fromBundle */
        public Response mo16fromBundle(Bundle var1) {
            setCode(var1.getInt(OpenApiConstant.ResponseConstant.BUNDLE_ERROR_CODE));
            setErrorDesc(var1.getString(OpenApiConstant.ResponseConstant.BUNDLE_ERROR_DESC));
            setTransaction(var1.getString(OpenApiConstant.ResponseConstant.BUNDLE_ERROR_TRANSACTION));
            setConversationId(var1.getString(OpenApiConstant.ResponseConstant.BUNDLE_CONVERSATION_ID));
            return this;
        }

        @Override // com.xtc.shareapi.share.interfaces.IBundleSerialize
        public BaseResponse checkArgs() {
            BaseResponse response = new Response();
            response.setCode(1);
            return response;
        }

        @Override // com.xtc.shareapi.share.communication.BaseResponse
        public int getType() {
            return 2;
        }
    }
}