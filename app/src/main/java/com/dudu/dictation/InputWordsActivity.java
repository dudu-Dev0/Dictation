package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InputWordsActivity extends AppCompatActivity {
    private int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_words);

        TextView numberTextOfWords = (TextView)findViewById(R.id.numberTextOfWords);
        EditText wordsText = (EditText)findViewById(R.id.wordsText);
        Button inputNextWord = (Button)findViewById(R.id.inputNextWord);
        Button finishInput = (Button)findViewById(R.id.finishInput);

        Intent getExtra = getIntent();
        String name = getExtra.getStringExtra("dataFileName");
        n = 1;                       //初始化数据
        numberTextOfWords.setText("单词"+n);
        inputNextWord.setOnClickListener(view -> {
            String word = wordsText.getText().toString();
            if(StringUtil.isBlank(word)||SpecialSymbolsUtil.isSpecialChar(word)) {
                Toast.makeText(InputWordsActivity.this,"请勿输入特殊符号",Toast.LENGTH_SHORT);
            }

            else {
                String path = this.getExternalFilesDir("dictation").getPath();
                FileUtils.writeFileFromString(path + File.separator + name, word + "\n", true);
                wordsText.setText("");
                n++;
                numberTextOfWords.setText("单词" + n);
            }
        });
        finishInput.setOnClickListener(view -> {
            String word = wordsText.getText().toString();

            if(StringUtil.isBlank(word)||SpecialSymbolsUtil.isSpecialChar(word)) {
                Toast.makeText(InputWordsActivity.this,"请勿输入空字符或非法符号",Toast.LENGTH_SHORT);
            }else {
                String path = this.getExternalFilesDir("dictation").getPath();
                FileUtils.writeFileFromString(path + File.separator + name, word, true);
                wordsText.setText("");
                Intent intent = new Intent(this, PlayActivity.class);
                intent.putExtra("dataFileName", name);
                startActivity(intent);
                finish();
            }
        });

    }
}