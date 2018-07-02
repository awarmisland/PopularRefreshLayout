package com.awarmisland.android.popularrefreshlayout.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.awarmisland.android.popularrefreshlayout.Config;

import java.util.Timer;

/**
 * Created by Administrator on 2016/9/12.
 */
public class DisplayUtil {
    private static Context mContext;
    
    private static  Timer timer;
    static {
        mContext = Config.mContext;
    }

    /**
     * 获取屏幕高宽
     * @return
     */
    public static int[] getScreenWidth_height() {
        int[] screenSize = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        screenSize[0] = dm.widthPixels;
        screenSize[1] = dm.heightPixels;
        return screenSize;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    
    public static float limitValue(float a, float b) {
        float valve = 0;
        final float min = Math.min(a, b);
        final float max = Math.max(a, b);
        valve = valve > min ? valve : min;
        valve = valve < max ? valve : max;
        return valve;
    }
}
