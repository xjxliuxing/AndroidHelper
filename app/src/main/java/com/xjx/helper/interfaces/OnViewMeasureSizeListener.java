package com.xjx.helper.interfaces;

/**
 * View测量宽高的回调接口，用来异步获取View的宽和高
 */
public interface OnViewMeasureSizeListener {
    void onMeasureSize(int width, int height);
}
