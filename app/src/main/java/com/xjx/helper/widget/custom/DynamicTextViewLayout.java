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

public class DynamicTextViewLayout extends View {

    private int measuredWidth;
    private int measuredHeight;
    private Paint mPaintLine = new Paint();
    private Paint mPaintText = new Paint();
    private int mPadding;// 边距
    private int mSize;// list中的数量
    private List<String> mList = new ArrayList<>();// 数据集合

    public DynamicTextViewLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public DynamicTextViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        mPaintLine.setColor(ContextCompat.getColor(context, R.color.read1));
        mPaintLine.setStrokeWidth(ConvertUtil.toDp(3));
        mPaintLine.setStyle(Paint.Style.FILL);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setDither(true);

        mPaintText.setColor(ContextCompat.getColor(context, R.color.black));
        mPaintText.setTextSize(ConvertUtil.toSp(18));
        mPaintText.setAntiAlias(true);
        mPaintText.setDither(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();

        mSize = mList.size();

        // padding

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void setDataList(List<String> list) {
        if ((list != null) && (list.size() > 0)) {
            mList = list;
        }
    }
}
