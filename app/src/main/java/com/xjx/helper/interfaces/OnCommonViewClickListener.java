package com.xjx.helper.interfaces;

import android.view.View;

/**
 * 公共的 接口回调方法，用来传递数据使用
 */
public interface OnCommonViewClickListener<T> {
    void onViewClick(View view, int position, T data);
}
