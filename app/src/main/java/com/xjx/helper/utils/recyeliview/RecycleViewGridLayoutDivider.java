package com.xjx.helper.utils.recyeliview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * recycleview类型中GridLayoutManager的分割线
 */
public class RecycleViewGridLayoutDivider extends RecyclerView.ItemDecoration {

    private int mDivderWidth; //  左右中间的边距
    private int mDividerHeight;// 默认分割线的高度

    private int mMaxSpanCount = -1; // RecycleView的列数
    private int mChildCount = -1; // RecycleView的child的数量

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getAdapter() == null) {
            throw new IllegalArgumentException("Adapter cannot be null！");
        }

        // 求出列数
        if (mMaxSpanCount == -1) {
            mMaxSpanCount = getSpanCount(parent);
        }
        // 求出litem的数量
        if (mChildCount == -1) {
            mChildCount = parent.getAdapter().getItemCount();
        }
        // 求出当前position
        int itemPosition = parent.getChildAdapterPosition(view);

        if (isLastRaw(parent, itemPosition, mMaxSpanCount, mChildCount)) {
            // 计算出最后一行，如果是最后一行，则不需要绘制底部

            outRect.set(0, 0, mDivderWidth, 0);

        } else if (isLastColum(parent, itemPosition, mMaxSpanCount, mChildCount)) {
            // 根据position计算出最后一列，如果是最后一列，则不需要绘制右边
            outRect.set(0, 0, 0, mDividerHeight);

        } else {
            outRect.set(0, 0, mDivderWidth, mDividerHeight);
        }
    }

    /**
     * @param dividerHeight 上下条目的间距
     * @param divderWidth   左右条目的边距
     */
    public RecycleViewGridLayoutDivider(int dividerHeight, int divderWidth) {
        this.mDivderWidth = divderWidth;
        this.mDividerHeight = dividerHeight;
    }

    /**
     * @param parent recycleView
     * @return 获取列数
     */
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    // 最后一行
    private boolean isLastRaw(RecyclerView parent, int position, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (position >= childCount) {
                // 如果是最后一行，则不需要绘制底部
                return true;
            }
        }
        return false;
    }

    private boolean isLastColum(RecyclerView parent, int position, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((position + 1) % spanCount == 0) {
                // 如果是最后一列，则不需要绘制右边
                return true;
            }
        }
        return false;
    }

}
