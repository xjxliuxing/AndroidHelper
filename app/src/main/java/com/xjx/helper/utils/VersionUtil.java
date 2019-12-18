package com.xjx.helper.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.xjx.helper.global.BaseApp;

import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * Created by Administrator on 2019/4/15.
 * App版本相关的工具类
 */

public class VersionUtil {

    private static VersionUtil appVersionUtil;
    private Context context;

    public static VersionUtil getInstance() {
        appVersionUtil = new VersionUtil();
        return appVersionUtil;
    }

    private VersionUtil() {
        context = BaseApp.getInstance().getContext();
    }

    /**
     * @return 获取版本号
     */
    public int getVersionCode() {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * @return 获取版本名字
     */
    public String getVersionName() {
        String versionName = "";
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取设备的唯一id值
     *
     * @return
     */
    public String getDeviceId() {
        // 获取android的id值
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        LogUtil.e("设备的android——id为：" + android_id);

        // 设备的唯一标识。由设备的多个信息拼接合成
        String fingerprint = Build.FINGERPRINT;
        LogUtil.e("设备的唯一识别码为：" + fingerprint);

        String value = android_id + fingerprint;

        String md5 = MD5Utils.MD5(value);
        LogUtil.e("设备的唯一识别码的Md5为：" + md5);
        return md5;
    }

    /**
     * @return 获取手机品牌
     */
    public String getBrand() {
        String brand = Build.BRAND;
        return brand;
    }

    /**
     * @return 获取型号
     */
    public String getModel() {
        String model = Build.MODEL;
        return model;
    }

    public String getSdk() {
        int sdkInt = Build.VERSION.SDK_INT;

        String version = "";

        if (sdkInt < 21) {
            version = "当前版本小于5.0，软件暂不支持！";
        } else {

            switch (sdkInt) {
                case 21:
                    version = "5.0";
                    break;
                case 22:
                    version = "5.1";
                    break;
                case 23:
                    version = "6.0";
                    break;
                case 24:
                    version = "7.0";
                    break;
                case 25:
                    version = "7.1";
                    break;
                case 26:
                    version = "8.0";
                    break;
                case 27:
                    version = "8.1";
                    break;
                case 28:
                    version = "9.0";
                    break;
                case 29:
                    version = "9.+";
                    break;
            }
        }
        return version;
    }

    /**
     * @return 通过反射的方式去获取Mac网卡的地址值
     */
    public String getMacAddress() {

        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:02";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();

            if (!TextUtils.isEmpty(macAddress)) {
                LogUtil.e("原始的mac地址为：" + macAddress);
                macAddress = MD5Utils.MD5(macAddress);
            }

        } catch (SocketException e) {
            e.printStackTrace();
            return "02:00:00:00:00:02";
        }

        if (TextUtils.equals(macAddress, "02:00:00:00:00:02")) {
            macAddress = getDeviceId();
        }
        return macAddress;
    }

}
