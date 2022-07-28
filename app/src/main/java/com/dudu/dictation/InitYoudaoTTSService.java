package com.dudu.dictation;

import android.app.Application;
import android.widget.Toast;

import com.youdao.sdk.app.YouDaoApplication;

public class InitYoudaoTTSService extends Application{
    private static InitYoudaoTTSService swYouAppction;

    @Override
    public void onCreate() {
        super.onCreate();
        YouDaoApplication.init(this,"\n" + "6ee26a242e433f7c");//创建应用，每个应用都会有一个Appid，绑定对应的翻译服务实例，即可使用
        swYouAppction = this;
        Toast.makeText(this, "初始化完成", Toast.LENGTH_SHORT).show();
    }

    public static InitYoudaoTTSService getInstance() {
        return swYouAppction;
    }

}

