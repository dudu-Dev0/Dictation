package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RecordActivity extends AppCompatActivity {
    private int n = 0;
    public static final int UPDATE_PROGRESS = 114514;
    private String name;
    ImageButton startRecord;
    ImageButton stopRecord;
    File dir;
    private TextView howManyRecordedText;
    private CircleProgressView progressBar;
    private MediaRecorder recorder = new MediaRecorder();
    Timer timer = new Timer();
    private int progress;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    progress = progressBar.getProgress();
                    progressBar.setProgress(progress + 1);
                    break;
                default:
                    break;
            }
        }
    };
TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = UPDATE_PROGRESS;
            handler.sendMessage(message);
            if (progress >= 100) {
                try{
                timer.cancel();
                timer.purge();
                timer = null;
                }catch (NullPointerException e){}
                stopRecord();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        name = getIntent().getStringExtra("dataDirName");
        howManyRecordedText = findViewById(R.id.howManyRecordedText);
        startRecord = findViewById(R.id.startRecord);
        stopRecord = findViewById(R.id.stopRecord);
        Button next = findViewById(R.id.recordNextWord);
        Button finish = findViewById(R.id.finishRecord);
        progressBar = findViewById(R.id.progress);
        progress = progressBar.getProgress();
        initText();
        dir = new File(this.getExternalFilesDir("dictation").getPath() + File.separator + name);
        setListeners();
        next.setOnClickListener(view -> {
            if(FileUtils.isFileExists(new File(dir,n + ".amr"))) {
                stopRecord();
                progressBar.setProgress(0);
                try {
                    mTimerTask.cancel();
                } catch (IllegalStateException e) {
                } catch (NullPointerException e) {
                }
                initText();
                startRecord.setVisibility(View.VISIBLE);
                stopRecord.setVisibility(View.GONE);
                setListeners();
            }
        });
        finish.setOnClickListener(view -> {
            stopRecord();
            try {
                mTimerTask.cancel();
            } catch (IllegalStateException e) {
            }catch (NullPointerException e){}
            Intent intent = new Intent(RecordActivity.this,PlayActivity.class);
            intent.putExtra("wayType","record");
            intent.putExtra("dataDirName",this.getExternalFilesDir("dictation").getPath() + File.separator + name);
            startActivity(intent);
            finish();
        });
    }

    private void initText() {
        n++;
        howManyRecordedText.setText("第" + n + "个");
    }


    private void startRecord() {

        recorder = new MediaRecorder();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File soundFile = new File(dir, n + ".amr");//存储到SD卡当然也可上传到服务器

        if (!soundFile.exists()) {
            try {
                soundFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //⾳频输⼊源
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB); //设置输出格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB); //设置编码格式
        recorder.setOutputFile(soundFile.getAbsolutePath());
        try {
            recorder.prepare();
            try {
                recorder.start();
            } catch (IllegalStateException e) {
            }
            //开始录制
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//停⽌录制，资源释放
    private void stopRecord() {
        if (recorder != null) {
            try {
                //recorder.stop();
                recorder.release();
            }catch(IllegalStateException e){}
        }
    }
    public void setListeners () {
        startRecord.setOnClickListener(view -> {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = UPDATE_PROGRESS;
                    handler.sendMessage(message);
                    if (progress >= 100) {
                        try{
                            timer.cancel();
                            timer.purge();
                            timer = null;
                        }catch (NullPointerException e){}
                        stopRecord();
                    }
                }
            };
            startRecord();
            timer.schedule(mTimerTask, 100, 100);
            startRecord.setVisibility(View.GONE);
            stopRecord.setVisibility(View.VISIBLE);
        });
        stopRecord.setOnClickListener(view -> {
            stopRecord();
            try {
                mTimerTask.cancel();
            } catch (IllegalStateException e) {
            }
            startRecord.setVisibility(View.VISIBLE);
            stopRecord.setVisibility(View.GONE);
            startRecord.setOnClickListener(view2 -> {
            });
        });
    }
}