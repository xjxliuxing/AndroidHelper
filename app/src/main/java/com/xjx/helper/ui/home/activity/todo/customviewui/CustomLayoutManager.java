package com.xjx.helper.ui.home.activity.todo.customviewui;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xjx.helper.utils.LogUtil;

public class CustomLayoutManager extends RecyclerView.LayoutManager {
    private String TAG = "Custom-RV-LM";

    int mRow;// 行
    int mColumn;// 列
    int left, right, top, bottom;
    private int columnPosition;
    private int rowPosition;
    private int maxHeight;
    private int newStateValue;
    private int dx1;
    private int dx2, dx3;
    private int column;
    private RecyclerView.Recycler mRecycler;

    public CustomLayoutManager(int mRow, int mColumn) {
        this.mRow = mRow;
        this.mColumn = mColumn;
    }

    /**
     * 这里定义RecyclerView里面每个item默认的LayoutParams
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        mRecycler = recycler;
        int viewCount = getItemCount();

        if (viewCount <= 0 || state.isPreLayout()) {
            return;
        }
        // 先移除所有view
        detachAndScrapAttachedViews(recycler);
        // 防止view过多产生oom情况，这里我们做了view最大个数的限制，因为没办法像别的LayoutManager那样通过item是否落在屏幕内判断是否回收

        //　这里要注意view要反着加，因为adapter position = 0对应的view我们要显示在最上层
        for (int position = 0; position < viewCount; position++) {
            // 获取到制定位置的view
            final View view = recycler.getViewForPosition(position);
            addView(view);
            // 测量view
            measureChildWithMargins(view, 0, 0);

            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);

            // 每一列的宽度
            column = (width / mColumn);

            /*
             *-------业务逻辑--------
             * 1：第一行的时候，top和bottom不改变
             * 2：其他行就开始递加
             */

            // 计算出当前的行数是几
            int rowForPosition = getRowForPosition(position);
            // 计算出当前position是在第几行的第几个位置
            if (rowForPosition == 0) {
                rowPosition = 0;
            } else {
                rowPosition = position % rowForPosition;
            }

            // 计算出当前的列数是几列
            int columnForPosition = getColumnForPosition(position);
            if (columnForPosition == 0) {
                columnPosition = 0;
            } else {
                // 计算出当前position是在第几列的第几个位置
                columnPosition = position % columnForPosition;
            }

            left = (columnForPosition * column);
            top = (rowForPosition * height);
            right = ((columnForPosition * column) + column);
            bottom = (rowForPosition * height) + height;

            LogUtil.e(TAG, "width:" + width + "--->height:" + height + "--->column:" + column);
            LogUtil.e(TAG, "position:" + position + "--->left:" + left + "--->top:" + (top) + "--->right:" + (right) + "--->bottom:" + (bottom));
            LogUtil.e((TAG + "--->position"), "position:" + position
                    + "\r\n"
                    + "--->当前是第: " + rowForPosition + " 行--->当前行中的列是：" + (rowPosition)
                    + "\r\n"
                    + "--->当前是第: " + (columnForPosition) + " 列--->当前列中的行是:" + (columnPosition));

            layoutDecoratedWithMargins(view, left, top, right, bottom);
        }
    }


    /**
     * @return 获取当前的行数
     */
    private int getRowForPosition(int position) {
        return position / mColumn;
    }

    /**
     * @return 获取当前的列数
     */
    private int getColumnForPosition(int position) {
        return position % mColumn;
    }


    /**
     * 是否可以水平滑动
     */
    @Override
    public boolean canScrollHorizontally() {
        if (getItemCount() > (mRow * mColumn)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //TODO:滑动逻辑
        LogUtil.e(TAG, "dx:" + dx);
        dx1 = dx;

        offsetChildrenHorizontal(-dx);

        return dx;
//        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    public void getRecycleView(RecyclerView mRvList) {
        mRvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                newStateValue = newState;

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    if (dx2 > 0 && dx3 <= 0) {
                        if (dx2 < column) {
                            offsetChildrenHorizontal(dx1);
                        } else {
                            offsetChildrenHorizontal(dx2);
                        }
                    } else if (dx2 <= 0 && Math.abs(dx3) > 0) {
                        offsetChildrenHorizontal(dx3);
                    }

                    dx2 = 0;
                    dx3 = 0;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx < 0) {
                    dx3 += dx;
                } else {
                    dx2 += dx;
                }

                LogUtil.e(TAG, "dx" + dx + "--->dx2--->" + dx2 + "---dx3:" + dx3);
            }
        });
    }
}
