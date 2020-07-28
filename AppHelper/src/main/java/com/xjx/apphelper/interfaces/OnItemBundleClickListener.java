package com.xjx.apphelper.interfaces;

import android.os.Bundle;
import android.view.View;

/**
 * Recycleview 的 条目点击事件
 */
public interface OnItemBundleClickListener {

    void onItemBundleClick(View view, int position, Bundle bundle);
}
