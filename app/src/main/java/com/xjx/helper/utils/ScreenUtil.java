package com.xjx.helper.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.xjx.helper.global.CommonBaseApp;
import com.xjx.helper.global.CommonConstant;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.SpUtil;

/**
 * <p>文件描述<p>
 * <p>作者：hp<p>
 * <p>创建时间：2019/1/17<p>
 * <p>更改时间：2019/1/17<p>
 */
public class ScreenUtil {

    private static ScreenUtil screenUtil;
    private final DisplayMetrics metrics;

    public static ScreenUtil getInstance() {
        if (screenUtil == null) {
            screenUtil = new ScreenUtil();
        }
        return screenUtil;
    }

    public ScreenUtil() {
        //得到屏幕窗口管理器
        WindowManager windowManager = (WindowManager) CommonBaseApp.getInstance().getContext().getSystemService(Context.WINDOW_SERVICE);
        // 屏幕信息（宽度、密度、字体）
        metrics = new DisplayMetrics();
        // 把获取到的信息放入到屏幕信息的类中
        windowManager.getDefaultDisplay().getMetrics(metrics);
    }

    /**
     * 获取屏幕信息
     */
    public void getScreenInof() {
        if (metrics != null) {
            LogUtil.e("屏幕的信息为: " + metrics.toString());
        }
    }

    /**
     * 获取屏幕的宽
     *
     * @return
     */
    public int getScreenWidth() {
        if (metrics == null) {
            return 0;
        } else {
            return metrics.widthPixels;
        }
    }

    /**
     * 获取屏幕的高
     *
     * @return
     */
    public int getScreenHeight() {
        if (metrics == null) {
            return 0;
        } else {
            return metrics.heightPixels;
        }
    }

    /**
     * 获取屏幕的密度
     * <p>
     * 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     *
     * @return
     */
    public float getScreenDensity() {
        if (metrics == null) {
            return 0;
        } else {
            return metrics.density;
        }
    }

    /**
     * 获取屏幕的密度
     * <p>
     * 屏幕密度（每寸像素：120/160/240/320）
     *
     * @return
     */
    public int getScreenDensityDpi() {
        if (metrics == null) {
            return 0;
        } else {
            return metrics.densityDpi;
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @param context Context
     * @return 屏幕宽度（px）
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕高度
     *
     * @param context Context
     * @return 屏幕高度（px）
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) CommonBaseApp.getInstance().getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获取状态栏的高度
     */
    public int getstatusBarHeight() {
        //获取status_bar_height资源的ID
        int statusBarHeight = 0;
        int resourceId = CommonBaseApp.getInstance().getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = CommonBaseApp.getInstance().getContext().getResources().getDimensionPixelSize(resourceId);
        }
        if (statusBarHeight > 0) {
            // 把状态栏的高度存入到sp中
            SpUtil.putInt(CommonConstant.STATUS_HEIGHT, statusBarHeight);
        }
        return statusBarHeight;
    }
}
