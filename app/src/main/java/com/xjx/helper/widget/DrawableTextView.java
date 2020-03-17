package com.xjx.helper.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.xjx.helper.R;
import com.xjx.helper.utils.ConvertUtil;

/**
 * @作者 徐腾飞
 * @创建时间 2020/3/17  10:44
 * @更新者 HongJing
 * @更新时间 2020/3/17  10:44
 * @描述 这个DrawableTextView 主要是针对textView不能设置drawable大小去构造的，使用的时候，
 */
@SuppressLint("AppCompatCustomView")
public class DrawableTextView extends TextView {

    private int roundWidth;
    private int roundHeight;

    public DrawableTextView(Context context) {
        super(context);
        init(context, null);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init(Context context, @Nullable AttributeSet attrs) {

        // 获取属性
        @SuppressLint("CustomViewStyleable")
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);

        // 获取属性值
        float width = array.getFloat(R.styleable.DrawableTextView_drawable_text_width, 0);
        float height = array.getFloat(R.styleable.DrawableTextView_drawable_text_height, 0);

        // 转换为dp值
        float dpWidth = ConvertUtil.toDp(width);
        float dpHeight = ConvertUtil.toDp(height);

        // 四舍五入取整数
        roundWidth = Math.round(dpWidth);
        roundHeight = Math.round(dpHeight);

        // 释放对象
        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Drawable[] drawables = getCompoundDrawables();
        // drawable对象
        Drawable drawableLeft = drawables[0];
        Drawable drawableTop = drawables[1];
        Drawable drawableRight = drawables[2];
        Drawable drawableBottom = drawables[3];

        if (drawableLeft != null) {
            setDrawable(drawableLeft, 0, roundWidth, roundHeight);
        }
        if (drawableTop != null) {
            setDrawable(drawableTop, 1, roundWidth, roundHeight);
        }
        if (drawableRight != null) {
            setDrawable(drawableRight, 2, roundWidth, roundHeight);
        }
        if (drawableBottom != null) {
            setDrawable(drawableBottom, 3, roundWidth, roundHeight);
        }
        this.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

    private void setDrawable(Drawable drawable, int tag, int drawableWidth, int drawableHeight) {
        drawable.setBounds(0, 0, drawableWidth, drawableHeight);
    }

}
