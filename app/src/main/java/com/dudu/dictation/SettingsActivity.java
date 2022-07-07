package com.dudu.dictation;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import android.view.View;

import com.xtc.shareapi.share.shareobject.XTCAppExtendObject;
import com.xtc.shareapi.share.shareobject.XTCShareMessage;
import android.graphics.BitmapFactory;
import com.xtc.shareapi.share.communication.SendMessageToXTC;
import com.xtc.shareapi.share.manager.ShareMessageManager;
import android.widget.LinearLayout;
import android.widget.Switch;

import android.widget.Toast;


public class SettingsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toast.makeText(SettingsActivity.this,"此功能作者还没有做",Toast.LENGTH_SHORT).show();
    
        // 查找 include 的 id, 其实找到的是 res/layout/include_switch.xml 的根布局
        LinearLayout layout=findViewById(R.id.item_switch);
        // 在上面的根布局上, 再次查找 Switch 的 id, 就能找到我们的 Switch 控件了
        Switch layout_switch=layout.findViewById(R.id.switch_widget);
        Button btshare = (Button) findViewById(R.id.btshare);
        btshare.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    XTCAppExtendObject xtcAppExtendObject = new XTCAppExtendObject();
                    xtcAppExtendObject.setStartActivity(MainActivity.class.getName());
                    xtcAppExtendObject.setExtInfo("ExtendInfo");
                    XTCShareMessage xtcShareMessage = new XTCShareMessage();
                    xtcShareMessage.setShareObject(xtcAppExtendObject);
                    xtcShareMessage.setThumbImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
                    xtcShareMessage.setDescription("快来安装dudu开发的听写吧");
                    SendMessageToXTC.Request request = new SendMessageToXTC.Request();
                    request.setMessage(xtcShareMessage);
                    new ShareMessageManager(SettingsActivity.this).sendRequestToXTC(request, "appKey");

                }

            });
        

    }


};

