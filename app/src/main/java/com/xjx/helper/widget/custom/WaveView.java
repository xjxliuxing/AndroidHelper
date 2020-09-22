package com.xjx.helper.widget.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xjx.helper.R;
import com.xjx.helper.utils.ConvertUtil;

import java.util.ArrayList;
import java.util.List;

public class WaveView extends View {
    
    private static int centerColor;
    private Paint mPaint;
    private float alpharate;
    private boolean isStartAnim = false;
    private int mSpace;
    private int mWidth = 400;
    private boolean isFillstyle;
    private List<Float> widths = new ArrayList();
    private List<Integer> mAlphas = new ArrayList();
    
    public WaveView(Context context) {
        this(context, null);
    }
    
    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        
    }
    
    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView, defStyleAttr, 0);
//        centerColor = typedArray.getColor(R.styleable.WaveView_wavecolor, getResources().getColor(R.color.colorAccent));
//        mSpace = typedArray.getInteger(R.styleable.WaveView_space, 100);
//        isFillstyle = typedArray.getBoolean(R.styleable.WaveView_fillstyle, true);
//        mWidth = typedArray.getInteger(R.styleable.WaveView_width, 400);
//        typedArray.recycle();
        init();
    }
    
    private void init() {
        centerColor = ContextCompat.getColor(getContext(), R.color.white);
        mSpace = (int) ConvertUtil.toDp(20f);
        
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        if (isFillstyle) {
//            mPaint.setStyle(Paint.Style.FILL);
//        } else {
//        }
        
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        
        alpharate = 255f / mWidth; //注意这里 如果为int类型就会为0,除数中f一定要加,默认int ;
        mAlphas.add(255);
        widths.add(0f);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        if (isStartAnim) {
            invalidate();
        }
        mPaint.setColor(centerColor);
        for (int i = 0; i < widths.size(); i++) { //遍历圆数目
            Integer cuAlpha = mAlphas.get(i);
            mPaint.setAlpha(cuAlpha);
            Float aFloat = widths.get(i);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, aFloat, mPaint); //画圆
            if (aFloat < mWidth) {  //扩散直径和透明度
                mAlphas.set(i, (int) (255 - alpharate * aFloat));
                widths.set(i, aFloat + 1);
            }
        }
        
        if (widths.size() >= 5) {
            mAlphas.remove(0);
            widths.remove(0);
        }
        
        if (widths.get(widths.size() - 1) == mSpace) {
            mAlphas.add(255);
            widths.add(0f);
        }
        
    }
    
    public void startAnim() {
        isStartAnim = true;
        invalidate();
    }
    
    public void pauseAnim() {
        isStartAnim = false;
    }
}
