package com.dudu.dictation;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
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
import com.wanjian.cockroach.Cockroach;

import de.hdodenhof.circleimageview.CircleImageView;


public class HistoryActivity extends Activity {
    private ArrayList<Dictation> fileList;// = DirList.getName(new File(Environment.getExternalStorageDirectory() + "/dictation"));
    boolean connected;
    private ListView list;
    DictationAdapter adapter;
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.RECORD_AUDIO};
    private int PERMISSIONS_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

      Cockroach.install((thread, throwable) -> {

//开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，

//由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，

//所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。

//new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途

            new Handler(Looper.getMainLooper()).post(() -> {

                try {

//建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log

                    Log.e("AndroidRuntime", "--->CockroachException:" + thread);

                    Toast.makeText(HistoryActivity.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();

// throw new RuntimeException("..."+(i++));

                } catch (Throwable e) {

                }

            });

        });
        boolean isPermissionAllGranted = checkPermissionAllGranted(permissions);
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
            tvToast.setText("无网络\n请检查您的网络连接");

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
            Intent intent = new Intent(HistoryActivity.this, ChooseWaysToInputActivity.class);
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

        fileList = DirList.getName(new File(this.getExternalFilesDir("dictation").getPath()));

       if(isPermissionAllGranted){
    if(fileList!=null) {
        if (fileList.size() == 0) {
            list.setBackgroundResource(R.drawable.list_back);
        } else {
            list.setBackground(null);
        }
        initList();
    }
       }
       ActivityCompat.requestPermissions(HistoryActivity.this,permissions,PERMISSIONS_REQUEST_CODE);

        refresh.setProgressViewOffset(true,30,180);
        refresh.setDistanceToTriggerSync(40);
        refresh.setColorSchemeColors(Color.parseColor("#2196F3"),Color.GREEN);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fileList = DirList.getName(new File(HistoryActivity.this.getExternalFilesDir("dictation").getPath()));
                if(fileList.size()==0) {
                    list.setBackgroundResource(R.drawable.list_back);
                }else{
                    list.setBackground(null);
                }
                initList();
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
                    boolean isDeleted = false;
                    int imageId = data.getIntExtra("imageId",1145141919);
                    if (imageId==R.drawable.text) {isDeleted = FileUtils.deleteFile(this.getExternalFilesDir("dictation")+ File.separator+data.getStringExtra("dataFileName"));}
                    if (imageId==R.drawable.recorded){isDeleted = FileUtils.deleteDir(this.getExternalFilesDir("dictation")+ File.separator+data.getStringExtra("dataFileName"));}
                    if(isDeleted){Toast.makeText(HistoryActivity.this,data.getStringExtra("data_return"),Toast.LENGTH_SHORT).show();}
                    else{Toast.makeText(HistoryActivity.this,"删除失败",Toast.LENGTH_SHORT).show();}
                    fileList = DirList.getName(new File(this.getExternalFilesDir("dictation").getPath()));
                    if(fileList.size()==0) {
                        list.setBackgroundResource(R.drawable.list_back);
                    }else{
                        list.setBackground(null);
                    }
                    initList();
                }
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
                boolean isAllGranted = true;

                for(int grant:grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        isAllGranted = false;
                        break;
                    }
                }
                if(isAllGranted){
                    if(fileList!=null) {
                        if (fileList.size() == 0) {
                            list.setBackgroundResource(R.drawable.list_back);
                        } else {
                            list.setBackground(null);
                        }
                        initList();
                    }
                }else{
                    Toast.makeText(HistoryActivity.this, "需要授予全部权限才能正常使用", Toast.LENGTH_SHORT).show();
                }

                }

            }
        }

    private void initList(){
        fileList = DirList.getName(new File(this.getExternalFilesDir("dictation").getPath()));
        adapter = new DictationAdapter(HistoryActivity.this, R.layout.simple_list_item_1, fileList);
        list.setAdapter(adapter);
        if(fileList!=null) {
            if (fileList.size() == 0) {
                list.setBackgroundResource(R.drawable.list_back);
            } else {
                list.setBackground(null);
            }
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(NoDoubleClick.isNotFastClick()) {
                    Dictation dictation = fileList.get(i);
                    String fileName = dictation.getName();
                    int imageId = dictation.getImageId();
                    Intent intent = new Intent(HistoryActivity.this, PlayActivity.class);
                    if (imageId==R.drawable.text) {
                        if (connected) {
                                intent.putExtra("wayType", "text");
                                intent.putExtra("dataFileName", fileName);
                            startActivity(intent);
                        } else {
                            MyToast();
                        }

                    }
                    if (imageId == R.drawable.recorded) {
                        intent.putExtra("wayType", "record");
                        intent.putExtra("dataDirName", HistoryActivity.this.getExternalFilesDir("dictation").getPath() + File.separator + fileName);
                        startActivity(intent);
                    }
                }
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dictation dictation = fileList.get(i);
                String fileName = dictation.getName();
                int imageId = dictation.getImageId();
                Intent intent = new Intent(HistoryActivity.this,MoreOptionsActivity.class);
                intent.putExtra("dataFileName",fileName);
                intent.putExtra("imageId",imageId);
                startActivityForResult(intent,1);
                overridePendingTransition(0,0);
                return true;
            }
        });
    }

    }



    

