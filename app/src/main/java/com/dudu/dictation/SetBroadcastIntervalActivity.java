package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SetBroadcastIntervalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_broadcast_interval);

        RadioGroup broadcastIntervalRadioGroup = (RadioGroup) findViewById(R.id.broadcastIntervalRadioGroup);
        RadioButton set2000ms = (RadioButton) findViewById(R.id.set2000ms);
        RadioButton set3000ms = (RadioButton) findViewById(R.id.set3000ms);
        RadioButton set4000ms = (RadioButton) findViewById(R.id.set4000ms);
        RadioButton set5000ms = (RadioButton) findViewById(R.id.set5000ms);

        SharedPreferences getSettings = getSharedPreferences("settings", MODE_PRIVATE);
        int broadcastInterval = getSettings.getInt("broadcastInterval", 3000);
        if (broadcastInterval==2000) {
            set2000ms.setChecked(true);
        }if (broadcastInterval==3000) {
            set3000ms.setChecked(true);
        }if (broadcastInterval==4000) {
            set4000ms.setChecked(true);
        }if (broadcastInterval==5000) {
            set5000ms.setChecked(true);
        }

        broadcastIntervalRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent intent = new Intent();
                if(checkedId==set2000ms.getId()){
                    intent.putExtra("broadcastInterval",2000);
                    setResult(RESULT_OK,intent);
                    finish();
                }if(checkedId==set3000ms.getId()){
                    intent.putExtra("broadcastInterval",3000);
                    setResult(RESULT_OK,intent);
                    finish();
                }if(checkedId==set4000ms.getId()){
                    intent.putExtra("broadcastInterval",4000);
                    setResult(RESULT_OK,intent);
                    finish();
                }if(checkedId==set5000ms.getId()){
                    intent.putExtra("broadcastInterval",5000);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });


    }
}