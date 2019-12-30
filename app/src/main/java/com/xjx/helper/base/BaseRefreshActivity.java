package com.xjx.helper.base;

import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xjx.helper.R;
import com.xjx.helper.utils.refresh.MyRefreshFooter;
import com.xjx.helper.utils.refresh.MyRrfreshHeader;
import com.xjx.helper.utils.refresh.MySmartRefreshLayout;

/**
 * @作者 徐腾飞
 * @创建时间 2019/12/18  20:18
 * @更新者 HongJing
 * @更新时间 2019/12/18  20:18
 * @描述 带刷新的基类Activity
 */
public abstract class BaseRefreshActivity extends BaseTitleActivity implements OnRefreshListener, OnLoadMoreListener {

    protected MySmartRefreshLayout mBaseRefresh;
    private MyRrfreshHeader mBaseRefreshHeader;
    private MyRefreshFooter mBaseRefreshFooter;
    private FrameLayout mRefreshFlContent;

    @Override
    protected int getTitleLayout() {
        return R.layout.base_refresh_activity;
    }

    /**
     * @return 获取刷新界面的布局
     */
    protected abstract int getRefreshLayout();

    @Override
    protected void initView() {
        super.initView();
        // Smartrefresh的根布局
        mBaseRefresh = findViewById(R.id.base_refresh);
        // Smartrefresh的布局头
        mBaseRefreshHeader = findViewById(R.id.base_refresh_header);
        // smartrefresh的脚布局
        mBaseRefreshFooter = findViewById(R.id.base_refresh_footer);
        // smartrefresh 的展示内容的布局
        mRefreshFlContent = findViewById(R.id.fl_refresh_content);

        // 添加布局
        LayoutInflater.from(mContext).inflate(getRefreshLayout(), mRefreshFlContent, true);
    }

    @Override
    protected void initListener() {
        super.initListener();

        // 刷新布局
        if (mBaseRefresh != null) {
            mBaseRefreshHeader.setFinishDuration(0);
            mBaseRefreshFooter.setFinishDuration(0);
        }
    }

    /**
     * 刷新布局的操作
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        Toast.makeText(mContext, "触发刷新事件", Toast.LENGTH_SHORT).show();
        refreshLayout.finishRefresh(2000);
        RequestData();
    }

    /**
     * 加载更多的布局
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore(2000);
    }

    /**
     * 是否显示标题头
     *
     * @param visibility
     */
    protected void setTitleLayout(int visibility) {
        if (mRlTitleRoot != null) {
            mRlTitleRoot.setVisibility(visibility);
        }
    }

}
