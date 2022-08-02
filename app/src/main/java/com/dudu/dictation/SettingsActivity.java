package com.dudu.dictation;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;


import com.suke.widget.SwitchButton;
import android.widget.LinearLayout;



public class SettingsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SwitchButton autoDel = (SwitchButton) findViewById(R.id.autoDel);
        SwitchButton scrambleTheOrder = (SwitchButton) findViewById(R.id.scrambleTheOrder);
        Button btshare = findViewById(R.id.btshare);
        LinearLayout toSetLoopTimes = findViewById(R.id.toSetLoopTimes);
        LinearLayout toSetBroadcastInterval = findViewById(R.id.toSetBroadcastInterval);

        SharedPreferences getSPData = getSharedPreferences("settings",MODE_PRIVATE);
        boolean settingsOfAutoDel = getSPData.getBoolean("autoDel",false);
        boolean settingsOfScrambleTheOrder = getSPData.getBoolean("scrambleTheOrder",true);
        autoDel.setChecked(settingsOfAutoDel);
        scrambleTheOrder.setChecked(settingsOfScrambleTheOrder);



        autoDel.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SharedPreferences.Editor setter = getSharedPreferences("settings",MODE_PRIVATE).edit();
                setter.putBoolean("autoDel",isChecked);
                setter.apply();
            }
        });
        scrambleTheOrder.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SharedPreferences.Editor setter = getSharedPreferences("settings",MODE_PRIVATE).edit();
                setter.putBoolean("scrambleTheOrder",isChecked);
                setter.apply();
            }
        });
        toSetLoopTimes.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this,SetLoopTimesActivity.class);
            startActivityForResult(intent,2);
        });
        toSetBroadcastInterval.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this,SetBroadcastIntervalActivity.class);
            startActivityForResult(intent,3);
        });



    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        SharedPreferences.Editor SPEditor = getSharedPreferences("settings",MODE_PRIVATE).edit();
        switch(requestCode){
            case 2:
                int loopTimes = data.getIntExtra("loopTimes",1);
                SPEditor.putInt("loopTimes",loopTimes);
                SPEditor.apply();
            case 3:
                int broadcastInterval = data.getIntExtra("broadcastInterval",3000);
                SPEditor.putInt("broadcastInterval",broadcastInterval);
                SPEditor.apply();
        }
    }


}
