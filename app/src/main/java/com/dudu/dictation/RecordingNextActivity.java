package com.dudu.dictation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.text.format.Time;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Locale;
import android.os.FileUtils;
import java.io.IOException;
import java.io.File;
import android.media.MediaRecorder;
import android.content.Intent;
import java.util.Date;


public class RecordingNextActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordingnext);
        final ProgressBar pggreen = (ProgressBar)findViewById(R.id.progesss1next);
        final ImageView record = (ImageView)findViewById(R.id.recordIVnext);        //获得控件实例
        final ImageView stopIV = (ImageView)findViewById(R.id.stopIVnext);
        final Button nextbt = (Button)findViewById(R.id.todonext);
        final Button finishbtnext = (Button)findViewById(R.id.finishbtnext);
        Intent get = getIntent();
        int a = 0;
        int geteddata = get.getIntExtra("data",a);
        final String dirString= get.getStringExtra("dir");
        final File dir = new File(dirString);
        a = geteddata+1;
        final int frequency = a;
        System.out.println(dir);
        
            
        
        record.setOnClickListener(new NoDoubleClickListener(){
                @Override
                public void onNoDoubleClick(View v) {
                    record.setVisibility(View.GONE);
                    stopIV.setVisibility(View.VISIBLE);
                    startRecord();
                    nextbt.setOnClickListener(new NoDoubleClickListener(){
                            @Override
                            public void onNoDoubleClick(View v){
                                int filename = frequency;
                                stopRecord();
                                Intent intent = new Intent(RecordingNextActivity.this,RecordingNextActivity.class);
                                intent.putExtra("data",filename);
                                intent.putExtra("dir",dirString);
                                startActivity(intent);
                                finish();

                            }
                        });

                    finishbtnext.setOnClickListener(new NoDoubleClickListener(){
                            @Override
                            public void onNoDoubleClick(View v){
                                stopRecord();
                                Intent intent = new Intent(RecordingNextActivity.this,PlayActivity.class);
                                intent.putExtra("dir",dirString);
                                startActivity(intent);
                                finish();
                            }
                        });
                        final Timer timer=new Timer(); TimerTask task=new TimerTask(){        //开启线程
                        int progress = pggreen.getProgress();
                        public boolean isContinue = false;
                            
                            public void run() {
                                
                                
                                progress = progress + 20;
                                pggreen.setProgress(progress);//每隔1s progress+20，5秒结束
                                if(progress==100){
                                    stopRecord();
                                    timer.cancel();


                                }
                                
                                
                                    
                                
                                
                                }
                                
                                
                            
                            
                        
                        
                        };
                        
                        timer.schedule(task, 1000, 1000);
                        
                        
                        
                        
                    
                  
                }
        
                MediaRecorder mr = new MediaRecorder();

                public void startRecord() {

                    

                    File soundFile = new File(dir, frequency + ".amr");//存储到SD卡当然也可上传到服务器

                    if (!soundFile.exists()) {

                        try {

                            soundFile.createNewFile();

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                    }



                    mr.setAudioSource(MediaRecorder.AudioSource.MIC); //⾳频输⼊源

                    mr.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB); //设置输出格式

                    mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB); //设置编码格式

                    mr.setOutputFile(soundFile.getAbsolutePath());

                    try {

                        mr.prepare();

                        mr.start(); //开始录制

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }






//停⽌录制，资源释放

                public void stopRecord() {

                    if (mr != null) {

                        mr.stop();

                        mr.release();

                        mr = null;
                        
                        
                        
                        
                        

                    }

                }

            });
    
    }
        



    
    

}


            
        
    


    

