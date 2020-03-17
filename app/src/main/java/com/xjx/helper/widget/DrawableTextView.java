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
import com.xjx.helper.utils.LogUtil;

/**
 * @作者 徐腾飞
 * @创建时间 2020/3/17  10:44
 * @更新者 HongJing
 * @更新时间 2020/3/17  10:44
 * @描述 这个DrawableTextView 主要是针对textView不能设置drawable大小去构造的，使用的时候，
 */
@SuppressLint("AppCompatCustomView")
public class DrawableTextView extends TextView {

    private int direction;// 方向，左上右下，分别对应，0,1,2,3
    private Drawable drawable;// 获取的drawable资源

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
        int roundWidth = Math.round(dpWidth);
        int roundHeight = Math.round(dpHeight);

        // 获取textView中drawable的数组对象
        Drawable[] compoundDrawables = getCompoundDrawables();
        if (compoundDrawables.length <= 0) {
            return;
        }

        // 循环拿到drawable资源的对象
        for (int i = 0; i < compoundDrawables.length; i++) {
            Drawable compoundDrawable = compoundDrawables[i];
            if (compoundDrawable != null) {
                // 获取方向
                direction = i;
                // 获取对象
                drawable = compoundDrawable;
                break;
            }
        }

        if (drawable == null) {
            LogUtil.e("drawble对象为空");
            return;
        }

        // 设置drawable宽高
        drawable.setBounds(0, 0, roundWidth, roundHeight);

        // 设置drawable的位置
        switch (direction) {
            case 0:
                setCompoundDrawables(drawable, null, null, null);
                break;

            case 1:
                setCompoundDrawables(null, drawable, null, null);
                break;

            case 2:
                setCompoundDrawables(null, null, drawable, null);
                break;

            case 3:
                setCompoundDrawables(null, null, null, drawable);
                break;
        }

        // 释放对象
        array.recycle();
    }
}
