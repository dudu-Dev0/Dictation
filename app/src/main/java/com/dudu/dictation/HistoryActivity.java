package com.dudu.dictation;

import android.Manifest;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tencent.bugly.crashreport.CrashReport;

import de.hdodenhof.circleimageview.CircleImageView;


public class HistoryActivity extends Activity {
    private ArrayList<String> fileList = DirList.getName(new File(Environment.getExternalStorageDirectory() + "/dictation"));
    ArrayAdapter<String> adapter;
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
        SwipeRefreshLayout refresh = (SwipeRefreshLayout)findViewById(R.id.refresh);

        ButtonUtil.addClickScale(toInputWords, 0.8f, 120);
        ButtonUtil.addClickScale(toSettings, 0.8f, 120);
        ButtonUtil.addClickScale(toAbout, 0.8f, 120);

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


        adapter = new ArrayAdapter<String>(HistoryActivity.this, R.layout.simple_list_item_1,fileList);
        list.setAdapter(adapter);       //list数据处理
        refresh.setProgressViewOffset(true,30,180);
        refresh.setDistanceToTriggerSync(40);
        refresh.setColorSchemeColors(Color.parseColor("#2196F3"),Color.GREEN);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String fileName = fileList.get(i);
                Intent intent = new Intent(HistoryActivity.this,PlayActivity.class);
                intent.putExtra("dataFileName",fileName);
                startActivity(intent);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String fileName = fileList.get(i);
                Intent intent = new Intent();
                intent.setClass(HistoryActivity.this,MoreOptionsActivity.class);
                intent.putExtra("dataFileName",fileName);
                startActivityForResult(intent,1);
                overridePendingTransition(0,0);
                return true;
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<String> fileList = DirList.getName(new File(Environment.getExternalStorageDirectory() + "/dictation"));
                adapter = new ArrayAdapter<String>(HistoryActivity.this, R.layout.simple_list_item_1,fileList);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String fileName = fileList.get(i);
                        Intent intent = new Intent(HistoryActivity.this,PlayActivity.class);
                        intent.putExtra("dataFileName",fileName);
                        startActivity(intent);
                    }
                });
                list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String fileName = fileList.get(i);
                        Intent intent = new Intent();
                        intent.setClass(HistoryActivity.this,MoreOptionsActivity.class);
                        intent.putExtra("dataFileName",fileName);
                        startActivityForResult(intent,1);
                        overridePendingTransition(0,0);
                        return true;
                    }
                });

                refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //关闭刷新
                        refresh.setRefreshing(false);
                    }
                },1500);
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK){
                    ListView list = (ListView) findViewById(R.id.historylist);
                    boolean isDeleted = FileUtils.deleteFile(Environment.getExternalStorageDirectory() + "/dictation"+File.separator+data.getStringExtra("dataFileName"));
                    if(isDeleted){Toast.makeText(HistoryActivity.this,data.getStringExtra("data_return"),Toast.LENGTH_SHORT).show();}
                    else{Toast.makeText(HistoryActivity.this,"删除失败",Toast.LENGTH_SHORT).show();}
                    ArrayList<String> fileList = DirList.getName(new File(Environment.getExternalStorageDirectory() + "/dictation"));
                    adapter = new ArrayAdapter<String>(HistoryActivity.this, R.layout.simple_list_item_1,fileList);
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String fileName = fileList.get(i);
                            Intent intent = new Intent(HistoryActivity.this,PlayActivity.class);
                            intent.putExtra("dataFileName",fileName);
                            startActivity(intent);
                        }
                    });
                    list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String fileName = fileList.get(i);
                            Intent intent = new Intent(HistoryActivity.this,MoreOptionsActivity.class);
                            intent.putExtra("dataFileName",fileName);
                            startActivityForResult(intent,1);
                            overridePendingTransition(0,0);
                            return true;
                        }
                    });}
                if (resultCode == RESULT_CANCELED){
                    Toast.makeText(HistoryActivity.this,data.getStringExtra("data_return"),Toast.LENGTH_SHORT).show();
                }
        }
    }


}

    

