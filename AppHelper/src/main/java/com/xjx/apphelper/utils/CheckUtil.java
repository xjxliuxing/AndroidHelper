package com.xjx.apphelper.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by Administrator on 2019/4/15.
 * 检测工具类
 */
@SuppressLint("NewApi")
public class CheckUtil {

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

}
