package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

public class SetNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_name);

        EditText nameText = (EditText) findViewById(R.id.nameText);
        Button finishSetName = (Button) findViewById(R.id.finishSetName);
        FileUtils.createOrExistsDir("/sdcard/dictation/");
        finishSetName.setOnClickListener(view -> {
            String name = nameText.getText().toString();
            if(FileUtils.isFileExists("/sdcard/dictation/"+name)){
                Toast.makeText(this,"请不要重复使用同一个名字",Toast.LENGTH_SHORT).show();
            }else{String path = Environment.getExternalStorageDirectory() + "/dictation";
                Log.e("------path", path);
                File files = new File(path);
                if (!files.exists()) {
                    files.mkdirs();
                }
                try {
                    FileWriter fw = new FileWriter(path + File.separator + name);
                    fw.write("");
                    fw.close();
                    Toast.makeText(SetNameActivity.this, "文件写入成功", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            Intent intent = new Intent(SetNameActivity.this,InputWordsActivity.class);
            intent.putExtra("dataFileName",name);
            startActivity(intent);}
        });
    }
}