package com.xjx.helper.interfaces;

import com.xjx.helper.enums.PlaceholderStatus;

/**
 * 加载状态图的监听
 */
public interface OnPlaceholderlistener {

    void onPlaceHolder(PlaceholderStatus placeholder, String message);
}
