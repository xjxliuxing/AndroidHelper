package com.xjx.helper.utils.views;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.TextView;

import com.xjx.helper.utils.LogUtil;

/**
 * Created by spc on 2017/4/13.
 */

public class TextViewUtils {
    private static AssetManager mgr;

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

    /**
     * 给指定的textview设置指定的字体
     *
     * @param context  上下文
     * @param textView textview
     * @param fontName 字体的名字
     */
    public static void setTextFont(Context context, TextView textView, String fontName) {
        //得到AssetManager
        if (mgr == null) {
            mgr = context.getAssets();
        }

        Typeface fromAsset = Typeface.createFromAsset(mgr, fontName);
        textView.setTypeface(fromAsset);
    }

    /**
     * 设置指定的字体，并设置成粗体
     */
    public static void setTextFontBold(Context context, TextView textView, String fontName) {
        //得到AssetManager
        if (mgr == null) {
            mgr = context.getAssets();
        }
        Typeface fromAsset = Typeface.createFromAsset(mgr, fontName);
        textView.setTypeface(fromAsset, Typeface.BOLD);
    }

}
