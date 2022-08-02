package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.VideoView;

public class EasterEggActivity extends AppCompatActivity {
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easter_egg);
        mVideoView = findViewById(R.id.video);

        //播放本地视频
        String videoPath = "https://vdse.bdstatic.com//192d9a98d782d9c74c96f09db9378d93.mp4";
        //设置播放地址，网络视频同样使用此方法，将网址链接放入即可
        mVideoView.setVideoPath(videoPath);
        mVideoView.setZOrderOnTop(true);
//开始播放
        mVideoView.start();



    }
}