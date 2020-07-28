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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xjx.helper.R;

public class TextViewDrawable extends View {

    private Paint mPaintTextView = new Paint();
    private Paint mPaintDrawable = new Paint();
    private Bitmap mBitmapLeft;
    private String mContent;
    private float mTextSize;
    private int mTextColor;
    private Drawable mDrawableLeft;
    private Drawable mDrawableRight;
    private Drawable mDrawableTop;
    private Drawable mDrawableBottom;
    private float mDrawableWidth;
    private float mDrawableHeight;
    private float mDrawablePadding;
    private int mContentWidth;
    private int mContentHeight;

    private float mDrawableLeftWidth, mDrawableLeftHeight;
    private float mDrawableTopWidth, mDrawableTopHeight;
    private float mDrawableRightWidth, mDrawableRightHeight;
    private float mDrawableBottomWidth, mDrawableBottomHeight;

    public TextViewDrawable(Context context) {
        super(context);
        initView(context, null);
    }

    public TextViewDrawable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        // 获取属性
        @SuppressLint("CustomViewStyleable")
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextViewDrawable);

        // 获取text的内容
        mContent = array.getString(R.styleable.TextViewDrawable_tw_text);
        // 获取textSize
        mTextSize = array.getDimension(R.styleable.TextViewDrawable_tw_textSize, 16);
        // 获取TextColor
        mTextColor = array.getColor(R.styleable.TextViewDrawable_tw_textColor, context.getResources().getColor(R.color.black));
        // drawable在左侧
        mDrawableLeft = array.getDrawable(R.styleable.TextViewDrawable_tw_drawable_Left);
        // drawable在右侧
        mDrawableRight = array.getDrawable(R.styleable.TextViewDrawable_tw_drawable_Right);
        // drawable在上册
        mDrawableTop = array.getDrawable(R.styleable.TextViewDrawable_tw_drawable_Top);
        // drawable在下册
        mDrawableBottom = array.getDrawable(R.styleable.TextViewDrawable_tw_drawable_Bottom);
        // drawable的Padding
        mDrawablePadding = array.getDimension(R.styleable.TextViewDrawable_tw_drawable_Padding, 0);
        // drawable的宽度
        mDrawableWidth = array.getDimension(R.styleable.TextViewDrawable_tw_drawable_Width, 0);
        // drawable的高度
        mDrawableHeight = array.getDimension(R.styleable.TextViewDrawable_tw_drawable_Height, 0);

        // 释放对象
        array.recycle();

        // 获取文字的宽高
        if (!TextUtils.isEmpty(mContent)) {
            Rect rect = new Rect();
            mPaintTextView.setTextSize(100);
            mPaintTextView.setColor(mTextColor);
            mPaintTextView.getTextBounds(mContent, 0, mContent.length(), rect);

            mContentWidth = rect.width();
            mContentHeight = rect.height();
        }

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        float mWidthValue = 0;
//        float mHeightValue = 0;
////
////        // drawable 如果有不为空的对象才会去重新设置宽高
////        if (mDrawableLeft != null) { // 计算左侧view的宽高
////            // 计算宽度== drawable的宽度 + 文字的宽度 +padding的宽度
////            if (mDrawableWidth > 0) {
////                mDrawableLeftWidth = (mDrawableWidth);
////            } else {
////                mDrawableLeftWidth = (mDrawableLeft.getIntrinsicWidth());
////            }
////
////            if (mDrawableHeight > 0) {
////                mDrawableLeftHeight = mDrawableHeight;
////            } else {
////                mDrawableLeftHeight = mDrawableLeft.getIntrinsicHeight();
////            }
////        } else if (mDrawableRight != null) {    // 计算右侧view的宽高
////            if (mDrawableWidth > 0) {
////                mDrawableRightWidth = (mDrawableWidth);
////            } else {
////                mDrawableRightWidth = (mDrawableRight.getIntrinsicWidth());
////            }
////            if (mDrawableHeight > 0) {
////                mDrawableRightHeight = mDrawableHeight;
////            } else {
////                mDrawableRightHeight = mDrawableRight.getIntrinsicHeight();
////            }
////        } else if (mDrawableTop != null) {    // 计算上方view的宽高
////            if (mDrawableWidth > 0) {
////                mDrawableTopWidth = (mDrawableWidth);
////            } else {
////                mDrawableTopWidth = (mDrawableTop.getIntrinsicWidth());
////            }
////            if (mDrawableHeight > 0) {
////                mDrawableTopHeight = mDrawableHeight;
////            } else {
////                mDrawableTopHeight = mDrawableTop.getIntrinsicHeight();
////            }
////        } else if (mDrawableBottom != null) {    // 计算下方view的宽高
////            if (mDrawableWidth > 0) {
////                mDrawableBottomWidth = (mDrawableWidth);
////            } else {
////                mDrawableBottomWidth = (mDrawableBottom.getIntrinsicWidth());
////            }
////            if (mDrawableHeight > 0) {
////                mDrawableBottomHeight = mDrawableHeight;
////            } else {
////                mDrawableBottomHeight = mDrawableBottom.getIntrinsicHeight();
////            }
////        }
////
////        // 计算左右的view
////        if ((mDrawableLeft != null) && (mDrawableRight != null)) {
////            mWidthValue = mDrawableLeftWidth + mDrawablePadding + mDrawableRightWidth + mDrawablePadding;
////        } else {
////
////        }
////
////        if (mDrawableLeft!=null){
////
////        }
//
//        // setMeasuredDimension:该方法用来设置View的宽高，在我们自定义View时也会经常用到。
//        // getDefaultSize(int size, int measureSpec)：该方法用来获取View默认的宽高，结合源码来看。
//
//        // getSuggestedMinimumHeight //当View没有设置背景时，默认大小就是mMinWidth，；、‘【-=:minWidth属性，如果没有设置时默认为0.
//        //如果有设置背景，则默认大小为mMinWidth和mBackground.getMinimumWidth()当中的较大值
//
//        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
//                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec)
//        );
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText(mContent, 0, 500, mPaintTextView);

        Bitmap leftBitmap = getLeftBitmap();
        canvas.drawBitmap(leftBitmap, mContentWidth, mContentHeight, null);
    }

    private Bitmap getLeftBitmap() {
        if (mDrawableLeft != null) {
            if (mDrawableLeft instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) mDrawableLeft).getBitmap();

//                Canvas canvas = new Canvas(bitmap);
//                mDrawableLeft.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//                mDrawableLeft.draw(canvas);

                return bitmap;
            } else {
                return null;
            }
//            else if (mDrawableLeft instanceof VectorDrawable){
//
//                return null;
//            }
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
