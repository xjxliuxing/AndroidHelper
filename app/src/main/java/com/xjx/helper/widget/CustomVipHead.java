package com.xjx.helper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.xjx.helper.utils.LogUtil;

public class CustomVipHead extends ViewGroup {

    private Paint mPaint;
    private int childCount;

    private View mHead;
    private View mVip;
    private int mHeadWidth;
    private int mVipWidth;
    private int mHeadHeight;
    private int mVipHeight;

    public CustomVipHead(Context context) {
        super(context);
        initView(context, null);
    }

    public CustomVipHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    // 布局完成后的调用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            String tag = (String) childAt.getTag();
            if (tag.equals("head")) {
                mHead = childAt;
            } else if
            (tag.equals("vip")) {
                mVip = childAt;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //制定测量规则 参数表示size + mode
//        int width = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getMode(widthMeasureSpec));
//        int height = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(heightMeasureSpec), View.MeasureSpec.getMode(heightMeasureSpec));

        int width = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
        int height = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.AT_MOST);

        //调用measure方法之后就可以获取宽高
        mHead.measure(width, height);

        mHeadWidth = mHead.getMeasuredWidth(); // 获取宽度
        mHeadHeight = mHead.getMeasuredHeight();// 获取高度
        LogUtil.e("width:" + mHeadWidth + "--->height:" + mHeadHeight);

        mVipWidth = mVip.getMeasuredWidth();
        mVipHeight = mVip.getMeasuredHeight();

        int widths = getViewSize(0, widthMeasureSpec);
        int heights = getViewSize(0, heightMeasureSpec);
        LogUtil.e("width:" + widths + "--->height:" + heights);

        setMeasuredDimension(widths, heights);
    }


    private int getViewSize(int defaultSize, int measureSpec) {
        int viewSize = defaultSize;
        //获取测量模式
        int mode = MeasureSpec.getMode(measureSpec);
        //获取大小
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: //如果没有指定大小，就设置为默认大小
                viewSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST: //如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                viewSize = size;
                break;
            case MeasureSpec.EXACTLY: //如果是固定的大小，那就不要去改变它
                viewSize = size;
                break;
        }
        return viewSize;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mHead.layout(0, 0, mHeadWidth, mHeadHeight);
//        mHead.layout(0, 0, 60, 60);
        int value = mVipWidth / 2;
        int left = mHeadWidth - value;

//        mVip.layout(left, mHeadHeight - mVipHeight, mVipWidth + value, mHeadHeight);
        invalidate();
    }

    private void initView(Context context, AttributeSet attrs) {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置画笔为无锯齿  
        mPaint.setColor(Color.BLACK);//设置画笔颜色  
//canvas.drawColor(Color.WHITE);                  //白色背景  
        mPaint.setStrokeWidth(3.0f);//线宽  
        mPaint.setStyle(Paint.Style.STROKE);//空心效果  
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawCircle(550, 900, 100, mPaint);

    }
}
