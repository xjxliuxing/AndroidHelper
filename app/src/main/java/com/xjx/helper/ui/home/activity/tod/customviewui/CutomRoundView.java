package com.xjx.helper.ui.home.activity.tod.customviewui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 绘制一个圆形的view
 */
public class CutomRoundView extends View {

    private Paint paint;

    public CutomRoundView(Context context) {
        super(context);
        initView(context, null);
    }

    public CutomRoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {

        // 创建一个画笔
        paint = new Paint();
        // 设置画笔颜色
        paint.setColor(Color.BLUE);
        // 设置空心类型
        paint.setStyle(Paint.Style.STROKE);
        // 设置实心的类型
//        paint.setStyle(Paint.Style.FILL);

        // 设置画笔的宽度
        paint.setStrokeWidth(20f);
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        int widthsize = MeasureSpec.getSize(widthMeasureSpec);      //取出宽度的确切数值
//        int widthmode = MeasureSpec.getMode(widthMeasureSpec);      //取出宽度的测量模式
//
//        int heightsize = MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值
//        int heightmode = MeasureSpec.getMode(heightMeasureSpec);    //取出高度的测量模式
//
//
//        // 重新设置view的宽高
//        setMeasuredDimension(widthsize, heightsize);
//    }


//    // 这是因为View的大小不仅由View本身控制，而且受父控件的影响，
//    // 所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//
//    }


//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 花点
//        canvas.drawPoint(50, 50, paint);
//        canvas.drawPoints(new float[]{50, 50, 50, 100, 50, 200}, paint);

        // 划线---横线
//        canvas.drawLine(50, 50, 150, 50, paint);
        // 划线---竖线
//        canvas.drawLine(50, 50, 50, 150, paint);
        // 划线，口字型
        canvas.drawLines(new float[]{
                50, 50, 50, 150,// 左侧竖线
                50, 50, 150, 50,// 上面横线
                150, 50, 150, 150,// 右侧竖线
                50, 150, 150, 150,// 下面横线
                100, 0, 100, 250
        }, paint);

        // 绘制矩形--->根据坐标系绘制
        canvas.drawRect(50, 300, 150, 450, paint);
        // 根据矩形对象绘制矩形
        canvas.drawRect(new Rect(200, 300, 350, 450), paint);

        // 绘制椭圆形
        canvas.drawRoundRect(new RectF(50, 500, 350, 700), 100, 100, paint);

    }
}
