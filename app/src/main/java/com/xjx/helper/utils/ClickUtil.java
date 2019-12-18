package com.xjx.helper.utils;

/**
 * Created by Administrator on 2019/5/13.
 * 防止按钮快速点击的工具，只要大于1秒 就能点击，否则就不能点击
 */

public class ClickUtil {

    private static long startTime;
    private static long endtTime;

    public static boolean checkClickTime() {
        boolean canClick = false;

        endtTime = System.currentTimeMillis();

        long timeD = endtTime - startTime;
        if (0 < timeD && timeD < 500) {
            LogUtil.e("点击速度过了，大侠歇息一会吧！");
            canClick = true;
        }
        startTime = endtTime;

        return canClick;
    }
}
