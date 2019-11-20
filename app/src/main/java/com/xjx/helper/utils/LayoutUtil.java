package com.xjx.helper.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @作者 徐吉星
 * @创建时间 2019/11/20  23:47
 * @更新者 XJX
 * @描述 为了适配布局创建的工具类
 */
public class LayoutUtil {

    private Context mContext;

    // UI设计图的配置信息
    public static final float STANARD_WIDTH = 1080f;
    public static final float STANARD_HEIGHT = 1920f;

    // 设备实际的宽高
    public static float displayMetricsWidth;
    public static float displayMetricsHeight;

    private static LayoutUtil util;

    public static LayoutUtil getInstance(Context context) {
        if (util == null) {
            util = new LayoutUtil(context);
        }
        return util;
    }

    private LayoutUtil(Context context) {
        this.mContext = context;

        // 获取屏幕的实际宽高
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);

        // 获取状态栏
        int statusBarHeight = SpUtil.getInt(CommonConstant.STATUS_HEIGHT);

        // 获取屏幕方向
        if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
            // 横屏
            this.displayMetricsWidth = (float) displayMetrics.heightPixels;
            this.displayMetricsHeight = (float) displayMetrics.widthPixels - statusBarHeight;
        } else {
            // 竖屏
            this.displayMetricsWidth = (float) displayMetrics.widthPixels;
            this.displayMetricsHeight = (float) displayMetrics.heightPixels - statusBarHeight;
        }
    }

    /**
     * @return 获取横向的缩放比值
     */
    public float getHorizontalScaleValue() {
        return (float) (this.displayMetricsWidth / STANARD_WIDTH);
    }

    /**
     * @return 获取竖向的缩放比值
     */
    public float getVerticalScaleValue() {
        return (float) (this.displayMetricsHeight / (STANARD_HEIGHT - SpUtil.getInt(CommonConstant.STATUS_HEIGHT)));
    }

}
