package com.dudu.dictation;



import android.app.Activity;
import android.app.Application;

import android.os.Handler;

import android.os.Looper;

import android.util.Log;

import android.widget.Toast;

import com.wanjian.cockroach.Cockroach;

/**

 * Created by wanjian on 2017/2/14.

 */

public class App extends Application {

    @Override

    public void onCreate() {


        // handlerException内部建议手动try{ 你的异常处理逻辑 }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException
        super.onCreate();
        Cockroach.install((thread, throwable) -> {

//开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，

//由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，

//所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。

//new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途

            new Handler(Looper.getMainLooper()).post(() -> {

                try {

//建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log

                    Log.e("AndroidRuntime", "--->CockroachException:" + thread);

                    Toast.makeText(App.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();

// throw new RuntimeException("..."+(i++));

                } catch (Throwable e) {

                }

            });

        });

    }

}