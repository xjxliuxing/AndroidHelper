package com.xjx.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xjx.helper.utils.LogUtil;

public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    //列表的方向：LinearLayoutManager.VERTICAL 或 LinearLayoutManager.HORIZONTAL,默认的是垂直方向的
    private int mOrientation = LinearLayoutManager.VERTICAL;

    private Context mContext;
    private int mMarginLeft;
    private int mMarginTop;
    private int mMarginRight;
    private int mMarginBottom;
    private int mDividerHeight = 2;// 默认分割线的高度为2px
    private Paint mPaint;

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    // 分割线的对象
    private Drawable mDivider;

    /**
     * step1 相当于itemView外还有一个矩形，我们可以自由在itemview上下左右设置空余部分，
     * 通过outRect 的left,top,right,bottom 来进行设置，我们可在这块控件进行绘制
     * 目标针对每一个item个体
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getAdapter() == null) {
            throw new IllegalArgumentException("Adapter cannot be null！");
        }

        int childAdapterPosition = parent.getChildAdapterPosition(view);

        if (mOrientation == LinearLayoutManager.VERTICAL) {
            //  竖向
            outRect.left = mMarginLeft;
            outRect.right = mMarginRight;

            if (childAdapterPosition == 0) {
                // 最上边的条目不要上边距
                outRect.top = mMarginTop;
                outRect.bottom = mDividerHeight;
            } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = mMarginBottom;
            } else {
                outRect.bottom = mDividerHeight;
            }

        } else if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            // 横向
            outRect.top = mMarginTop;
            outRect.bottom = mMarginBottom;
            if ((childAdapterPosition == 0)) {

                outRect.left = mMarginLeft;
                outRect.right = mDividerHeight;
            } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                outRect.right = mMarginRight;
            } else {
                outRect.right = mDividerHeight;
            }

        }
        LogUtil.e("left:" + mMarginLeft + "   right:" + mMarginRight + "  top:" + mMarginTop + "  bottom:" + mDividerHeight);
    }

    //绘制分割线，其实就是在固定的位置绘制背景
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (mOrientation == LinearLayoutManager.VERTICAL) {
            // 绘制横向的分割线
            drawHorizontalLine(c, parent);
        } else if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            // 绘制竖向的分割线
            drawVerticalLine(c, parent);
        }
    }

    /**
     * 使用默认的分割线
     *
     * @param context 上下文
     */
    public RecycleViewDivider(Context context, int orientation) {
        mContext = context;
        setDefaultValue();

        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 自定义分割线，drawable类型
     *
     * @param context     上下文
     * @param orientation 方向
     * @param drawableId  drawable资源
     */
    public RecycleViewDivider(Context context, int orientation, int drawableId) {
        mContext = context;
        setDefaultValue();

        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;

        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线颜色，自定义高度和颜色
     *
     * @param orientation   方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
        setDefaultValue();
        this.mContext = context;
        this.mOrientation = orientation;
        this.mDividerHeight = dividerHeight;
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(dividerColor));
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setOrientation(int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;
    }

    /**
     * 设置margin的值
     *
     * @param left   如果是横向布局，则针对第一个左侧的item，如果是纵向的则针对每一个item左侧
     * @param top    如果是横向的布局，则针对每个itme的顶部，如果是纵向的则针对第一个itme的高度
     * @param right  如果是横向，则针对最后一个itme的右侧间距，如果是纵向的则针对每一个item的右侧间距
     * @param bottom 如果是横向，则针对每一个item的下边距，如果是纵向的，则针对最后一个item的下边距
     */
    public void setMargin(int divider, int left, int top, int right, int bottom) {
        this.mMarginLeft = left;
        this.mMarginTop = top;
        this.mMarginRight = right;
        this.mMarginBottom = bottom;
        this.mDividerHeight = divider;
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    private void drawHorizontalLine(Canvas c, RecyclerView parent) {

        // 左右固定，这里只考虑画item下面的横线，左右不考虑
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        // 计算item的个数
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 确定开始的高度位置== view的高度+view的margin值
            int top = child.getBottom() + params.bottomMargin;

            // 如果是drawable的资源文件，则走这里的设置
            if (mDivider != null) {
                // 获取底部的位置，如果是纯色之类的drawable就会返回-1，所有要设置默认值
                int bottom = top + mDivider.getIntrinsicHeight();
                if (bottom == -1) {
                    bottom = mMarginBottom;
                }
                // 设置drawable的内容
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            // 如果是单纯的画笔，则走这里的设置
            if (mPaint != null) {
                c.drawRect(left, top, right, mMarginBottom, mPaint);
            }
        }
    }

    //画竖线
    private void drawVerticalLine(Canvas c, RecyclerView parent) {
        // 画竖线的时候不用处理上下的边距
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 只在右侧画线，左侧不管
            final int left = child.getRight() + params.rightMargin;
            if (mDivider != null) {
                int intrinsicWidth = mDivider.getIntrinsicWidth();
                if (intrinsicWidth == -1) {
                    intrinsicWidth = 2;// 如果获取的是-1，就设置默认的2px
                }
                int right = left + intrinsicWidth;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            if (mPaint != null) {
                c.drawRect(left, top, mMarginBottom, bottom, mPaint);
            }
        }
    }

    private void setDefaultValue() {
        this.mMarginLeft = 0;
        this.mMarginRight = 0;
        this.mMarginBottom = 2;
    }
}
