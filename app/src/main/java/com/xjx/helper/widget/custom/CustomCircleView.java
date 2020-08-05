package com.xjx.helper.widget.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xjx.helper.utils.GlideUtil;
import com.xjx.helper.utils.LogUtil;

/**
 * 自定义随机生成圆圈
 */
public class CustomCircleView extends View {
    private Paint mPaintDrawable = new Paint();
    private Paint mPaintCircle = new Paint();
    private Bitmap bitmap;
    private int left;
    private int top;

    public CustomCircleView(Context context) {
        super(context);
        initView(context, null);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();

        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            LogUtil.e("bitmap:--->width:" + width + "--->height：" + height);

            left = (measuredWidth - width) / 2;
            top = (measuredHeight - height) / 2;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, left, top, mPaintDrawable);
        }

    }

    public void setDrawable(String url) {
        bitmap = GlideUtil.getInstance().getBitmapForUrl(url);
        invalidate();
    }

}
