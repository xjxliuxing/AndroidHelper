package com.xjx.helper.utils;

import com.orhanobut.logger.Logger;
import com.xjx.helper.BuildConfig;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Log的工具类<p>
 * <p>作者：hp<p>
 * <p>创建时间：2019/1/14<p>
 * <p>更改时间：2019/1/14<p>
 */
public class LogUtil {

    private static boolean isDebug = BuildConfig.DEBUG;

    /**
     * Created by XTF on 2017/5/5.
     * Log的管理工具类
     */

    /**
     * 默认 e 级别的 log
     *
     * @param message 需要打印的log内容
     */
    public static void e(String message) {
        if (!isDebug) {
            return;
        }
        Logger.e(message);
    }

    /**
     * 指定 tag 的 e 级别log
     *
     * @param tag     指定的TAG
     * @param message 需要打印的log内容
     */
    public static void e(String tag, String message) {
        if (!isDebug) {
            return;
        }
        Logger.t(tag).e(message);
    }

    /**
     * 默认 i 级别的 log
     *
     * @param message 需要打印的log内容
     */
    public static void i(String message) {
        if (!isDebug) {
            return;
        }
        Logger.i(message);
    }

    /**
     * 指定 tag 的 i 级别log
     *
     * @param tag     指定的TAG
     * @param message 需要打印的log内容
     */
    public static void i(String tag, String message) {
        if (!isDebug) {
            return;
        }
        Logger.t(tag).i(message);
    }

    public static void d(String message) {
        if (!isDebug) {
            return;
        }
        Logger.d(message);
    }

    public static void d(String tag, String message) {
        if (!isDebug) {
            return;
        }
        Logger.t(tag).d(message);
    }

    public static void List(List list) {
        if (!isDebug) {
            return;
        }
        Logger.d(list);
    }

    public static void List(String tag, List list) {
        if (!isDebug) {
            return;
        }
        Logger.t(tag).d(list);
    }

    public static void Set(Set set) {
        if (!isDebug) {
            return;
        }
        Logger.d(set);
    }

    public static void Set(String tag, Set set) {
        if (!isDebug) {
            return;
        }
        Logger.t(tag).d(set);
    }

    public static void Array(Array array) {
        if (!isDebug) {
            return;
        }
        Logger.d(array);
    }

    public static void Array(String tag, Array array) {
        if (!isDebug) {
            return;
        }
        Logger.t(tag).d(array);
    }

    public static void Json(String json) {
        if (!isDebug) {
            return;
        }
        Logger.json(json);
    }

    public static void Json(String tag, String json) {
        if (!isDebug) {
            return;
        }
        Logger.t(tag).json(json);
    }

    public static void Map(Map map) {
        if (!isDebug) {
            return;
        }
        Logger.d(map);
    }

    public static void Map(String tag, Map map) {
        if (!isDebug) {
            return;
        }
        Logger.t(tag).d(map);
    }

    public static void Xml(String xml) {
        if (!isDebug) {
            return;
        }
        Logger.xml(xml);
    }

    public static void Xml(String tag, String xml) {
        if (!isDebug) {
            return;
        }
        Logger.t(tag).xml(xml);
    }
}

