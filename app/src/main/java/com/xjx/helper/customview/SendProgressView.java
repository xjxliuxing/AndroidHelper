package com.xjx.helper.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.xjx.helper.R;

/**
 * 圆形进度条的view
 * 1：宽度可以指定
 * 2：颜色可以指定
 * 3：总的时间可以指定
 */
public class SendProgressView extends View {

    private int height;
    private int width;
    private RectF rectF;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private DisplayMetrics displayMetrics;
    private Drawable drawable;
    private float strokeWidthValue;
    private long progress = 0;
    private int average; // 计算出平均值
    private int time;
    private Bitmap bitmap;
    private int intrinsicWidth;
    private int intrinsicHeight;

    public SendProgressView(Context context) {
        super(context);
        initView(context, null);
    }

    public SendProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    @SuppressLint("ResourceType")
    private void initView(Context context, @Nullable AttributeSet attrs) {
        // 获取属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SendProgressView);
        // 一共几个view
        // 使用四舍五入的方式，获取需要设置的view
        drawable = array.getDrawable(R.styleable.SendProgressView_drawable);
        int strokeWidth = array.getInteger(R.styleable.SendProgressView_stroke_Width, 3);
        int colorInner = array.getColor(R.styleable.SendProgressView_inner_layer_color, Color.WHITE);
        int colorOuter = array.getColor(R.styleable.SendProgressView_outer_layer_color, Color.WHITE);
        // 内层的透明度
        float alphaInner = array.getFloat(R.styleable.SendProgressView_inner_alpha, 0.2f);
        // 多少时间走完整个圆圈（单位秒）
        time = array.getInteger(R.styleable.SendProgressView_time, 90);
        average = 360 / time;

        if (displayMetrics == null) {
            displayMetrics = getResources().getDisplayMetrics();
        }

        strokeWidthValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, strokeWidth, displayMetrics);

        // 外层的画笔
        paint1 = new Paint();
        paint1.setColor(colorInner); // 设置颜色
        paint1.setAlpha((int) (alphaInner * 255));   // 设置透明度
        paint1.setStyle(Paint.Style.STROKE); // 设置为实体
        paint1.setStrokeWidth(strokeWidthValue);  // 设置画笔的宽度
        // 设置线段连接处样式  Join.MITER（结合处为锐角）Join.Round(结合处为圆弧) Join.BEVEL(结合处为直线)
        paint1.setStrokeCap(Paint.Cap.ROUND); // 设置圆角
        paint1.setAntiAlias(true);        // 防锯齿

        // 内层的画笔
        paint2 = new Paint();
        paint2.setColor(colorOuter); // 设置颜色
        paint2.setStyle(Paint.Style.STROKE); // 设置为实体
        paint2.setStrokeWidth(strokeWidthValue);  // 设置画笔的宽度
        // 设置线段连接处样式  Join.MITER（结合处为锐角）Join.Round(结合处为圆弧) Join.BEVEL(结合处为直线)
        paint2.setStrokeCap(Paint.Cap.ROUND); // 设置圆角
        paint2.setAntiAlias(true);        // 防锯齿

        paint3 = new Paint();
        paint3.setAntiAlias(true);        // 防锯齿

        rectF = new RectF();

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();

        float width2 = strokeWidthValue / 2;

        rectF.left = width2;
        rectF.top = width2;
        rectF.right = width - width2;
        rectF.bottom = height - width2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        OuterRing(canvas);
        innerRing(canvas);
        drawPictures(canvas);
    }

    private void drawPictures(Canvas canvas) {
        if (bitmap == null || bitmap.isRecycled()) {
            bitmap = getBitmap();
        }
        int with2 = width - intrinsicWidth;
        int height2 = height - intrinsicHeight;

//        canvas.drawBitmap(bitmap, with2 / 2, height2 / 2, paint3);

        Rect rect = new Rect((with2 / 2-50), (height2 / 2-50), ((with2 / 2) + 50), (height2 / 2 +50));
        RectF rectF = new RectF((with2 / 2-50), (height2 / 2-50), ((with2 / 2) + 50), (height2 / 2 +50));

        canvas.drawBitmap(bitmap,rect,rectF,paint3);
    }

    private Bitmap getBitmap() {
        intrinsicWidth = drawable.getIntrinsicWidth();
        intrinsicHeight = drawable.getIntrinsicHeight();

        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 设置外环
     */
    private void OuterRing(Canvas canvas) {
        canvas.drawArc(rectF, -90, 360, false, paint1);
    }

    /**
     * 设置内环
     */
    private void innerRing(Canvas canvas) {
        long value = progress * average;
        canvas.drawArc(rectF, -90, value, false, paint2);
    }

    /**
     * 设置当前的进度
     */
    public void setProgress(Long progress) {
        this.progress = progress;
        invalidate();
    }

    /**
     * @return 获取设置的事件
     */
    public long getTime() {
        return time;
    }


}
