package com.xjx.helper.widget.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xjx.helper.R;

public class RoundView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public RoundView(Context context) {
        super(context);
        intView(context, null);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        intView(context, attrs);
    }

    private void intView(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = getBitmap();

//        RectF rect3 = new RectF(0, 0, mWidth, mHeight);
//        canvas.drawRoundRect(rect3, 50, 50, mPaint);

        //第二种
        canvas.drawRoundRect(0, 0, mWidth, mHeight, 80, 80, mPaint);

        assert bitmap != null;
        Rect rect1 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect rect2 = new Rect(80, 80, mWidth - 80, mHeight - 80);

        canvas.drawBitmap(bitmap, rect1, rect2, mPaint);
    }

    private Bitmap getBitmap() {

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.tab_todo_selected);
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return bitmap;
        }
        return null;
    }

}
