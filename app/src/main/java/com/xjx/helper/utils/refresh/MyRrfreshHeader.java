package com.xjx.helper.utils.refresh;

import android.content.Context;
import android.util.AttributeSet;

/**
 * SmartRefreshLayout 刷新头部的扩展类
 */
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class MyRrfreshHeader extends ClassicsHeader {
    public MyRrfreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public MyRrfreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {

    }

}
