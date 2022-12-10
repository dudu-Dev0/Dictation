package com.xtc.shareapi.share.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.xtc.shareapi.share.constant.OpenApiConstant;
import java.io.ByteArrayOutputStream;

/* loaded from: share.aar:classes.jar:com/xtc/shareapi/share/utils/BitmapUtil.class */
public class BitmapUtil {
    private static final String TAG = OpenApiConstant.TAG + BitmapUtil.class.getSimpleName();
    public static final int THUMB_LENGTH = 300;
    private static final int THUMB_SIZE = 14;

    public static byte[] bitmapToByteArray(Bitmap drawable) {
        byte[] bitmapData = new byte[0];
        try {
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();
            drawable.compress(Bitmap.CompressFormat.WEBP, 10, var2);
            bitmapData = var2.toByteArray();
        } catch (Exception var3) {
            Log.e(TAG, "bitmapToByteArray exception:" + var3.getMessage());
        }
        return bitmapData;
    }

    public static Bitmap scaleIcon(Context context, Bitmap src) {
        int size = dpToPx(context, 14.0f);
        Bitmap bitmap = Bitmap.createScaledBitmap(src, size, size, true);
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(output);
        canvas.drawColor(Color.parseColor("#000000"));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        return output;
    }

    private static int dpToPx(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dpValue * scale) + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics dm = new DisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(dm);
        }
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics dm = new DisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(dm);
        }
        return dm.heightPixels;
    }
}