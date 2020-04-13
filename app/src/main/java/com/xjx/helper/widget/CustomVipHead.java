package com.xjx.helper.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.xjx.helper.utils.ConvertUtil;
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
    private int value;
    private int round;

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
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        // 他是用于将所有的子 View 进行测量，这会触发每个子 View 的 onMeasure 函数
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
//
//        measureChild(mHead, widthMeasureSpec, heightMeasureSpec);
//
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//
//        // viewGrounp的宽和高都是warp自动适应的模式
//        if ((widthMode == MeasureSpec.AT_MOST) && (heightMode == MeasureSpec.AT_MOST)) {
//            // 获取最大的宽度和最大的高度
//            int groupWidth = getMaxWidth();
//            int groupHeight = getTotalHeight();
//
//            // 设置之viewGrounp的宽度和高度
//            setMeasuredDimension(groupWidth, groupHeight);
//
//        } else if (widthMode == MeasureSpec.AT_MOST) {
//            // 当viewGrounp的的宽度为warp的模式，宽度为最大值得宽度，高度为。。。
//            setMeasuredDimension(getMaxWidth(), height);
//
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(width, getTotalHeight());
//
//        }

        // 让子view都去测量一遍自己的onMeasure方法
//        measureChildren(widthMeasureSpec, heightMeasureSpec);

        measureChild(mHead, widthMeasureSpec, heightMeasureSpec);


        mHeadWidth = mHead.getMeasuredWidth();
        mHeadHeight = mHead.getMeasuredHeight();

        LogUtil.e("head--->width:" + mHeadWidth + "--->height:" + mHeadHeight);

        measureChild(mVip, widthMeasureSpec, heightMeasureSpec);
        mVipWidth = mVip.getMeasuredWidth();
        mVipHeight = mVip.getMeasuredHeight();

        // vip宽度的一半
        value = mVipWidth / 2;

        float v = ConvertUtil.toDp(0.5f);
        round = Math.round(v);

        // 设置viewGrounp的大小
        setMeasuredDimension(mHeadWidth + value, mHeadHeight + round);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mHead.layout(0, 0, mHeadWidth, mHeadHeight);

        int value = mVipWidth / 2;
        int left = mHeadWidth - value;

        mVip.layout(left, mHeadHeight - mVipHeight - round, mHeadWidth + value, mHeadHeight + round);
    }

    private void initView(Context context, AttributeSet attrs) {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置画笔为无锯齿  
        mPaint.setColor(Color.BLACK);//设置画笔颜色  
        mPaint.setStrokeWidth(3.0f);//线宽  
        mPaint.setStyle(Paint.Style.STROKE);//空心效果  
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
