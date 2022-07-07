package com.dudu.dictation;
import android.view.View;
import android.widget.Toast;


public abstract class NoDoubleClickListener implements View.OnClickListener{
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    protected static final int MIN_CLICK_DELAY_TIME = 1000;
    protected static long lastClickTime;

    public abstract void onNoDoubleClick(View v);

    @Override
    public void onClick(View v) {
        long curClickTime = System.currentTimeMillis();
      
                
        if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间

            
            lastClickTime = curClickTime;
            onNoDoubleClick(v);
        }
   
        }
    
    }

