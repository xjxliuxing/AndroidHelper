package com.xjx.helper.utils;

import android.content.Context;

/**
 * @作者 徐吉星
 * @创建时间 2019/11/20  23:59
 * @更新者 XJX
 * @描述 屏幕相关的工具类
 */
public class ScreenUtil {

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {

        //获取status_bar_height资源的ID
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        if (statusBarHeight <= 0) {
            // 给出一个默认的数据
            statusBarHeight = 48;
        }
        return statusBarHeight;
    }
}
