package com.dudu.dictation;

import android.view.MotionEvent;
import android.view.View;

public class ButtonUtil {

    /**
     * 添加点击缩放效果
     * 调用：ButtonUtils.addClickScale(btn, 0.9f, 150);
     * @param scale    缩放比例  默认0.9f
     * @param duration 动画时长  默认150
     */
    public static void addClickScale(View view, Float scale, Integer duration) {
        if (view != null) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.animate().scaleX(scale).scaleY(scale).setDuration(duration).start();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.animate().scaleX(1f).scaleY(1f).setDuration(duration).start();
                        // 抬手的时候，回调OnClickListener点击事件
                        //v.performClick();
                    } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                        v.animate().scaleX(1f).scaleY(1f).setDuration(duration).start();
                    }
                    return false;
                }
            });
        }
    }

}
