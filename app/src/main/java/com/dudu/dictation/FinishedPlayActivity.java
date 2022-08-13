package com.dudu.dictation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;

import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import java.util.List;
import java.io.File;
import java.util.Objects;

import android.content.Intent;


public class FinishedPlayActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishedplay);
        Button backbt = (Button)findViewById(R.id.backbt);
        Button answer = (Button)findViewById(R.id.answer);
        TextView text = (TextView)findViewById(R.id.text);
        Intent getData = getIntent();
        String dataFileName = getData.getStringExtra("dataFileName");
        String dataDirName = getData.getStringExtra("dataDirName");
        String wayType = getData.getStringExtra("wayType");
        if (Objects.equals(wayType, "text")) {
            List<String> wordsList = FileUtils.readFile2List(this.getExternalFilesDir("dictation") + File.separator + dataFileName, "UTF-8");
            text.setText("本次听写" + wordsList.size() + "个单词");
            SharedPreferences getSPData = getSharedPreferences("settings", MODE_PRIVATE);
            boolean autoDel = getSPData.getBoolean("autoDel", false);
            if (autoDel) {
                FileUtils.deleteFile(this.getExternalFilesDir("dictation") + File.separator + dataFileName);
            }
            backbt.setOnClickListener(v -> finish());
            answer.setOnClickListener(v -> {
                Intent intent = new Intent(FinishedPlayActivity.this, AnswerActivity.class);
                intent.putExtra("dataFileName", dataFileName);
                startActivity(intent);
                finish();
            });
        }
        if (Objects.equals(wayType, "record")) {
            List<String> wordsList = DirList.getRecordedFileNames(new File(dataDirName+ File.separator));
            text.setText("本次听写" + wordsList.size() + "个单词");
            SharedPreferences getSPData = getSharedPreferences("settings", MODE_PRIVATE);
            boolean autoDel = getSPData.getBoolean("autoDel", false);
            if (autoDel) {
                FileUtils.deleteDir(dataDirName);
            }
            backbt.setOnClickListener(v -> finish());
            answer.setOnClickListener(v -> {
                Toast.makeText(this, "暂不支持语音听写查看答案", Toast.LENGTH_SHORT).show();
            });
        }
    }
    
}
