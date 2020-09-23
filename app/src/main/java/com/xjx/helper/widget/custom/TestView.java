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
import com.xjx.helper.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class TestView extends View {
    
    private boolean isStartAnim = false; // 控制是否循环
    private Paint mPaintSpread;
    private Bitmap mBitmap;
    private int mBitmapWidth;
    private int mBitmapHeight;
    
    private int mLeft;
    private int mRight;
    private int mTop;
    private int mBottom;
    private float mBottomValue = ConvertUtil.toDp(100f); // 距离底部的距离
    
    private List<Float> mRadiusList = new ArrayList();
    private List<Integer> mAlphaList = new ArrayList();
    
    private int maxRadius = 0; // 最大的半径值
    private int mRadiusPadding = (int) ConvertUtil.toDp(30f); // 左右边距的值
    
    private float alphasZoom; // 透明度
    private int centerX; // view的X轴中心
    private int centerY; // view的Y轴中心
    private int radius;   // 绘制的半径
    private int mPaintWidth = 30; // 画笔的宽度
    private int intervalWidth = 30; // 每隔view间隔的宽度
    private float speed = 1.5f;// 扩散的速度
    
    public TestView(Context context) {
        super(context);
        initView(context, null);
    }
    
    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }
    
    private void initView(Context context, AttributeSet attrs) {
        
        mPaintSpread = new Paint();
        mPaintSpread.setAntiAlias(true);
        mPaintSpread.setDither(true);
        mPaintSpread.setStyle(Paint.Style.FILL);
        mPaintSpread.setStrokeWidth(mPaintWidth);// view的宽度
        mPaintSpread.setColor(ContextCompat.getColor(getContext(), R.color.white));
        
        mAlphaList.add(255); // 默认设置最大值
        mRadiusList.add(0f);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        int value = (int) (mBitmapHeight + mBottomValue * 2);
        
        setMeasuredDimension(
                resolveSize(View.MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec),
                value
        );
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        
        mBitmap = CustomViewUtil.getBitmapDefault(ContextCompat.getDrawable(getContext(), R.mipmap.anxia));
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
        
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
        alphasZoom = 255f / (maxRadius - radius); //注意这里 如果为int类型就会为0,除数中f一定要加,默认int ;
    }
    
    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        for (int i = 0; i < mRadiusList.size(); i++) {
            // 设置透明
            float alphas = mAlphaList.get(i);
            LogUtil.e("alpha:" + alphas);
            mPaintSpread.setAlpha((int) alphas);
            
            Float width = mRadiusList.get(i);
            // 绘制圆形
            canvas.drawCircle(centerX, centerY, width + radius, mPaintSpread);
            
            LogUtil.e("width:" + width + "  alphas:" + alphas + "   alphasZoom：" + alphasZoom + " maxRadius：" + maxRadius + "  maxLenght:" + (maxRadius - radius));
            if (width + radius < maxRadius) {  //扩散直径和透明度
                LogUtil.e("  ssss:" + (255 - alphasZoom * (width + speed)));
                mAlphaList.set(i, (int) (255 - alphasZoom * (width + speed)));
                mRadiusList.set(i, width + speed);
            } else {
                mAlphaList.set(i, 0);
            }
        }
        
        // 只要最外边的view宽度大于30 ，就开始添加一个view
        Float current = mRadiusList.get(mRadiusList.size() - 1);
        if ((current) == (mPaintWidth + intervalWidth)) {
            mRadiusList.add(0f);
            mAlphaList.add(255);
        }
        
        if (mRadiusList.size() >= 10) {
            mRadiusList.remove(0);
            mAlphaList.remove(0);
        }
        
        // 绘制中心圆
        Rect srcRect = new Rect(0, 0, mBitmapWidth, mBitmapHeight);
        Rect desRect = new Rect(mLeft, mTop, mRight, mBottom);
        canvas.drawBitmap(mBitmap, srcRect, desRect, null);
        
        if (isStartAnim) {
            invalidate();
        }
    }
    
    public void start() {
        isStartAnim = true;
        invalidate();
    }
    
    public void pause() {
        isStartAnim = false;
    }
    
}
