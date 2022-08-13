package com.dudu.dictation;
import android.view.View;
import android.widget.Toast;


public abstract class NoDoubleClick{
    // 两次点击按钮之间的点击间隔不能少于250毫秒

    public static final int DELAY = 250;
    private static long lastClickTime = 0;
    public static boolean isNotFastClick(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > DELAY) {
            lastClickTime = currentTime;
            return true;
        }else{
            return false;
        }
    }
    
    }

