package com.dudu.dictation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.io.File;
import android.widget.ListView;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.view.View;

import android.content.Intent;

public class HistoryActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ListView list = (ListView) findViewById(R.id.historylist);
        File path = new File("/sdcard/dudu/");
        final ArrayList<String> nameList = DirList.getName(path);
        System.out.println(nameList);
        if(nameList.size()!=0){}
        else{nameList.add(0,"没有听写记录哦");}
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.simple_list_item_1,nameList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                String name = nameList.get(position);
                String dirString = "/sdcard/dudu/"+name;
                Intent intent = new Intent(HistoryActivity.this,PlayActivity.class);
                intent.putExtra("dir",dirString);
                startActivity(intent);
                finish();
            }
            });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent,View view,int position,long id){
                    String name = nameList.get(position);
                    String dirString = "/sdcard/dudu/"+name;
                    Intent intent = new Intent(HistoryActivity.this,PlayActivity.class);
                    intent.putExtra("dir",dirString);
                    startActivity(intent);
                    finish();
                    return true;
                }
            });
    }
    
}
