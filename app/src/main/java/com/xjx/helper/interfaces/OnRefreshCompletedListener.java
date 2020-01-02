package com.xjx.helper.interfaces;

/**
 * 刷新控件完成后的监听操作
 */
public interface OnRefreshCompletedListener {

    // 刷新完成的操作
    void onRefreshCompleted();

    // 没有更多数据的操作
    void onRefreshNoMoreDate();
}
