package com.xjx.helper.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.xjx.helper.utils.LogUtil;

/**
 * 自定义圆形的图片
 */
public class CustomRoundView extends androidx.appcompat.widget.AppCompatImageView {

    public CustomRoundView(Context context) {
        super(context);
    }

    public CustomRoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        int value = 0;
        if (measuredHeight > measuredWidth) {
            value = measuredHeight;
        } else if (measuredHeight < measuredWidth) {
            value = measuredHeight;
        } else if (measuredHeight == measuredWidth) {
            value = measuredHeight;
        }

        // 强制改变大小结构
        setMeasuredDimension(value, value);
        LogUtil.e("widht:---->" + measuredWidth + "---->" + measuredHeight);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取background的对象
        Drawable drawable = getDrawable();

        if (drawable != null) {
            //获取drawable的宽和高
            int dWidth = drawable.getIntrinsicWidth();
            int dHeight = drawable.getIntrinsicHeight();


            Paint paint = new Paint();
            paint.setAntiAlias(true);

            Bitmap bitmap = getBitmap(drawable);

            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }

    private Bitmap getBitmap(Drawable drawable) {
        // 创建一个和指定尺寸相同的bitmap
        @SuppressLint("DrawAllocation")
        Bitmap bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);


        drawable.draw(canvas);

        return bitmap;
    }
}
