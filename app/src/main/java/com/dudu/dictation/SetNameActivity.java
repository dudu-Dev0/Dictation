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
import java.util.Objects;

public class SetNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_name);

        Intent getType = getIntent();
        String wayType = getType.getStringExtra("wayType");

        EditText nameText = (EditText) findViewById(R.id.nameText);
        Button finishSetName = (Button) findViewById(R.id.finishSetName);

        ButtonUtil.addClickScale(finishSetName, 0.9f, 120);

        finishSetName.setOnClickListener(view -> {
            String name = nameText.getText().toString();
            if(FileUtils.isFileExists(this.getExternalFilesDir("dictation")+ File.separator+name)){
                Toast.makeText(this,"请不要重复使用同一个名字",Toast.LENGTH_SHORT).show();
            }else{String path = this.getExternalFilesDir("dictation").getPath();
                Log.e("------path", path);
                File files = new File(path);
                if (!files.exists()) {
                    files.mkdirs();
                }
                if(Objects.equals(wayType, "input")) {
                    try {
                        FileWriter fw = new FileWriter(path + File.separator + name);
                        fw.write("");
                        fw.close();
                        Intent intent = new Intent(SetNameActivity.this,InputWordsActivity.class);
                        intent.putExtra("dataFileName",name);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }if(Objects.equals(wayType, "record")){
                    boolean isCreated = FileUtils.createOrExistsDir(path + File.separator + name);
                    if(!isCreated){
                        Toast.makeText(SetNameActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(SetNameActivity.this,RecordActivity.class);
                    intent.putExtra("dataDirName",name);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}