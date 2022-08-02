package com.dudu.dictation;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tencent.bugly.crashreport.CrashReport;

import de.hdodenhof.circleimageview.CircleImageView;


public class HistoryActivity extends Activity {
    private ArrayList<String> fileList = DirList.getName(new File(Environment.getExternalStorageDirectory() + "/dictation"));
    boolean connected;
    private ListView list;
    ArrayAdapter<String> adapter;
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        requestPermission();
        connected = NetWorkChangeBroadcastReceiver.isNetConnected(this);
        if(connected){
            // 网络正常,做你想做的操作

        }else {
            Toast toast=new Toast(getApplicationContext());

            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

            View view =inflater.inflate(R.layout.my_toast,null);

            ImageView ivToast=view.findViewById(R.id.iv_toast);
            TextView tvToast=view.findViewById(R.id.tv_toast);
            ivToast.setImageResource(R.drawable.warn);
            tvToast.setText("无网络");

            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);

            toast.show();
            //说明当前无网络连接

        }
        CrashReport.initCrashReport(getApplicationContext());   //初始化CrashReport


        list = (ListView) findViewById(R.id.historylist);  //获得控件实例
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


       if(checkPermissionAllGranted(permissions)){
            adapter = new ArrayAdapter<String>(HistoryActivity.this, R.layout.simple_list_item_1, fileList);
            list.setAdapter(adapter);
           if(fileList.size()==0) {
               list.setBackgroundResource(R.drawable.list_back);
           }else{
               list.setBackground(null);
           }}
       else{
           Toast.makeText(HistoryActivity.this, "请授予权限", Toast.LENGTH_SHORT).show();
       }

        refresh.setProgressViewOffset(true,30,180);
        refresh.setDistanceToTriggerSync(40);
        refresh.setColorSchemeColors(Color.parseColor("#2196F3"),Color.GREEN);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (NoDoubleClick.isNotFastClick()) {
                    if (connected) {
                        String fileName = fileList.get(i);
                        Intent intent = new Intent(HistoryActivity.this, PlayActivity.class);
                        intent.putExtra("dataFileName", fileName);
                        startActivity(intent);
                    } else {
                        MyToast();
                    }
                }
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
                if(fileList.size()==0) {
                    list.setBackgroundResource(R.drawable.list_back);
                }else{
                    list.setBackground(null);
                }
                adapter = new ArrayAdapter<String>(HistoryActivity.this, R.layout.simple_list_item_1,fileList);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(NoDoubleClick.isNotFastClick()){
                        if(connected) {
                            String fileName = fileList.get(i);
                            Intent intent = new Intent(HistoryActivity.this, PlayActivity.class);
                            intent.putExtra("dataFileName", fileName);
                            startActivity(intent);
                        }else{
                            MyToast();
                        }
                        }
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
                    if(fileList.size()==0) {
                        list.setBackgroundResource(R.drawable.list_back);
                    }else{
                        list.setBackground(null);
                    }
                    adapter = new ArrayAdapter<String>(HistoryActivity.this, R.layout.simple_list_item_1,fileList);
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if(NoDoubleClick.isNotFastClick()) {
                                if (connected) {
                                    String fileName = fileList.get(i);
                                    Intent intent = new Intent(HistoryActivity.this, PlayActivity.class);
                                    intent.putExtra("dataFileName", fileName);
                                    startActivity(intent);
                                } else {
                                    MyToast();
                                }
                            }
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

    private void MyToast(){
        Toast toast=new Toast(HistoryActivity.this);

        LayoutInflater inflater = LayoutInflater.from(HistoryActivity.this);

        View view =inflater.inflate(R.layout.my_toast,null);

        ImageView ivToast=view.findViewById(R.id.iv_toast);
        TextView tvToast=view.findViewById(R.id.tv_toast);
        ivToast.setImageResource(R.drawable.warn);
        tvToast.setText("无网络\n请检查您的网络连接");

        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);

        toast.show();
        //说明当前无网络连接
    }
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            System.out.println("用户没用此权限");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                System.out.println("用户申请过权限，但是被拒绝了（不是彻底决绝）");
                ActivityCompat.requestPermissions(this,permissions,100);

            } else {
                System.out.println("申请过权限，但是被用户彻底决绝了或是手机不允许有此权限（依然可以在此再申请权限）");
                ActivityCompat.requestPermissions(this,permissions,100);

            }
        }
    }
    /**
     * 检查是否获取所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("用户授予了权限");
                    // 执行相应的操作：
                    fileList = DirList.getName(new File(Environment.getExternalStorageDirectory() + "/dictation"));
                    int listSize=fileList.size();
                    adapter = new ArrayAdapter<String>(HistoryActivity.this, R.layout.simple_list_item_1,fileList);
                    list.setAdapter(adapter);
                    if(fileList.size()==0) {
                        list.setBackgroundResource(R.drawable.list_back);
                    }else{
                        list.setBackground(null);
                    }
                } else {
                    System.out.println("用户没有给予权限");
                    Toast.makeText(HistoryActivity.this, "请授予权限,应用将在三秒后退出", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
                return;
            }
        }
    }
    }



    

