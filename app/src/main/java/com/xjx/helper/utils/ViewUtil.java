package com.xjx.helper.utils;

import android.view.View;
import android.view.ViewGroup;

import com.xjx.helper.interfaces.OnViewMeasureSizeListener;

/**
 * View的工具类
 */
public class ViewUtil {

    public static ViewUtil viewUtil;

    public static ViewUtil newInstance() {
        if (viewUtil == null) {
            viewUtil = new ViewUtil();
        }
        return viewUtil;
    }

    /**
     * @param view
     * @param array 设置View的margin值，顺序为左上右下
     */
    public static void setViewMargin(View view, int[] array) {
        if (view == null || array == null || array.length <= 0) {
            return;
        } else {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams viewGrounpLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                viewGrounpLayoutParams.setMargins(array[0], array[1], array[2], array[3]);
            }
        }
    }

    /**
     * @param view
     * @param marginType 0：左侧、1：上边、2：右侧、3：下侧
     * @param value      设置View的margin值，
     */
    public static void setViewMargin(View view, int marginType, int value) {
        if (view == null) {
            return;
        } else {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams viewGrounpLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                switch (marginType) {
                    case 0:
                        viewGrounpLayoutParams.leftMargin = value;
                        break;

                    case 1:
                        viewGrounpLayoutParams.topMargin = value;
                        break;

                    case 2:
                        viewGrounpLayoutParams.rightMargin = value;
                        break;

                    case 3:
                        viewGrounpLayoutParams.bottomMargin = value;
                        break;
                }
            }
        }
    }

    /**
     * @param view
     * @return 获取View的宽高，使用这个方法，需要在依赖的View的Activity或者Fragment中使用EventBus 去获取#{CommonUser.KEY_EVENT_VIEW_SIZE}的值，再根据tag去获取对应的对象
     */
    public ViewUtil getViewMeasureSize(final View view, final OnViewMeasureSizeListener measureSizeListener) {

        view.post(new Runnable() {
            @Override
            public void run() {
                int measuredWidth = view.getMeasuredWidth();// 获取宽度
                int measuredHeight = view.getMeasuredHeight();// 获取高度

                LogUtil.e("--->measuredWidth:" + measuredWidth);
                LogUtil.e("--->measuredHeight:" + measuredHeight);

                if (measureSizeListener != null) {
                    measureSizeListener.onMeasureSize(measuredWidth, measuredHeight);
                }
            }
        });
        return viewUtil;
    }

    /**
     * @param view
     * @return 获取view在屏幕上的X轴和Y轴坐标
     */
    public int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

}
