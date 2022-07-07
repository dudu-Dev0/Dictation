package com.dudu.dictation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import com.tencent.bugly.crashreport.CrashReport;
import android.Manifest;
import androidx.core.app.ActivityCompat;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrashReport.initCrashReport(getApplicationContext());
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO},0);
        Button btsettings = (Button) findViewById(R.id.button2);
        Button bttorecord = (Button) findViewById(R.id.button1);
        Button bthistory =  (Button) findViewById(R.id.button4);
        btsettings.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }

            });
        bthistory.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                    
                
                    startActivity(intent);
                }

            });
        bttorecord.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RecordingActivity.class);
                    startActivity(intent);
                }
            });

    }



}


