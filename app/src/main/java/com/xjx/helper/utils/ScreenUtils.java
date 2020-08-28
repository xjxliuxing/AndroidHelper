package com.xjx.helper.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.xjx.helper.global.App;
import com.xjx.helper.global.CommonConstant;

import java.lang.reflect.Method;

public class ScreenUtils {

    private static ScreenUtils screenUtil;
    private static DisplayMetrics metrics;
    private WindowManager windowManager;

    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕高度：包括底部虚拟导航栏的高度
     * @return
     */
    public static int getScreenHeight2(Context context) {
        int dpi = 0;
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;

    }

    /**
     * 判断底部导航栏是否展现
     * @param context
     * @return
     */
    public static boolean isNavigationBarShow(Context context) {
        boolean isShow = false;
        View decorView = ((Activity) context).getWindow().getDecorView().getRootView();
        if (decorView instanceof ViewGroup) {
            View childAt = ((ViewGroup) decorView).getChildAt(0);
            if (childAt != null) {
                final int bottom = childAt.getBottom();
                isShow = bottom < getScreenHeight(context);
            }
        }
        return isShow;
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(@NonNull Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static ScreenUtils getInstance() {
        if (metrics == null) {
            screenUtil = new ScreenUtils();
        }
        return screenUtil;
    }

    private ScreenUtils() {
        //得到屏幕窗口管理器
        if (windowManager == null) {
            windowManager = (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);
        }
        // 屏幕信息（宽度、密度、字体）
        metrics = new DisplayMetrics();
        // 把获取到的信息放入到屏幕信息的类中
        windowManager.getDefaultDisplay().getMetrics(metrics);
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

    public void getScreenInof() {
        if (metrics != null) {
            LogUtil.e("屏幕的信息为: " + metrics.toString());
        }
    }

    /**
     * 获取顶部状态栏的高度
     *
     * @return
     */
    public static int getStatusHeight(@NonNull Activity activity) {
        int statusHeight;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);

                // 存入本地sp中状态栏高度
//                SPUtils.getInstance().put(CommonUser.KEY_STATUS_HEIGHT, statusHeight);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 获取状态栏的高度
     */
    public int getstatusBarHeight() {
        //获取status_bar_height资源的ID
        int statusBarHeight = 0;
        int resourceId = App.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = App.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        if (statusBarHeight > 0) {
            // 把状态栏的高度存入到sp中
//            SPUtils.getInstance().put(CommonConstant.KEY_STATUS_HEIGHT, statusBarHeight);
            LogUtil.e("获取状态栏的高度为：" + statusBarHeight);
        } else {
            LogUtil.e("拿不到状态栏的高度，走的是默认25dp的方法");
            float v = ConvertUtil.toDp(25);
            // 四舍五入取整数
            int round = Math.round(v);
//            SPUtils.getInstance().put(CommonConstant.KEY_STATUS_HEIGHT, round);
        }
        return statusBarHeight;
    }

}
