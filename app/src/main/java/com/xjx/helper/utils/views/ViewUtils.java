package com.xjx.helper.utils.views;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {

    /**
     * 设置view的margin的数据
     *
     * @param view  view的对象，一般都是viewGrounp
     * @param array 左上右下的顺序
     */
    public static void setMargin(View view, int[] array) {
        if (view == null) {
            return;
        }
        if ((array == null) || (array.length != 4)) {
            return;
        }

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
        int left = array[0];
        int top = array[1];
        int right = array[2];
        int bottom = array[3];

        marginLayoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(marginLayoutParams);
    }
}
