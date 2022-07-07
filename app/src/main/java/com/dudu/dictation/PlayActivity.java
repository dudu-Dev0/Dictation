package com.dudu.dictation;

import android.app.Activity;
import android.os.Bundle;
import java.util.List;
import java.util.Collections;
import android.content.Context;
import java.io.File;


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
import java.util.Timer;
import java.util.TimerTask;

public class PlayActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Intent getData = getIntent();
        final String dirString = getData.getStringExtra("dir");
        System.out.println("目录"+dirString);
        final File dir = new File(dirString);
        final GlobalVariable GV = new GlobalVariable();
        
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volumenow = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
        if (volumenow<=2){
            Toast.makeText(PlayActivity.this,"音量过小，调大音量可能会听得更清楚哦",Toast.LENGTH_SHORT).show();
        }
        final TextView textofpg = (TextView)findViewById(R.id.textofpg);
        final Button nextPlayer = (Button)findViewById(R.id.nextPlayer);
        final Button finishPlay = (Button)findViewById(R.id.finishPlay);
        final ProgressBar progressbarOfMusic = (ProgressBar)findViewById(R.id.progesssbarOfMusic);
        List<File> files;
        if (GV.varInt==0){
        files = FileList.getFile(dir);
        Collections.shuffle(files);
        }
        else{files = GV.varList;}
        System.out.println(files);
        textofpg.setText(Integer.toString(GV.varInt+1)+"/"+files.size());
        final List<File> listOfpath = files;
        File pathOfMusic = files.get(GV.varInt);
        System.out.println(GlobalVariable.varInt);
        final MediaPlayer player = new MediaPlayer();
        final int tmpVarInt = GV.varInt;
        try{String music = pathOfMusic.getAbsolutePath();
            System.out.println(music);
            player.setDataSource(music);
            if(GV.varInt == files.size()-1){
                nextPlayer.setVisibility(View.GONE);
                finishPlay.setVisibility(View.VISIBLE);
                GV.varInt = 0;
                GV.varList = null;
            }
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);    //播放音频
        player.prepare();
        player.start();
        }catch(IOException e){
            System.out.println("err,cannot prepare mediaplayer");
        }
        System.out.println("音频时长"+player.getDuration());
        final int dur = player.getDuration();
        final Timer timer=new Timer(); final TimerTask task=new TimerTask(){        //开启线程
            double progress = 0;
            double time=0.0;
            double duration = (double)dur;
            public volatile boolean exit = false;
            public void run() {
                time=time+1000.0;
                progress=(time/duration)*100;
                System.out.println(time);
                System.out.println(progress);
                    nextPlayer.setOnClickListener(new NoDoubleClickListener(){
                            @Override
                            public void onNoDoubleClick(View v){
                                if(dur<=500){
                                    Toast.makeText(AppContext.getContext(),"还没听清吧，再等等吧",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                player.stop();
                                player.release();
                                GV.varList = listOfpath;
                                System.out.println(GV.varList);
                                GV.varInt= GV.varInt+1;
                                Intent intent = new Intent(PlayActivity.this,PlayActivity.class);
                                intent.putExtra("dir",dirString);
                                startActivity(intent);
                                timer.cancel();
                                finish();

                                }
                            }
                        });
                    finishPlay.setOnClickListener(new NoDoubleClickListener(){
                            @Override
                            public void onNoDoubleClick(View v){
                                player.stop();
                                player.release();
                                GV.varInt = 0;
                                GV.varList = null;
                                Intent intent = new Intent(PlayActivity.this,FinishedPlayActivity.class);
                                intent.putExtra("dir",dirString);
                                startActivity(intent);
                                timer.cancel();
                                finish();
                            }
                        });
                if(progress < 100.0){
                    progressbarOfMusic.setProgress((int)progress);
                }
                if(progress >= 100.0){
                    progressbarOfMusic.setProgress((int)progress);
                    if(FileList.getFile(dir).size()==tmpVarInt+1){
                        timer.cancel();
                        player.stop();
                        player.release();
                    }
                    else{
                        try{Thread.sleep(2000);

                            System.out.println(tmpVarInt);
                            System.out.println(FileList.getFile(dir));
                            GV.varList = listOfpath;
                            System.out.println(GV.varList);
                            GV.varInt= GV.varInt+1;
                            player.stop();
                            player.release();
                            timer.cancel();
                            Intent intent = new Intent(PlayActivity.this,PlayActivity.class);
                            intent.putExtra("dir",dirString);
                            startActivity(intent);
                            finish();
                        }catch(InterruptedException e){
                            Log.e("PlayActivity","cannot sleep");
                        }
                    }

                }
                
            }
            

            
                
                
                
            



        };
        timer.schedule(task,1000,1000);
        
    }


    
    
}
