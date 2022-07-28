package com.dudu.dictation;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.tencent.bugly.crashreport.CrashReport;

import de.hdodenhof.circleimageview.CircleImageView;


public class HistoryActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private File path = new File("/sdcard/dudu/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        CrashReport.initCrashReport(getApplicationContext());   //初始化CrashReport

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
                },1);

        ListView list = (ListView) findViewById(R.id.historylist);  //获得控件实例
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.menu);
        ImageButton btMenu = (ImageButton) findViewById(R.id.btMenu);
        CircleImageView toInputWords = (CircleImageView) findViewById(R.id.toInputWords);
        CircleImageView toSettings = (CircleImageView) findViewById(R.id.toSettings);
        CircleImageView toAbout = (CircleImageView) findViewById(R.id.toAbout);


        btMenu.setOnClickListener(view -> mDrawerLayout.openDrawer(GravityCompat.END));    //为每个按钮重写OnClick方法
        toInputWords.setOnClickListener(view -> {
            Intent intent = new Intent(HistoryActivity.this, SetNameActivity.class);
            startActivity(intent);
        });
        toSettings.setOnClickListener(view -> {
            Intent intent = new Intent(HistoryActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
        toAbout.setOnClickListener(view -> {
            Intent intent = new Intent(HistoryActivity.this,AboutActivity.class);
            startActivity(intent);
        });

        ArrayList<String> fileList = DirList.getName(new File(Environment.getExternalStorageDirectory() + "/dictation"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HistoryActivity.this, R.layout.simple_list_item_1,fileList);
        list.setAdapter(adapter);       //list数据处理

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String fileName = fileList.get(i);
                Intent intent = new Intent(HistoryActivity.this,PlayActivity.class);
                intent.putExtra("dataFileName",fileName);
                startActivity(intent);
            }
        });



    }


}

    

