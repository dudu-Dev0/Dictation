package com.dudu.dictation;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import java.util.List;
import java.util.Collections;
import android.content.Context;
import java.io.File;


import android.os.Environment;
import android.transition.Visibility;
import android.widget.TextView;
import android.util.Log;
import android.content.Intent;
import android.media.MediaPlayer;
import java.io.IOException;
import android.media.AudioManager;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;
import android.widget.ProgressBar;

import com.youdao.sdk.app.Language;
import com.youdao.sdk.app.YouDaoApplication;
import com.youdao.speechsynthesizer.online.YoudaoSpeechSynthesizer;
import com.youdao.speechsynthesizer.online.YoudaoSpeechSynthesizerParameters;

import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends Activity {
    private MediaPlayer mp;
    private int howManyWordsPlayed;
    private int keyOfWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        final TextView textofpg = (TextView)findViewById(R.id.textofpg);
        final Button nextPlayer = (Button)findViewById(R.id.nextPlayer);
        final Button finishPlay = (Button)findViewById(R.id.finishPlay);
        final ProgressBar progressbarOfMusic = (ProgressBar)findViewById(R.id.progesssbarOfMusic);
        Intent getData = getIntent();
        String dataFileName = getData.getStringExtra("dataFileName");
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volumenow = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
        if (volumenow<=2){
            Toast.makeText(PlayActivity.this,"音量过小，调大音量可能会听得更清楚哦",Toast.LENGTH_SHORT).show();
        }

        List<String> wordsList= FileUtils.readFile2List(Environment.getExternalStorageDirectory() + "/dictation"+File.separator+dataFileName,"UTF-8");
        int listSize = wordsList.size();
        System.out.println(wordsList);
        howManyWordsPlayed = 1;
        keyOfWords = howManyWordsPlayed-1;
        textofpg.setText(howManyWordsPlayed+"/"+(listSize));
        Start(wordsList.get(keyOfWords));
        nextPlayer.setOnClickListener(view -> {
            Stop();
            howManyWordsPlayed++;
            keyOfWords = howManyWordsPlayed-1;
            textofpg.setText(howManyWordsPlayed+"/"+(listSize));
            if (howManyWordsPlayed==listSize){
                setVisible();
            }
            Start(wordsList.get(keyOfWords));
        });
        finishPlay.setOnClickListener(view -> {
            Stop();
            Intent intent = new Intent(PlayActivity.this,FinishedPlayActivity.class);
            intent.putExtra("dataFileName",dataFileName);
            startActivity(intent);
            finish();
        });
    }

    private void setVisible() {
        TextView textofpg = (TextView)findViewById(R.id.textofpg);
        Button nextPlayer = (Button)findViewById(R.id.nextPlayer);
        Button finishPlay = (Button)findViewById(R.id.finishPlay);
        ProgressBar progressbarOfMusic = (ProgressBar)findViewById(R.id.progesssbarOfMusic);
        nextPlayer.setVisibility(View.GONE);
        finishPlay.setVisibility(View.VISIBLE);
    }

    //TTS的调用
    public void Start(String query) {

            System.out.println("https://tts.youdao.com/fanyivoice?word=" + query + "&le=zh&keyfrom=speaker-target");
            Uri location = Uri.parse("https://tts.youdao.com/fanyivoice?word=" + query + "&le=zh&keyfrom=speaker-target");

            mp = MediaPlayer.create(PlayActivity.this, location);
            System.out.println("音乐开始播放");
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                // 不循环播放
                try {

                    mp.stop();
                    mp.release();
                    // mp.start();
                    System.out.println("stopped");
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
            });
            // 播放音乐时发生错误的事件处理
            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // 释放资源
                    try {
                        mp.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        }

        // super.onStart(intent, startId);

    protected void Stop() {
        try{
        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = null;

        }
        }catch(IllegalStateException e){}


    }
    }




    
    

