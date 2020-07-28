package com.xjx.apphelper.utils;

/**
 * Created by Administrator on 2019/5/21.
 */

public class AnimationUtil {

    public static void setXmlAnimation() {
        String a = "<item android:drawable=\"@drawable/icon_tpr_yinfuyou_";
        String b = "android:duration=\"50\"/>";
        String index = "";

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < 100; i++) {
            if (i < 10) {
                index = "0000" + (i);
            } else if (i < 100) {
                index = "000" + (i);
            }
            buffer.append(a + index + "\"" + " " + b);
            buffer.append("\r\n");
        }
        LogUtil.e(buffer.toString());

    }
}
