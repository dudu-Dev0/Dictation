package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.xtc.shareapi.share.communication.BaseResponse;
import com.xtc.shareapi.share.communication.SendMessageToXTC;
import com.xtc.shareapi.share.communication.ShowMessageFromXTC;
import com.xtc.shareapi.share.interfaces.IResponseCallback;
import com.xtc.shareapi.share.interfaces.IXTCCallback;
import com.xtc.shareapi.share.manager.ShareMessageManager;
import com.xtc.shareapi.share.manager.XTCCallbackImpl;
import com.xtc.shareapi.share.shareobject.XTCAppExtendObject;
import com.xtc.shareapi.share.shareobject.XTCShareMessage;

public class AboutActivity extends AppCompatActivity implements IResponseCallback {
    private int clickCount;
    private IXTCCallback xtcCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView easterEgg = findViewById(R.id.easter_egg);
        Button shareApp = findViewById(R.id.shareApp);
        ButtonUtil.addClickScale(shareApp,0.9f,120);

        easterEgg.setOnClickListener(view -> {
            clickCount++;
            if(clickCount>=50){
                easterEgg.setOnLongClickListener(view1 -> {
                    Intent intent = new Intent(AboutActivity.this,EasterEggActivity.class);
                    startActivity(intent);
                    return false;
                });
            }

        });
        shareApp.setOnClickListener(view -> {
            //第一步：创建XTCAppExtendObject对象
            XTCAppExtendObject xtcAppExtendObject = new XTCAppExtendObject();
//设置点击分享的内容启动的页面
            xtcAppExtendObject.setStartActivity(HistoryActivity.class.getName());
//设置分享的扩展信息，点击分享的内容会将该扩展信息带入跳转的页面
            xtcAppExtendObject.setExtInfo("114514");

//第二步：创建XTCShareMessage对象，并将shareObject属性设置为xtcTextObject对象
            XTCShareMessage xtcShareMessage = new XTCShareMessage();
            xtcShareMessage.setShareObject(xtcAppExtendObject);
//设置图片
            xtcShareMessage.setThumbImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
//设置文本
            xtcShareMessage.setDescription("快来下载听写APP吧，我觉得还不错哦");

//第三步：创建SendMessageToXTC.Request对象，并设置message属性为xtcShareMessage
            SendMessageToXTC.Request request = new SendMessageToXTC.Request();
            request.setMessage(xtcShareMessage);

//第四步：创建ShareMessageManagr对象，调用sendRequestToXTC方法，传入SendMessageToXTC.Request对象和AppKey
            new ShareMessageManager(this).sendRequestToXTC(request, "114514");
        });
        //处理回调
        xtcCallback = new XTCCallbackImpl();
        xtcCallback.handleIntent(getIntent(), this);
    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        //处理回调
        xtcCallback.handleIntent(intent, this);
    }

    //第三步：实现onResp和onReq方法
    public void onResp(boolean isSuccess, BaseResponse response) {
        //to-do
        if(isSuccess){
            Toast.makeText(AboutActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(AboutActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
            finish();

        }
    }
    public void onReq(ShowMessageFromXTC.Request request) {
        //to-do
    }
}