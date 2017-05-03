package com.wzq.duorou.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 尺寸工具箱，提供与Android尺寸相关的工具方法
 */
public class DimenUtils {
    /**
     * dp单位转换为px
     *
     * @param context 上下文，需要通过上下文获取到当前屏幕的像素密度
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) (dpValue * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * px单位转换为dp
     *
     * @param context 上下文，需要通过上下文获取到当前屏幕的像素密度
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        return (int) (pxValue / (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /** 获取手机的密度*/
    public static float getDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }

}