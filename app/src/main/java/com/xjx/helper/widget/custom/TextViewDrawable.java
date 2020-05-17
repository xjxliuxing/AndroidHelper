package com.xjx.helper.widget.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xjx.helper.R;

public class TextViewDrawable extends View {

    private Paint mPaintTextView = new Paint();
    private Paint mPaintDrawable = new Paint();
    private Drawable drawableLeft;
    private Bitmap mBitmapLeft;


    public TextViewDrawable(Context context) {
        super(context);
        initView(context, null);
    }

    public TextViewDrawable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mPaintTextView.setTextSize(15);


        // 获取属性
        @SuppressLint("CustomViewStyleable")
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextViewDrawable);

        drawableLeft = array.getDrawable(R.styleable.TextViewDrawable_drawable_left);


        // 释放对象
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // setMeasuredDimension:该方法用来设置View的宽高，在我们自定义View时也会经常用到。
        // getDefaultSize(int size, int measureSpec)：该方法用来获取View默认的宽高，结合源码来看。

        // getSuggestedMinimumHeight //当View没有设置背景时，默认大小就是mMinWidth，；、‘【-=:minWidth属性，如果没有设置时默认为0.
        //如果有设置背景，则默认大小为mMinWidth和mBackground.getMinimumWidth()当中的较大值

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmapLeft == null || mBitmapLeft.isRecycled()) {
            mBitmapLeft = drawableToBitmap(drawableLeft,300,300);
        }
        canvas.drawBitmap(mBitmapLeft, 0, 0, mPaintDrawable);


    }


    private Bitmap getLeftBitmap() {
        if (drawableLeft != null) {
            if (drawableLeft instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawableLeft).getBitmap();

                Canvas canvas = new Canvas(bitmap);
                drawableLeft.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawableLeft.draw(canvas);

                return bitmap;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }



    public Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
