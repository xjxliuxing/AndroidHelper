package com.xjx.helper.utils.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.xjx.helper.global.CommonBaseApp;
import com.xjx.helper.utils.LogUtil;

public class LinearLayoutUtil extends LinearLayout {
    private boolean isMeasure = true;
    private float horizontalScaleValue = CommonBaseApp.getInstance().horizontalScaleValue;
    private float verticalScaleValue = CommonBaseApp.getInstance().verticalScaleValue;

    public LinearLayoutUtil(Context context) {
        super(context);
    }

    public LinearLayoutUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
//        int orientation = getOrientation();
//        setOrientation(orientation);
    }

    public LinearLayoutUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取宽高的比值
        Log.e("xjx", "horizontalScaleValue:" + horizontalScaleValue);
        Log.e("xjx", "verticalScaleValue:" + verticalScaleValue);

        if (isMeasure) {
            // 获取宽高的比值
            Log.e("xjx", "horizontalScaleValue:" + horizontalScaleValue);
            Log.e("xjx", "verticalScaleValue:" + verticalScaleValue);

            // 获取子控件的数量
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();
                // 重新设置宽高的比值

                int width = layoutParams.width;
                LogUtil.e("View的宽度为：" + width);

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
