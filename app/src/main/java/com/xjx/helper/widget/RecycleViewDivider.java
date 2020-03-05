package com.xjx.helper.widget;

import android.content.Context;
import android.content.res.Resources;
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

            if ((childAdapterPosition == 0) && (childAdapterPosition == parent.getAdapter().getItemCount() - 1)) {
                // 说明只有一个item
                outRect.top = mMarginTop;
                outRect.bottom = mMarginBottom;
            } else {
                if (childAdapterPosition == 0) {
                    // 最上边的条目不要上边距
                    outRect.top = mMarginTop;
                    outRect.bottom = mDividerHeight;
                } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = mMarginBottom;
                } else {
                    outRect.bottom = mDividerHeight;
                }
            }

        } else if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            // 横向
            outRect.top = mMarginTop;
            outRect.bottom = mMarginBottom;

            if ((childAdapterPosition == 0) && (childAdapterPosition == parent.getAdapter().getItemCount() - 1)) {
                // 只有一个itme
                outRect.left = mMarginLeft;
                outRect.right = mMarginRight;
            } else {
                if ((childAdapterPosition == 0)) {
                    // 第一个
                    outRect.left = mMarginLeft;
                    outRect.right = mDividerHeight;
                } else if (childAdapterPosition == parent.getAdapter().getItemCount() - 1) {
                    // 最后一个
                    outRect.right = mMarginRight;
                } else {
                    outRect.right = mDividerHeight;
                }
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
     *
     *
     * @param context     上下文
     * @param orientation 方向
     * @param drawableId  drawable资源
     */

    /**
     * 自定义分割线，drawable类型
     *
     * @param context       上下文
     * @param orientation   方向
     * @param drawableId    drawable资源
     * @param dividerHeight 分割线高度
     * @param other         站位参数，没有任何作用
     */
    public RecycleViewDivider(Context context, int orientation, int drawableId, int dividerHeight, int... other) {
        mContext = context;

        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;

        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = dividerHeight;
    }

    /**
     * 自定义分割线颜色，自定义高度和颜色
     *
     * @param orientation   方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public RecycleViewDivider(Context context, int orientation, int dividerColor, int dividerHeight) {
        this.mContext = context;
        this.mOrientation = orientation;
        this.mDividerHeight = dividerHeight;
        mPaint = new Paint();
        try {
            mPaint.setColor(context.getResources().getColor(dividerColor));
            mPaint.setStyle(Paint.Style.FILL);
        } catch (Exception e) {
            throw new Resources.NotFoundException("没有找到对应的colorId！");
        }

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

    //绘制横向的分割线，其实就是绘制横向的背景色
    private void drawHorizontalLine(Canvas c, RecyclerView parent) {

        if (parent.getAdapter() == null) {
            throw new NullPointerException("适配器不能为空！");
        }

        int bottom = 0;
        // 由于是横向的分割线，所有左右部分，只考虑pading值，剩余空间全部填充
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        // 计算item的个数
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            // 高度 = view本身所在屏幕的位置 减去 自己设置的top值 减去 view的设置的marttop的值
            int top = child.getTop() - mMarginTop - params.topMargin;

            // 如果是drawable的资源文件，则走这里的设置
            if (mDivider != null) {
                // 获取底部的位置，如果是纯色之类的drawable就会返回-1，所有要设置默认值
//                bottom = top + mDivider.getIntrinsicHeight();
//                if (bottom == -1) {
//                    bottom = mDividerHeight;
//                }

                if (i == parent.getAdapter().getItemCount() - 1) {
                    bottom = (child.getBottom()) + (params.bottomMargin) + (mMarginBottom);
                } else {
                    // bottom= view本身所在屏幕上的底部值 加上view本身的marginbottom + 自己设定的dividerHeight
                    bottom = (child.getBottom()) + (params.bottomMargin) + (mDividerHeight);
                }

                // 设置drawable的内容
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            // 如果是单纯的画笔，则走这里的设置
            if (mPaint != null) {

                // 最后一个item，使用自己定义的marginBottom
                if (i == parent.getAdapter().getItemCount() - 1) {
                    bottom = (child.getBottom()) + (params.bottomMargin) + (mMarginBottom);
                } else {
                    // bottom= view本身所在屏幕上的底部值 加上view本身的marginbottom + 自己设定的dividerHeight
                    bottom = (child.getBottom()) + (params.bottomMargin) + (mDividerHeight);
                }
                // 绘制图形
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    //画竖线
    private void drawVerticalLine(Canvas c, RecyclerView parent) {
        if (parent.getAdapter() == null) {
            throw new NullPointerException("适配器不能为空！");
        }

        int right = 0;

        // 画竖线的时候，只关心左右边距的值，上下只使用viewgrounp的pading值
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // 左侧边距==view本身所在的左侧位置 减去 左侧的margin值 减去 自己设定的marginleft的值
            int left = child.getLeft() - params.leftMargin - mMarginLeft;

            if (mDivider != null) {
                if (i == parent.getAdapter().getItemCount() - 1) {
                    // 最后一个itme
                    right = child.getRight() + params.rightMargin + mMarginRight;
                } else {
                    right = child.getRight() + params.rightMargin + mDividerHeight;
                }

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
            if (mPaint != null) {

                if (i == parent.getAdapter().getItemCount() - 1) {
                    // 最后一个itme
                    right = child.getRight() + params.rightMargin + mMarginRight;
                } else {
                    right = child.getRight() + params.rightMargin + mDividerHeight;
                }

                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

}
