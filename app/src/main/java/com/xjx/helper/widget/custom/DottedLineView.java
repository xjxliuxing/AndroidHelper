package com.xjx.helper.widget.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xjx.helper.R;
import com.xjx.helper.utils.ConvertUtil;

public class DottedLineView extends View {

    private Paint paint;
    private float toDp;
    private float toDp1;
    private float yb;

    public DottedLineView(Context context) {
        super(context);
        initView(context, null);
    }

    public DottedLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.blue_1));
        toDp1 = ConvertUtil.toDp(2);
        yb = toDp1 / 2;

        paint.setStrokeWidth(toDp1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));

        toDp = ConvertUtil.toDp(14);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(yb, yb, getMeasuredWidth() - yb, getMeasuredHeight() - yb, toDp, toDp, paint);
    }
}
