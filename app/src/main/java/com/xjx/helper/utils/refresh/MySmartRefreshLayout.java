package com.xjx.helper.utils.refresh;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * SmartRefreshLayout 刷新的扩展类，为了避免以后升级的改动，如果改动就全部在这里进行， 尽量避免改动布局
 */
public class MySmartRefreshLayout extends SmartRefreshLayout {

    public MySmartRefreshLayout(Context context) {
        super(context);
    }

    public MySmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    private void initView() {
        setEnableRefresh(true);// 是否开启下拉刷新功能（默认true）
//        setEnableLoadMore(true);// 是否开启加上拉加载功能（默认false-智能开启）
        setEnableLoadMoreWhenContentNotFull(false);// 在内容不满一页的时候，是否可以上拉加载更多（默认-false）
        setEnableFooterFollowWhenNoMoreData(true); // 是否在全部加载结束之后Footer跟随内容
        finishLoadMoreWithNoMoreData();
        finishRefresh(1);
    }
}
