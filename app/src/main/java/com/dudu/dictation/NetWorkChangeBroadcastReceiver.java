package com.dudu.dictation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NetWorkChangeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (isNetConnected(context)) {
            //网络正常,不执行任何操作.
            return;
        }

        //没有执行return,则说明当前网络异常  3秒钟后跳转到无网络界面
        Log.e("LOG", "------------> No internet connection");
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                //to-do

                Toast toast=new Toast(context.getApplicationContext());

                LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());

                View view =inflater.inflate(R.layout.my_toast,null);

                ImageView ivToast=view.findViewById(R.id.iv_toast);
                TextView tvToast=view.findViewById(R.id.tv_toast);
                ivToast.setImageResource(R.drawable.warn);
                tvToast.setText("无网络");

                toast.setView(view);
                toast.setDuration(Toast.LENGTH_SHORT);


                toast.show();
            }

        },3000);

    }

    /**
     * @return
     */
    public static boolean isNetConnected(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();
        for (int i = 0; i < networkInfos.length; i++) {
            NetworkInfo.State state=networkInfos[i].getState();
            if (NetworkInfo.State.CONNECTED == state) {
                Log.d("LOG","------------> Network is ok");
                return true;
            }
        }
        return false;
    }
}