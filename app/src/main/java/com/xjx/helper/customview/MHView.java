package com.xjx.helper.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xjx.helper.utils.ConvertUtil;

public class MHView extends View {

    private Paint mPaint = new Paint();

    public MHView(Context context) {
        super(context);
    }

    public MHView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = resolveSize(View.MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec);
        int height = resolveSize(View.MeasureSpec.getSize(heightMeasureSpec), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(ConvertUtil.toDp(8));
        mPaint.setAntiAlias(true);
        // 设置画笔遮罩滤镜  ,传入度数和样式
        mPaint.setMaskFilter(new BlurMaskFilter(40, BlurMaskFilter.Blur.NORMAL));
        // 画一个矩形
        canvas.drawLine(0, 400, 600, 400, mPaint);
    }
}
