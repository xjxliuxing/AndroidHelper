package com.xjx.helper.widget.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xjx.helper.utils.ConvertUtil;
import com.xjx.helper.R;

public class TextViewDrawableLeft extends View {

    private Paint mPaintDrawable = new Paint();
    private Paint mPaintText = new Paint();
    private Bitmap bitmap;
    private Drawable drawable;
    private int drawableWidth;
    private int drawableHeight;
    private String content;
    private int color;
    private float size;
    private int bitmapHeight;
    private int contentWidth;
    private int contentHeight;
    private int measuredWidth;
    private int measuredHeight;
    private int bitmapWidth;
    private int mHeightValue;

    public TextViewDrawableLeft(Context context) {
        super(context);
        initView(context, null);
    }

    public TextViewDrawableLeft(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        // 获取属性
        @SuppressLint("CustomViewStyleable")
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextViewDrawableLeft);

        // drawable
        drawable = array.getDrawable(R.styleable.TextViewDrawableLeft_tdl_drawable);
        drawableWidth = (int) array.getDimension(R.styleable.TextViewDrawableLeft_tdl_drawable_width, 0);
        drawableHeight = (int) array.getDimension(R.styleable.TextViewDrawableLeft_tdl_drawable_height, 0);

        // text
        content = array.getString(R.styleable.TextViewDrawableLeft_tdl_content);
        color = array.getColor(R.styleable.TextViewDrawableLeft_tdl_color, ContextCompat.getColor(getContext(), R.color.black_4));
        size = array.getDimension(R.styleable.TextViewDrawableLeft_tdl_size, ConvertUtil.toSp(16));

        mPaintText.setColor(color);
        mPaintText.setTextSize(size);
        mPaintText.setAntiAlias(true);

        mPaintDrawable.setAntiAlias(true);

        // 释放对象
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();

        // 获取text的宽高
        if ((contentWidth <= 0) || (contentHeight <= 0)) {
            Rect rect = new Rect();
            mPaintText.getTextBounds(content, 0, content.length(), rect);
            contentWidth = rect.width();
            contentHeight = rect.height();
        }

        if (bitmap == null) {
            bitmap = getBitmap();
        }

        // 获取bitmap的高度
        if (this.bitmap != null) {
            bitmapHeight = this.bitmap.getHeight();
            bitmapWidth = this.bitmap.getWidth();
        }

        if (contentHeight > bitmapHeight) {
            mHeightValue = contentHeight;
        } else {
            mHeightValue = bitmapHeight;
        }

//        int height = mHeightValue;
//        int width = contentWidth + bitmapWidth;
//        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        final int heightSpecSize = MeasureSpec.getMode(heightMeasureSpec);
//        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(width, height);
//        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(width, heightSpecSize);
//        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(widthSpecSize, height);
//        }

        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));

    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bitmap == null || bitmap.isRecycled()) {
            bitmap = getBitmap();
        }

        if (bitmap == null) {
            return;
        }
        // 获取bitmap的高度
        if (bitmapHeight <= 0) {
            bitmapHeight = bitmap.getHeight();
        }

        // 获取text的宽高
        if ((contentWidth <= 0) || (contentHeight <= 0)) {
            Rect rect = new Rect();
            mPaintText.getTextBounds(content, 0, content.length(), rect);
            contentWidth = rect.width();
            contentHeight = rect.height();
        }

        // 设置bitmap
        int top = 0;
        // 如果bitmap的高度大于文字的高度，那么top就是0
        // 如果bitmap的高度小于文字的高度，那么top =  文字的高度 -  bitmap的高度
        if (bitmapHeight < contentHeight) {
            top = (contentHeight - bitmapHeight) / 2;
        }

        Rect rectSrc = new Rect(0, top, drawableWidth, drawableHeight);
        Rect rectDst = new Rect(0, 0, measuredWidth, measuredHeight);

        canvas.drawBitmap(bitmap, rectSrc, rectDst, mPaintDrawable);

        if (!TextUtils.isEmpty(content)) {
            Rect rect = new Rect();
            mPaintText.getTextBounds(content, 0, content.length(), rect);
            canvas.drawText(content, bitmap.getWidth(), bitmap.getHeight(), mPaintText);
        }
    }

    public Bitmap getBitmap() {
        if (drawable != null) {
            if (drawable instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof StateListDrawable) {
                Drawable current = drawable.getCurrent();
                if (current instanceof BitmapDrawable) {
                    bitmap = ((BitmapDrawable) current).getBitmap();
                }
                bitmap.setWidth(drawableWidth);
                bitmap.setHeight(drawableHeight);
            }
        }
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
