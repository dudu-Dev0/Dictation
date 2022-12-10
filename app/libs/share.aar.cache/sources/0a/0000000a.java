package com.xtc.shareapi.share.constant;

/* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant.class */
public interface OpenApiConstant {
    public static final String TAG = "Share_";

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$App.class */
    public interface App {
        public static final String LAUNCHER = "com.xtc.i3launcher";
        public static final String LAUNCHER_CHAT_ACTIVITY = "com.xtc.open.share.chat.activity";
        public static final String LAUNCHER_MOMENT_ACTIVITY = "com.xtc.open.share.moment.activity";
        public static final String META_DATA_VERSION = "com.xtc.open.share.version";
        public static final String CHAT_PACKAGE_NAME = "com.xtc.weichat";
        public static final String MOMENT_PACKAGE_NAME = "com.xtc.moment";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$BaseRequestConstant.class */
    public interface BaseRequestConstant {
        public static final String BASE_REQUEST_TRANSACTION = "xtc_share_base_request_transaction";
        public static final String BASE_REQUEST_TYPE = "xtc_share_base_request_type";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$BuilderConstant.class */
    public interface BuilderConstant {
        public static final String KEY_IDENTIFIER = "_wxobject_identifier_";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$IntentConstant.class */
    public interface IntentConstant {
        public static final String INTENT_VERSION = "_xtc_api_version";
        public static final String INTENT_PACKAGE = "_xtc_api_package";
        public static final String INTENT_CLASSNAME = "_xtc_api_class_name";
        public static final String INTENT_APP_NAME = "_xtc_api_app_name";
        public static final String INTENT_APP_ICON = "_xtc_api_app_icon";
        public static final String INTENT_APP_KEY = "_xtc_api_app_key";
        public static final String INTENT_APP_TOKEN = "_xtc_api_app_token";
        public static final String INTENT_CHAT_SERVICE = "com.xtc.share.chat.service";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$ModuleSwitch.class */
    public interface ModuleSwitch {
        public static final String XTC_MODULE_SWITCH_OPEN = "moduleSwitch";
        public static final String XTC_MODULE_SWITCH_TIP = "moduleSwitchTip";
        public static final String SHARE_APP_MODULE = "content://com.xtc.share/switch";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$ResponseConstant.class */
    public interface ResponseConstant {
        public static final String BUNDLE_ERROR_CODE = "_xtc_api_response_code";
        public static final String BUNDLE_ERROR_DESC = "_xtc_api_response_desc";
        public static final String BUNDLE_ERROR_TRANSACTION = "_xtc_api_response_transation";
        public static final String BUNDLE_CONVERSATION_ID = "_xtc_api_conversation_id";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$SceneConstant.class */
    public interface SceneConstant {
        public static final String BUNDLE_CHAT_SELECTION_MODE = "_xtc_api_chat_select_actionMode";
        public static final String BUNDLE_CHAT_OPENID_LIST = "_xtc_api_chat_openId_list";
        public static final String BUNDLE_CHAT_CONVERSATION_LIST = "_xtc_api_conversationId_list";
        public static final String BUNDLE_CHAT_MODE_LIST = "_xtc_api_conversationMode_list";
        public static final String BUNDLE_CHAT_FRIEND_TYPE = "_xtc_api_chat_firend_type";
        public static final String BUNDLE_CHAT_FILTER_TIP = "_xtc_api_chat_filter_tip";
        public static final String BUNDLE_SCENE_SHARE_TYPE = "_xtc_api_chat_share_type";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$SdkVersionCode.class */
    public interface SdkVersionCode {
        public static final String SDK_VERSION_CODE = "1";
        public static final int CHAT_VERSION_CODE = 1;
        public static final int MOMENT_VERSION_CODE = 1;
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$SendMessageFromXTCConstant.class */
    public interface SendMessageFromXTCConstant {
        public static final String BUNDLE_MESSAGE_SHARE_SENCE = "_xtc_api_share_scene";
        public static final String BUNDLE_MESSAGE_SHARE_TYPE = "_xtc_api_share_type";
        public static final String BUNDLE_MESSAGE_SHARE_NAME = "_xtc_api_share_name";
        public static final String BUNDLE_MESSAGE_SHARE_ICON = "_xtc_api_share_icon";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$ShowMessageFromXTCConstant.class */
    public interface ShowMessageFromXTCConstant {
        public static final String BUNDLE_MESSAGE_SHARE_SENCE = "_xtc_api_share_scene";
        public static final String BUNDLE_MESSAGE_EXTEND = "_xtc_api_share_type";
        public static final String BUNDLE_MESSAGE_SHARE_NAME = "_xtc_api_share_name";
        public static final String BUNDLE_MESSAGE_SHARE_ICON = "_xtc_api_share_icon";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$TokenConstant.class */
    public interface TokenConstant {
        public static final String SHARE_APP_URI = "content://com.xtc.share/app";
        public static final String SHARE_CHAT_URI = "content://com.xtc.share.chat/app";
        public static final String SHARE_MOMENT_URI = "content://com.xtc.share.moment/app";
        public static final String SHARE_APP_PACKAGE = "xtc_share_package";
        public static final String SHARE_APP_ALLOW = "xtc_share_allow";
        public static final String SHARE_APP_TOKEN = "xtc_share_token";
        public static final String QUERY_SELECTION = "xtc_share_package = ?";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$XTCAppExtendConstant.class */
    public interface XTCAppExtendConstant {
        public static final String BUNDLE_EXTEND_INFO = "_xtc_api_share_app_content";
        public static final String BUNDLE_START_ACTIVITY = "_xtc_api_share_app_start";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$XTCImageConstant.class */
    public interface XTCImageConstant {
        public static final String BUNDLE_IMAGE_DATA = "_xtc_api_share_image_data";
        public static final String BUNDLE_IMAGE_PATH = "_xtc_api_share_image_path";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$XTCMusicConstant.class */
    public interface XTCMusicConstant {
        public static final String BUNDLE_MUSIC_DEFAULT_URL = "_xtc_api_share_music_url";
        public static final String BUNDLE_MUSIC_LOW_URL = "_xtc_api_share_music_lowUrl";
        public static final String BUNDLE_MUSIC_HIGH_URL = "_xtc_api_share_music_highUrl";
        public static final String BUNDLE_MUSIC_EXTEND = "_xtc_api_share_music_ext_info";
        public static final String BUNDLE_MUSIC_START_ACTIVITY = "_xtc_api_share_start_activity";
        public static final String BUNDLE_MUSIC_NAME = "_xtc_api_share_music_name";
        public static final String BUNDLE_MUSIC_AUTHOR = "_xtc_api_share_music_author";
        public static final String BUNDLE_MUSIC_DURATION = "_xtc_api_share_music_duration";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$XTCShareAppName.class */
    public interface XTCShareAppName {
        public static final String XTC_CHAT_APP_NAME = "微聊";
        public static final String XTC_MOMENT_APP_NAME = "好友圈";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$XTCShareMessageConstant.class */
    public interface XTCShareMessageConstant {
        public static final String BUNDLE_MESSAGE_TITLE = "_xtc_api_share_title";
        public static final String BUNDLE_MESSAGE_EXT = "_xtc_api_share_ext";
        public static final String BUNDLE_MESSAGE_DESC = "_xtc_api_share_desc";
        public static final String BUNDLE_MESSAGE_THUMB = "_xtc_api_share_thumb";
        public static final String BUNDLE_MESSAGE_ACTION = "_xtc_api_share_action";
        public static final String BUNDLE_MESSAGE_ACTION_TYPE = "_xtc_api_share_actionType";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$XTCShareType.class */
    public interface XTCShareType {
        public static final int TEXT = 1;
        public static final int IMAGE = 2;
        public static final int APP = 3;
        public static final int VIDEO = 4;
        public static final int MUSIC = 5;
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$XTCTextConstant.class */
    public interface XTCTextConstant {
        public static final String BUNDLE_TEXT = "_xtc_api_share_text_content";
    }

    /* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/constant/OpenApiConstant$XTCVideoConstant.class */
    public interface XTCVideoConstant {
        public static final String BUNDLE_VIDEO_DEFAULT_URL = "_xtc_api_share_video_url";
        public static final String BUNDLE_VIDEO_LOW_URL = "_xtc_api_share_video_lowUrl";
        public static final String BUNDLE_VIDEO_HIGH_URL = "_xtc_api_share_video_highUrl";
        public static final String BUNDLE_VIDEO_EXTINFO = "_xtc_api_share_video_ext_info";
        public static final String BUNDLE_VIDEO_START_ACTIVITY = "_xtc_api_video_start_activity";
        public static final String BUNDLE_VIDEO_NAME = "_xtc_api_share_video_name";
        public static final String BUNDLE_VIDEO_AUTHOR = "_xtc_api_share_video_author";
        public static final String BUNDLE_VIDEO_DURATION = "_xtc_api_share_video_duration";
    }
}