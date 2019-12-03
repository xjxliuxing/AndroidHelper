package com.xjx.helper.global;

import android.app.Application;
import android.support.annotation.Nullable;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.xjx.helper.BuildConfig;
import com.xjx.helper.utils.CommonConstant;
import com.xjx.helper.utils.LayoutUtil;
import com.xjx.helper.utils.SpUtil;

public class App extends Application {

    private static App mApp;
    public boolean isDebug;
    public float horizontalScaleValue; // 布局宽的缩放比例
    public float verticalScaleValue;   // 布局高的缩放比例

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        isDebug = BuildConfig.DEBUG;
        initApp();
    }

    public static App getInstance() {
        return mApp;
    }

    private void initApp() {
        initLogger();
        getStatusBarHeight();
        initLayout();
    }

    private void initLogger() {

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(0)               // （可选）要显示的方法行数。 默认2
                .methodOffset(0)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                // .logStrategy(customLog)  //（可选）更改要打印的日志策略。 默认LogCat
                .tag("XJX")                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG; // 只有在 Debug模式下才会打印
            }
        });
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        horizontalScaleValue = LayoutUtil.getInstance(this).getHorizontalScaleValue();
        verticalScaleValue = LayoutUtil.getInstance(this).getVerticalScaleValue();
    }

    /**
     * 获取状态栏的高度
     */
    private int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int statusBarHeight = 48;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        if (statusBarHeight > 0) {
            // 把状态栏的高度存入到sp中
            SpUtil.putInt(CommonConstant.STATUS_HEIGHT, statusBarHeight);
        }
        return statusBarHeight;
    }

}
