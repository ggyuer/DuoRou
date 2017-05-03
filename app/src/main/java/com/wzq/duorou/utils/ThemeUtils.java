package com.wzq.duorou.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by wzq on 2017/3/15.
 */
public class ThemeUtils {
    public static int getThemeColor(Context context, int attrRes) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{attrRes});
        int color = typedArray.getColor(0, 0xffffff);
        typedArray.recycle();
        return color;
    }
}
