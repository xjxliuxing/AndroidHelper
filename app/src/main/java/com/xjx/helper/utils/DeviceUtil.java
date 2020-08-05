package com.xjx.helper.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

/**
 * 跟设备相关的工具类
 */
public class DeviceUtil {

    private static String FILE_NAME = "device.text";

    /**
     * @return 获取设备的唯一id
     */
    @SuppressLint("HardwareIds")
    private String getAndroidId(Context context) {
        try {
            // 获取android系统的id
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            LogUtil.e("获取设备唯一id失败：" + e.getMessage());
        }
        return "";
    }

    /**
     * @return 获取随机数
     */
    private String getRandom() {
        long millis = System.currentTimeMillis();
        return String.valueOf(millis);
    }

    /**
     * @return 获取设备的唯一id
     */
    public String getDeviceId(Context context) {
        String devieceId = "";
        // 首先获取androidId
        String androidId = getAndroidId(context);
        if (!TextUtils.isEmpty(androidId)) {
            devieceId = androidId;

            saveContent(context, devieceId);
        } else {
            // 如果获取不到就去获取随机数
            String random = getRandom();
            if (TextUtils.isEmpty(random)) {
                devieceId = random;
            } else {

            }
        }
        return devieceId;
    }

    /**
     * 把数据存储到sd卡中
     */
    private void saveContent(Context context, String devieceId) {
        // 获取的是设备的id
        if (!TextUtils.isEmpty(devieceId)) {
            // 保存到sd卡中
            FileUtil.getInstance().saveToSdContent(FILE_NAME, devieceId);
            // 保存到App中去
            FileUtil.getInstance().saveApp(context, FILE_NAME, devieceId);
        }
    }

    /**
     * 获取存储到本地的device字段
     */
    public String getSaveDeviceId() {
        String result = "";
        result = FileUtil.getInstance().getSdContent(FILE_NAME);
        LogUtil.e("sdContent:" + result);
        if (TextUtils.isEmpty(result)) {
            result = FileUtil.getInstance().getAppContent(FILE_NAME);
            LogUtil.e("appContent:" + result);
        }
        return result;
    }

}
