package com.xjx.helper.utils.refresh;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * SmartRefreshLayout 刷新的扩展类，为了避免以后升级的改动，如果改动就全部在这里进行， 尽量避免改动布局
 */
public class MySmartRefreshLayout extends SmartRefreshLayout {

    public MySmartRefreshLayout(Context context) {
        super(context);
    }

    public MySmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    private void initView() {

    }
}
