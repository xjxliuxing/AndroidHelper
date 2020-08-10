package com.xjx.helper.widget.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xjx.helper.R;
import com.xjx.helper.utils.ConvertUtil;

public class TextViewDrawableLeft extends View {

    private Paint mPaintDrawable = new Paint();
    private Paint mPaintText = new Paint();
    private Bitmap bitmap;
    private Drawable mDrawable;
    private int mDrawableWidth;
    private int mDrawableHeight;
    private String mContent;
    private int mColor;
    private float mSize;
    private int bitmapHeight;
    private int contentWidth;
    private int contentHeight;
    private int mHeightValue;
    private float mDrawablePadding;

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
        @SuppressLint("CustomViewStyleable") TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextViewDrawableLeft);

        // drawable
        mDrawable = array.getDrawable(R.styleable.TextViewDrawableLeft_tdl_drawable);
        mDrawableWidth = (int) array.getDimension(R.styleable.TextViewDrawableLeft_tdl_drawable_width, 0);
        mDrawableHeight = (int) array.getDimension(R.styleable.TextViewDrawableLeft_tdl_drawable_height, 0);
        mDrawablePadding = array.getDimension(R.styleable.TextViewDrawableLeft_tdl_drawable_padding, 0);

        // text
        mContent = array.getString(R.styleable.TextViewDrawableLeft_tdl_content);
        mColor = array.getColor(R.styleable.TextViewDrawableLeft_tdl_color, ContextCompat.getColor(getContext(), R.color.black_4));
        mSize = array.getDimension(R.styleable.TextViewDrawableLeft_tdl_size, ConvertUtil.toSp(16));

        mPaintText.setColor(mColor);
        mPaintText.setTextSize(mSize);
        mPaintText.setAntiAlias(true);

        mPaintDrawable.setAntiAlias(true);

        // 释放对象
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (bitmap == null || bitmap.isRecycled()) {
            bitmap = getBitmap();
        }

        // 获取text的宽高
        if ((contentWidth <= 0) || (contentHeight <= 0)) {
            Rect rect = new Rect();
            mPaintText.getTextBounds(mContent, 0, mContent.length(), rect);
            contentWidth = rect.width();
            contentHeight = rect.height();
        }

        // 计算高度
        if (contentHeight > mDrawableHeight) {
            mHeightValue = contentHeight;
        } else {
            mHeightValue = mDrawableHeight;
        }

        setMeasuredDimension((int) (contentWidth + mDrawableWidth + mDrawablePadding), mHeightValue);
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
            mPaintText.getTextBounds(mContent, 0, mContent.length(), rect);
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

        Rect rectSrc = new Rect(0, 0, mDrawableWidth, mDrawableHeight);
        Rect rectDst = new Rect(0, 0, mDrawableWidth, mDrawableHeight);
        // 绘制图片
        canvas.drawBitmap(bitmap, rectSrc, rectDst, mPaintDrawable);



        // 绘制文字
        if (!TextUtils.isEmpty(mContent)) {
            int heightValue = mDrawableHeight + contentHeight;
            if ((heightValue) > 0) {
                canvas.drawText(mContent, (bitmap.getWidth() + mDrawablePadding), (heightValue / 2), mPaintText);
            }
        }
    }

    public Bitmap getBitmap() {

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.tab_todo_selected);
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }

        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();

        float scaleWidth = ((float) mDrawableWidth) / bmpWidth;
        float scaleHeight = ((float) mDrawableHeight) / bmpHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, true);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
