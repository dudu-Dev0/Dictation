package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import java.io.File;

public class AnswerActivity extends AppCompatActivity {
    private String dataFileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        TextView answerText = findViewById(R.id.answerText);

        Intent getFileName = getIntent();
        dataFileName = getFileName.getStringExtra("dataFileName");

        String answers = FileUtils.readFile2String(this.getExternalFilesDir("dictation")+ File.separator+dataFileName,"UTF-8");
        answerText.setText(answers);
    }
}