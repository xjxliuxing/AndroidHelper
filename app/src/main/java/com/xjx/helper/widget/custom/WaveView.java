package com.xjx.helper.widget.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xjx.helper.R;
import com.xjx.helper.utils.ConvertUtil;
import com.xjx.helper.utils.CustomViewUtil;

import java.util.ArrayList;
import java.util.List;

public class WaveView extends View {
    
    private boolean isStartAnim = false;
    private int mSpace;
    private List<Float> mRadiusList = new ArrayList();
    private List<Integer> mAlphaList = new ArrayList();
    
    private Paint mPaintSpread;
    private Bitmap mBitmap;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private float alphas; // 透明度
    private int centerX; // view的X轴中心
    private int centerY; // view的Y轴中心
    private int radius;   // 绘制的半径
    
    private int mLeft;
    private int mRight;
    private int mTop;
    private int mBottom;
    private float mBottomValue = ConvertUtil.toDp(100f);
    private int maxRadius = 0; // 最大的半径值
    private int mRadiusPadding = (int) ConvertUtil.toDp(30f); // 边距最大的值
    
    public WaveView(Context context) {
        this(context, null);
        initView(context, null);
    }
    
    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }
    
    private void initView(Context context, @Nullable AttributeSet attrs) {
        mSpace = (int) ConvertUtil.toDp(20f);
        
        mBitmap = CustomViewUtil.getBitmapDefault(ContextCompat.getDrawable(context, R.mipmap.anxia));
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        
        mPaintSpread = new Paint();
        mPaintSpread.setAntiAlias(true);
        mPaintSpread.setStrokeWidth(3);
        mPaintSpread.setDither(true);
        mPaintSpread.setStyle(Paint.Style.FILL);
        mPaintSpread.setColor(ContextCompat.getColor(getContext(), R.color.white));
        
        mAlphaList.add(255);
        mRadiusList.add(0f);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(
                resolveSize(View.MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec),
                resolveSize(View.MeasureSpec.getSize(heightMeasureSpec), heightMeasureSpec)
        );
    }
    
    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        
        for (int i = 0; i < mRadiusList.size(); i++) { //遍历圆数目
            // 设置透明度
            mPaintSpread.setAlpha(mAlphaList.get(i));
            // 设置宽度
            Float width = mRadiusList.get(i);
            
            canvas.drawCircle(centerX, centerY, width + radius, mPaintSpread); //画圆
            
            if (width + radius <= maxRadius) {  //扩散直径和透明度
                mAlphaList.set(i, (int) (255 - alphas * width));
                mRadiusList.set(i, width + 1);
            }
        }
        
        if (mRadiusList.size() >= 10) {
            mAlphaList.remove(0);
            mRadiusList.remove(0);
        }
        
        if (mRadiusList.get(mRadiusList.size() - 1) == mSpace) {
            mAlphaList.add(255);
            mRadiusList.add(0f);
        }
        
        // 绘制中心的图片
        if (mBitmap.isRecycled()) {
            mBitmap = CustomViewUtil.getBitmapDefault(ContextCompat.getDrawable(getContext(), R.mipmap.anxia));
            mBitmapWidth = mBitmap.getWidth();
            mBitmapHeight = mBitmap.getHeight();
        }
        
        Rect srcRect = new Rect(0, 0, mBitmapWidth, mBitmapHeight);
        Rect desRect = new Rect(mLeft, mTop, mRight, mBottom);
        canvas.drawBitmap(mBitmap, srcRect, desRect, null);
        
        if (isStartAnim) {
            invalidate();
        }
    }
    
    public void startAnim() {
        isStartAnim = true;
        invalidate();
    }
    
    public void pauseAnim() {
        isStartAnim = false;
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        
        mLeft = (measuredWidth - mBitmapWidth) / 2;
        mRight = mLeft + mBitmapWidth;
        mTop = (int) (measuredHeight - mBitmapHeight - mBottomValue);
        mBottom = (int) (measuredHeight - mBottomValue);
        
        // 中心的模糊效果
        centerX = (mLeft + (mBitmapWidth / 2)); // 圆形X轴的中心位置
        centerY = (mTop + (mBitmapHeight / 2)); // 圆形Y轴的中心位置
        radius = Math.max(mBitmapWidth / 2, mBitmapHeight / 2);   // 圆形的半径
        setLayerType(LAYER_TYPE_SOFTWARE, mPaintSpread);
        
        // 最大的半径
        maxRadius = (measuredHeight / 2) - mRadiusPadding;
        alphas = 255f / maxRadius; //注意这里 如果为int类型就会为0,除数中f一定要加,默认int ;
    }
}
