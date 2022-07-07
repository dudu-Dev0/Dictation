package com.dudu.dictation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import java.util.List;
import java.io.File;
import android.content.Intent;


public class FinishedPlayActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishedplay);
        Button backbt = (Button)findViewById(R.id.backbt);
        Button sharebt = (Button)findViewById(R.id.sharebt);
        TextView text = (TextView)findViewById(R.id.text);
        Intent getData = getIntent();
        String dirString = getData.getStringExtra("dir");
        File dir = new File(dirString);
        
        List<File> filelist = FileList.getFile(dir);
        text.setText("本次听写"+filelist.size()+"个单词");
        backbt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        sharebt.setOnClickListener(new View.OnClickListener(){ 
            @Override
            public void onClick(View v){
                Toast.makeText(FinishedPlayActivity.this,"暂无此功能",Toast.LENGTH_SHORT).show();
            
            }
        });
    }
    
}
