package com.dudu.dictation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class AboutActivity extends AppCompatActivity {
    private int clickCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView easterEgg = findViewById(R.id.easter_egg);

        easterEgg.setOnClickListener(view -> {
            clickCount++;
            if(clickCount>=50){
                easterEgg.setOnLongClickListener(view1 -> {
                    Intent intent = new Intent(AboutActivity.this,EasterEggActivity.class);
                    startActivity(intent);
                    return false;
                });
            }

        });
    }
}