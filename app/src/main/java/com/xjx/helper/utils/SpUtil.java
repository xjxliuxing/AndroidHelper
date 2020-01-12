package com.xjx.helper.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.xjx.helper.global.CommonBaseApp;

/**
 * <p>文件描述<p>
 * <p>作者：hp<p>
 * <p>创建时间：2019/1/14<p>
 * <p>更改时间：2019/1/14<p>
 */
public class SpUtil {
    /**
     * Sp 存储的文件名
     */
    public static final String SP_FILE_NAME = "userInfo";

    private static SharedPreferences sp;

    // 获取SP 对象
    private static synchronized SharedPreferences getSp() {

        if (sp == null) {
            sp = CommonBaseApp.getInstance().getApplicationContext().getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }

    /**
     * 放入String类型的数据
     *
     * @param key   存入的key
     * @param value 存入的value
     */
    public static void putString(String key, String value) {
        if (key != null) {
            getSp();
            // 开启编辑器
            SharedPreferences.Editor edit = sp.edit();
            // 存入数据
            if (value == null) {
                value = "";
            }
            edit.putString(key, value);
            // 提交
            edit.commit();
        } else {
            LogUtil.e("存入PutString类型的值为空!");
        }
    }

    /**
     * @param key 获取数据的key
     * @return 获取Stirng类型的数据, 如果获取不到, 则返回null
     */
    public static String getString(String key) {
        String value = null;
        if (!TextUtils.isEmpty(key)) {
            getSp();
            value = sp.getString(key, null);
        }
        return value;
    }

    /**
     * 放入Int类型的数据
     *
     * @param key   存入的key
     * @param value 存入的value
     */
    public static void putInt(String key, int value) {
        if (key != null) {
            getSp();
            // 开启编辑器
            SharedPreferences.Editor edit = sp.edit();
            // 存入数据
            edit.putInt(key, value);
            // 提交
            edit.commit();
        } else {
            LogUtil.e("存入PutInt类型的值为空!");
        }
    }

    /**
     * @param key 获取数据的key
     * @return 获取int类型的数据, 如果获取不到, 则返回-999
     */
    public static int getInt(String key) {
        int value = -999;
        if (!TextUtils.isEmpty(key)) {
            getSp();
            value = sp.getInt(key, -999);
        } else {
            LogUtil.e("取出getInt类型的值为空!");
        }
        return value;
    }

    /**
     * 放入Boolean类型的数据
     *
     * @param key   存入的key
     * @param value 存入的value
     */
    public static void putBoolean(String key, boolean value) {
        if (key != null) {
            getSp();
            // 开启编辑器
            SharedPreferences.Editor edit = sp.edit();
            // 存入数据
            edit.putBoolean(key, value);
            // 提交
            edit.commit();
        } else {
            LogUtil.e("存入PutBoolean类型的值为空!");
        }
    }

    /**
     * @param key 获取数据的key
     * @return 获取Boolean类型的数据, 如果获取不到, 则返回false
     */
    public static boolean getBoolean(String key) {
        boolean value = false;
        if (!TextUtils.isEmpty(key)) {
            getSp();
            value = sp.getBoolean(key, false);
        } else {
            LogUtil.e("取出getBoolean类型的值为空!");
        }
        return value;
    }

    /**
     * 放入long类型的数据
     *
     * @param key   存入的key
     * @param value 存入的value
     */
    public static void putLong(String key, long value) {
        if (key != null) {
            getSp();
            // 开启编辑器
            SharedPreferences.Editor edit = sp.edit();
            // 存入数据
            edit.putLong(key, value);
            // 提交
            edit.commit();
        } else {
            LogUtil.e("存入PutInt类型的值为空!");
        }
    }

    /**
     * @param key 获取数据的key
     * @return 获取long类型的数据, 如果获取不到, 则返回-999
     */
    public static long getLong(String key) {
        long value = -1;
        if (!TextUtils.isEmpty(key)) {
            getSp();
            value = sp.getLong(key, -1);
        } else {
            LogUtil.e("取出getInt类型的值为空!");
        }
        return value;
    }

    /**
     * 放入float类型的数据
     *
     * @param key   存入的key
     * @param value 存入的value
     */
    public static void putFloat(String key, float value) {
        if (key != null) {
            getSp();
            // 开启编辑器
            SharedPreferences.Editor edit = sp.edit();
            // 存入数据
            edit.putFloat(key, value);
            // 提交
            edit.commit();
        } else {
            LogUtil.e("存入PutFloat类型的值为空!");
        }
    }

    /**
     * @param key 获取数据的key
     * @return 获取float类型的数据, 如果获取不到, 则返回0.0f
     */
    public static float getFloat(String key) {
        float value = 0.0f;
        if (!TextUtils.isEmpty(key)) {
            getSp();
            value = sp.getFloat(key, 0.0f);
        } else {
            LogUtil.e("取出getFloat类型的值为空!");
        }
        return value;
    }

    public static long getLongTime(String key) {
        long value = 0;
        if (!TextUtils.isEmpty(key)) {
            getSp();
            value = sp.getLong(key, 0);
        } else {
            LogUtil.e("取出getInt类型的值为空!");
        }
        return value;
    }

    /**
     * 删除指定的Key的Value
     *
     * @param key
     */
    public static void Remove(String key) {
        if (!TextUtils.isEmpty(key)) {
            getSp();
            SharedPreferences.Editor edit = sp.edit();
            edit.remove(key);
            edit.commit();
        }
    }

    /**
     * 清空Sp
     */
    public static void clear() {
        getSp();
        if (sp != null) {
            sp.edit().clear().commit();
            LogUtil.e("数据已经清空！");
        }
    }
}