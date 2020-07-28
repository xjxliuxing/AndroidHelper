package com.xjx.apphelper.interfaces;

import com.xjx.apphelper.enums.PlaceholderStatus;

/**
 * 加载状态图的监听
 */
public interface OnPlaceholderlistener {

    void onPlaceHolder(PlaceholderStatus placeholder, String message);
}
