package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class ChooseWaysToInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ways_to_input);

        LinearLayout toRecord = findViewById(R.id.toRecord);
        LinearLayout toInput = findViewById(R.id.toInput);

        ButtonUtil.addClickScale(toInput, 0.9f, 120);
        ButtonUtil.addClickScale(toRecord, 0.9f, 120);

        toRecord.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseWaysToInputActivity.this,SetNameActivity.class);
            intent.putExtra("wayType","record");
            startActivity(intent);
            finish();
        });
        toInput.setOnClickListener(view -> {
            Intent intent = new Intent(ChooseWaysToInputActivity.this,SetNameActivity.class);
            intent.putExtra("wayType","input");
            startActivity(intent);
            finish();
        });
    }
}