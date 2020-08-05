package com.xjx.helper.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xjx.helper.R;
import com.xjx.helper.global.App;

/**
 * Created by admin on 2017/1/4.
 */

public class ToastUtil {

    private static View view;
    private static TextView textView;
    private static Toast toast;

    /**
     * 强大的吐司，能够连续弹的吐司
     *
     * @param text
     */
    public static void showToast(String text) {

        showToast(text, Toast.LENGTH_SHORT, Gravity.BOTTOM, 0, 180);
    }

    public static void showToast(String text, int duration, int gravity, int xOffset, int yOffset) {
        Context context = App.getInstance().getContext();

        if (toast != null) {
            toast.cancel();
        }

        if (TextUtils.isEmpty(text)) {
            return;
        }

        toast = new Toast(context);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.widget_toast, null);
            textView = (TextView) view.findViewById(R.id.message);
        }
        if (textView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.widget_toast, null);
            textView = (TextView) view.findViewById(R.id.message);
        }

        // 4:设置布局的内容
        textView.setText(text);

        // 5:设置Toast的参数
        toast.setDuration(duration);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setView(view);

        toast.show();
    }

    public static void showUiToast(final Activity activity, String value, int duration, int gravity, int xOffset, int yOffset) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(value, duration, gravity, xOffset, yOffset);
            }
        });
    }

    public static void showUiToast(final Activity activity, final String value) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(value);
            }
        });
    }
}
