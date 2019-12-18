package com.xjx.helper.utils.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.xjx.helper.global.BaseApp;
import com.xjx.helper.utils.LogUtil;

public class ConstraintLayoutUtil extends ConstraintLayout {

    private boolean isMeasure = true;
    private float horizontalScaleValue = BaseApp.getInstance().horizontalScaleValue;
    private float verticalScaleValue = BaseApp.getInstance().verticalScaleValue;

    public ConstraintLayoutUtil(Context context) {
        super(context);
    }

    public ConstraintLayoutUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
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
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) child.getLayoutParams();
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
