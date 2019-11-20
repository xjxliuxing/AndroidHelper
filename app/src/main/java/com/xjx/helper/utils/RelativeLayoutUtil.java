package com.xjx.helper.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class RelativeLayoutUtil extends RelativeLayout {

    private boolean isMeasure = true;

    public RelativeLayoutUtil(Context context) {
        super(context);
    }

    public RelativeLayoutUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativeLayoutUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (isMeasure) {
            // 获取宽高的比值
            float horizontalScaleValue = LayoutUtil.getInstance(getContext()).getHorizontalScaleValue();
            Log.e("xjx", "horizontalScaleValue:" + horizontalScaleValue);
            float verticalScaleValue = LayoutUtil.getInstance(getContext()).getVerticalScaleValue();
            Log.e("xjx", "verticalScaleValue:" + verticalScaleValue);

            // 获取子控件的数量
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                // 重新设置宽高的比值
                layoutParams.width = (int) (layoutParams.width * horizontalScaleValue);
                layoutParams.height = (int) (layoutParams.height * verticalScaleValue);

                // 重新设置view的margin值
                layoutParams.leftMargin = (int) (layoutParams.leftMargin * horizontalScaleValue);
                layoutParams.rightMargin = (int) (layoutParams.rightMargin * horizontalScaleValue);
                layoutParams.topMargin = (int) (layoutParams.topMargin * verticalScaleValue);
                layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * verticalScaleValue);

                // 设置View的padding值
                child.setPadding((int) (child.getPaddingLeft() * horizontalScaleValue), (int) (child.getPaddingTop() * verticalScaleValue), (int) (child.getPaddingRight() * horizontalScaleValue), (int) (child.getPaddingBottom() * verticalScaleValue));
            }

            // 设置布局的padding值
            setPadding((int) (getPaddingLeft() * horizontalScaleValue), (int) (getPaddingTop() * verticalScaleValue), (int) (getPaddingRight() * horizontalScaleValue), (int) (getPaddingBottom() * verticalScaleValue));

            isMeasure = false;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
