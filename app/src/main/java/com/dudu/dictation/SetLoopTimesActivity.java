package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SetLoopTimesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_loop_times);

        RadioGroup loopTimeRadioGroup = (RadioGroup) findViewById(R.id.loopTimeRadioGroup);
        RadioButton once = (RadioButton) findViewById(R.id.loopOnce);
        RadioButton twice = (RadioButton) findViewById(R.id.loopTwice);
        RadioButton threeTimes = (RadioButton) findViewById(R.id.loopThreeTimes);

        SharedPreferences getSettings = getSharedPreferences("settings", MODE_PRIVATE);
        int loopTimes = getSettings.getInt("loopTimes", 1);
        if (loopTimes==1) {
            once.setChecked(true);
        }if (loopTimes==2) {
            twice.setChecked(true);
        }if (loopTimes==3) {
            threeTimes.setChecked(true);
        }

        loopTimeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Intent intent = new Intent();
                if(checkedId==once.getId()){
                    intent.putExtra("loopTimes",1);
                    setResult(RESULT_OK,intent);
                    finish();
                }if(checkedId==twice.getId()){
                    intent.putExtra("loopTimes",2);
                    setResult(RESULT_OK,intent);
                    finish();
                }if(checkedId==threeTimes.getId()){
                    intent.putExtra("loopTimes",3);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });


    }
}
