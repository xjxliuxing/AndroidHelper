package com.xjx.helper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xjx.helper.R;

/**
 * 加载页面占位图
 */
public class PlaceHolderView extends FrameLayout {

    public PlaceHolderView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public PlaceHolderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        // 把自定义的布局添加到站位图中去
        View inflate = LayoutInflater.from(context).inflate(R.layout.common_base_place_holder, this);

    }

}
