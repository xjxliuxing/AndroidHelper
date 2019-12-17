package com.xjx.helper.utils.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.xjx.helper.R;
import com.xjx.helper.global.App;
import com.xjx.helper.utils.LogUtil;

/**
 * @作者 徐吉星
 * @创建时间 2019/11/21  2:01
 * @更新者 XJX
 * @描述 1：这个是为了适配相对布局创造出来的工具类，使用该布局，可以适配任意的屏幕
 * 2：需要注意是：使用该布局的时候，布局的单位需要改为px，不然就会出现问题
 */
public class RelativeLayoutUtil extends RelativeLayout {

    private boolean isMeasure = true;
    private float horizontalScaleValue = App.getInstance().horizontalScaleValue;
    private float verticalScaleValue = App.getInstance().verticalScaleValue;

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
            Log.e("xjx", "horizontalScaleValue:" + horizontalScaleValue);
            Log.e("xjx", "verticalScaleValue:" + verticalScaleValue);

            // 获取子控件的数量
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
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

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {

//        float titleSize = attrs.getInteger(R.styleable.BaseTitle_title_size, 0);
        int x_width = attrs.getAttributeIntValue(R.styleable.RelativeLayoutUtil_x_width, 0);

        return super.generateLayoutParams(attrs);
    }
}
