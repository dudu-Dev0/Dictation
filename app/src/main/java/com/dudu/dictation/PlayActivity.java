package com.dudu.dictation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;
import java.util.Collections;

import android.content.Context;

import java.io.File;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


import android.os.Environment;
import android.transition.Visibility;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;
import android.content.Intent;
import android.media.MediaPlayer;

import android.media.AudioManager;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;


public class PlayActivity extends Activity {
    private MediaPlayer mp;
    private int howManyWordsPlayed;
    private int keyOfWords;
    private int soundCount;
    private int listSize;
    private int n;
    private int count = 0;
    private List<String> wordsList;
    private int broadcastInterval;
    String dataDirName;
    TextView textofpg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        textofpg = (TextView) findViewById(R.id.textofpg);
        final Button nextPlayer = (Button) findViewById(R.id.nextPlayer);
        final Button finishPlay = (Button) findViewById(R.id.finishPlay);
        ImageButton toStartBt = (ImageButton) findViewById(R.id.stopped);
        ImageButton toStopBt = (ImageButton) findViewById(R.id.started);

        ButtonUtil.addClickScale(nextPlayer, 0.9f, 120);
        ButtonUtil.addClickScale(finishPlay, 0.9f, 120);
        ButtonUtil.addClickScale(toStartBt, 0.9f, 120);
        ButtonUtil.addClickScale(toStopBt, 0.9f, 120);

        Intent getData = getIntent();

        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volumenow = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (volumenow <= 2) {
            Toast.makeText(PlayActivity.this, "音量过小，调大音量可能会听得更清楚哦", Toast.LENGTH_SHORT).show();
        }
        SharedPreferences getSettings = getSharedPreferences("settings", MODE_PRIVATE);
        soundCount = getSettings.getInt("loopTimes", 1);
        broadcastInterval = getSettings.getInt("broadcastInterval", 3000);
        boolean scrambleTheOrder = getSettings.getBoolean("scrambleTheOrder", true);
        String wayType = getData.getStringExtra("wayType");
        if (Objects.equals(wayType, "text")) {
            String dataFileName = getData.getStringExtra("dataFileName");
            wordsList = FileUtils.readFile2List(this.getExternalFilesDir("dictation") + File.separator + dataFileName, "UTF-8");
            if (scrambleTheOrder) {
                Collections.shuffle(wordsList);
            }
            listSize = wordsList.size();
            System.out.println(wordsList);
            howManyWordsPlayed = 1;
            keyOfWords = howManyWordsPlayed - 1;
            textofpg.setText(howManyWordsPlayed + "/" + (listSize));
            StartTTS(wordsList.get(keyOfWords));
            nextPlayer.setOnClickListener(view -> {
                Stop();
                soundCount = getSettings.getInt("loopTimes", 1);
                howManyWordsPlayed++;
                keyOfWords = howManyWordsPlayed - 1;
                textofpg.setText(howManyWordsPlayed + "/" + (listSize));
                StartTTS(wordsList.get(keyOfWords));
                setStartAndStopButtonVisible(true);
            });
            finishPlay.setOnClickListener(view -> {
                Stop();
                Intent intent = new Intent(PlayActivity.this, FinishedPlayActivity.class);
                intent.putExtra("dataFileName", dataFileName);
                intent.putExtra("wayType","text");
                startActivity(intent);
                finish();
            });
            toStartBt.setOnClickListener(view -> {
                setStartAndStopButtonVisible(true);
                try {
                    mp.start();
                } catch (IllegalStateException e) {
                }
            });
            toStopBt.setOnClickListener(view -> {
                setStartAndStopButtonVisible(false);
                try {
                    mp.pause();
                } catch (IllegalStateException e) {
                }
            });
        }
        if (Objects.equals(wayType, "record")){
            dataDirName = getData.getStringExtra("dataDirName");
            System.out.println(dataDirName);
            wordsList = DirList.getRecordedFileNames(new File(dataDirName));
            listSize = wordsList.size();
            System.out.println(wordsList);
            howManyWordsPlayed = 1;
            keyOfWords = howManyWordsPlayed - 1;
            textofpg.setText(howManyWordsPlayed + "/" + (listSize));
            if (scrambleTheOrder == true) {
                Collections.shuffle(wordsList);
            }
            StartPlay(dataDirName+File.separator+wordsList.get(keyOfWords));
            nextPlayer.setOnClickListener(view -> {
                if(NoDoubleClick.isNotFastClick()) {
                    if (mp != null) {
                        //if (mp.getDuration() >= 1000) {
                        Stop();
                        soundCount = getSettings.getInt("loopTimes", 1);
                        howManyWordsPlayed++;
                        keyOfWords = howManyWordsPlayed - 1;
                        textofpg.setText(howManyWordsPlayed + "/" + (listSize));
                        try {
                            StartPlay(dataDirName + File.separator + wordsList.get(keyOfWords));
                        } catch (IndexOutOfBoundsException e) {
                        }

                        setStartAndStopButtonVisible(true);
                    } //else {
                        //Toast.makeText(PlayActivity.this, "你确定这么短的音频都要跳过嘛QwQ", Toast.LENGTH_SHORT).show();
                    //}
                }else{
                    Toast.makeText(PlayActivity.this, "点太快啦", Toast.LENGTH_SHORT).show();
                }
                //}
            });
            finishPlay.setOnClickListener(view -> {
                Stop();
                Intent intent = new Intent(PlayActivity.this, FinishedPlayActivity.class);
                intent.putExtra("dataDirName", dataDirName);
                intent.putExtra("wayType","record");
                startActivity(intent);
                finish();
            });
            toStartBt.setOnClickListener(view -> {
                setStartAndStopButtonVisible(true);
                try {
                    mp.start();
                } catch (IllegalStateException e) {
                }
            });
            toStopBt.setOnClickListener(view -> {
                setStartAndStopButtonVisible(false);
                try {
                    mp.pause();
                } catch (IllegalStateException e) {
                }
            });
        }
    }

    private void setNextButtonVisible() {
        Button nextPlayer = (Button) findViewById(R.id.nextPlayer);
        Button finishPlay = (Button) findViewById(R.id.finishPlay);
        nextPlayer.setVisibility(View.GONE);
        finishPlay.setVisibility(View.VISIBLE);
    }

    private void setStartAndStopButtonClickable(boolean clickable) {
        ImageButton toStartBt = (ImageButton) findViewById(R.id.stopped);
        ImageButton toStopBt = (ImageButton) findViewById(R.id.started);
        if (clickable) {
            toStartBt.setClickable(true);
            toStopBt.setClickable(true);
        }
        if (!clickable) {
            toStartBt.setClickable(false);
            toStopBt.setClickable(false);
        }
    }

    private void setStartAndStopButtonVisible(boolean startOrStop) {
        //按到开始按钮时应传入true
        //按到停止按钮时传入false
        ImageButton toStartBt = (ImageButton) findViewById(R.id.stopped);
        ImageButton toStopBt = (ImageButton) findViewById(R.id.started);
        if (startOrStop) {
            toStartBt.setVisibility(View.GONE);
            toStopBt.setVisibility(View.VISIBLE);
        } else {
            toStartBt.setVisibility(View.VISIBLE);
            toStopBt.setVisibility(View.GONE);
        }
    }

    //TTS的调用
    public void StartTTS(String query) {
        if (howManyWordsPlayed == listSize) {
            setNextButtonVisible();
            setStartAndStopButtonClickable(false);
        }
        System.out.println("https://tts.youdao.com/fanyivoice?word=" + query + "&le=zh&keyfrom=speaker-target");
        Uri location = Uri.parse("https://tts.youdao.com/fanyivoice?word=" + query + "&le=zh&keyfrom=speaker-target");

        mp = MediaPlayer.create(PlayActivity.this, location);
        System.out.println("音乐开始播放");
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                try {
                    Thread.sleep(broadcastInterval);
                } catch (InterruptedException e) {
                }
                try {
                    soundCount--;
                    if (soundCount >= 1) {
                        mp.start();
                    } else {
                        Stop();
                        howManyWordsPlayed++;
                        keyOfWords = howManyWordsPlayed - 1;
                        if (howManyWordsPlayed <= listSize) {
                            textofpg.setText(howManyWordsPlayed + "/" + listSize);
                        }
                        SharedPreferences getSettings = getSharedPreferences("settings", MODE_PRIVATE);
                        soundCount = getSettings.getInt("loopTimes", 1);
                        StartTTS(wordsList.get(keyOfWords));
                        setStartAndStopButtonVisible(true);
                    }
                } catch (Exception e) {
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


    protected void StartPlay(String filePath) {

                MediaPlayer mp = new MediaPlayer();
                System.out.println(filePath);
                try {
                    mp.setDataSource(filePath);
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
                if (howManyWordsPlayed == listSize) {
                    setNextButtonVisible();
                    setStartAndStopButtonClickable(false);
                }
                System.out.println("音乐开始播放");

                try {
                    mp.prepare();
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        try {
                            Thread.sleep(broadcastInterval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            soundCount--;
                            if (soundCount >= 1) {
                                mp.start();
                            } else {
                                Stop();
                                howManyWordsPlayed++;
                                keyOfWords = howManyWordsPlayed - 1;
                                if (howManyWordsPlayed <= listSize) {
                                    textofpg.setText(howManyWordsPlayed + "/" + listSize);
                                }
                                SharedPreferences getSettings = getSharedPreferences("settings", MODE_PRIVATE);
                                soundCount = getSettings.getInt("loopTimes", 1);

                                StartPlay(dataDirName + File.separator + wordsList.get(keyOfWords));
                                setStartAndStopButtonVisible(true);
                            }
                        } catch (Exception e) {
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




    protected void Stop() {
        try {
            if (mp != null && mp.isPlaying()) {
                mp.stop();


            }
        } catch (IllegalStateException e) {
        }


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mp.stop();
            mp.release();
            mp = null;
        } catch (IllegalStateException e) {
        } catch (NullPointerException e) {
        }


    }
}