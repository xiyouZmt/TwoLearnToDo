package com.example.xiyou.twolearntodo.utils;

import android.content.Context;

/**
 * Created by maple on 2016/7/4.
 */
public class DisplayUtils {

    //将dp转化为像素
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale);
    }

    //像素转化为dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale);
    }

    //计算多少个菜单每行
    public static int colums(Context context) {
        //每个菜单间隔7，图片宽度为80 左右间隔8  每个菜单算下来 100*100

        return context.getResources().getDisplayMetrics().widthPixels / dip2px(context,80);

    }
}
