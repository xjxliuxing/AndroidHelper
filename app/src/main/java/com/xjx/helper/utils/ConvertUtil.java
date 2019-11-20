package com.xjx.helper.utils;

import android.content.Context;

/**
 * @作者 徐吉星
 * @创建时间 2019/11/21  2:10
 * @更新者 XJX
 * @描述 转换工具类
 */
public class ConvertUtil {

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
