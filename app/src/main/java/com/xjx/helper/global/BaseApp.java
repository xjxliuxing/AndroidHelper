package com.xjx.helper.global;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.xjx.helper.R;
import com.xjx.helper.ScreenUtil;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.VersionUtil;
import com.xjx.helper.utils.layout.LayoutUtil;

import java.util.Locale;

/**
 * 基类的Application，使用的时候，需要注意以下事项：
 * 1：必须设置debug模式,否则就会导致很多东西无法使用，例如，log会一直打印
 */
public class BaseApp extends Application {

    private static BaseApp mApp;
    private boolean isDebug = true; // debug类型，默认是false类型
    private String mLogTag = "XJX";// 默认log的tag标记
    public float horizontalScaleValue; // 布局宽的缩放比例
    public float verticalScaleValue;   // 布局高的缩放比例

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initApp();
    }

    public static BaseApp getInstance() {
        return mApp;
    }

    public Context getContext() {
        return getApplicationContext();
    }

    private void initApp() {
        initLogger();
        initLayout();
        initPhone();
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                //开始设置全局的基本参数（可以被下面的DefaultRefreshHeaderCreator覆盖）
                layout.setReboundDuration(1000);
//                layout.setReboundInterpolator(new DropBounceInterpolator());
                layout.setFooterHeight(100);
                layout.setDisableContentWhenLoading(false);
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);

//                srlEnablePreviewInEditMode
            }
        });

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    /**
     * 手机信息相关
     */
    private void initPhone() {
        // 屏幕相关
        ScreenUtil screenUtil = ScreenUtil.getInstance();
        screenUtil.getstatusBarHeight();
        screenUtil.getScreenInof();

        // version
        VersionUtil versionUtil = VersionUtil.getInstance();
        String versionName = versionUtil.getVersionName();
        int versionCode = versionUtil.getVersionCode();
        String brand = versionUtil.getBrand();
        String model = versionUtil.getModel();
        String sdk = versionUtil.getSdk();

        String macAddress = versionUtil.getMacAddress();

        LogUtil.e(String.format(Locale.CHINA,
                "App:SDK   : 【 %s 】" +
                        "%nApp:版本  : 【 %s 】" +
                        "%nApp版本号 : 【 %s 】" +
                        "%n设备唯一码: 【 %s 】" +
                        "%n手机品牌  : 【 %s 】" +
                        "%n手机型号  : 【 %s 】 ",
                sdk,
                versionName,
                versionCode,
                macAddress,
                brand,
                model));
    }

    private void initLogger() {

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(0)               // （可选）要显示的方法行数。 默认2
                .methodOffset(0)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                // .logStrategy(customLog)  //（可选）更改要打印的日志策略。 默认LogCat
                .tag(getLogTag())                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return isDebug(); // 只有在 Debug模式下才会打印
            }
        });
    }

    /**
     * 设置debug模式
     *
     * @param debug
     */
    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    /**
     * @return 获取是否是debug的模式
     */
    public boolean isDebug() {
        return isDebug;
    }

    /**
     * @return 获取全局log的tag
     */
    public String getLogTag() {
        return mLogTag;
    }

    /**
     * 设置全局log的tag
     *
     * @param logTag
     */
    public void setLogTag(String logTag) {
        this.mLogTag = mLogTag;
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        horizontalScaleValue = LayoutUtil.getInstance(this).getHorizontalScaleValue();
        verticalScaleValue = LayoutUtil.getInstance(this).getVerticalScaleValue();
    }

}
