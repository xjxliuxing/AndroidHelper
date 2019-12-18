package com.xjx.helper.utils.views;

import android.text.TextUtils;
import android.widget.TextView;

import com.xjx.helper.utils.LogUtil;

/**
 * Created by spc on 2017/4/13.
 */

public class TextViewUtils {

    /**
     * 给TextView设置任意类型的数据
     *
     * @param textView textView对象
     * @param object   任意对象
     */
    public static void setText(TextView textView, Object object) {
        if (textView != null) {
            if (object != null) {
                String s = String.valueOf(object);
                textView.setText(s);
            } else {
                LogUtil.e("textView的值为空！");
            }
        } else {
            LogUtil.e("textView为空！");
        }
    }

    /**
     * 给TextView设置String类型的数据
     *
     * @param textView
     * @param value
     */
    public static void setText(TextView textView, String value) {
        if (textView != null) {
            if (!TextUtils.isEmpty(value)) {
                textView.setText(value);
            } else {
                LogUtil.e("textView的值为空！");
            }
        } else {
            LogUtil.e("textView为空！");
        }
    }
}
