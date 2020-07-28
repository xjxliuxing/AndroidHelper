package com.xjx.apphelper.base;

import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xjx.apphelper.R;
import com.xjx.apphelper.utils.LogUtil;
import com.xjx.apphelper.utils.refresh.MyRefreshFooter;
import com.xjx.apphelper.utils.refresh.MyRrfreshHeader;
import com.xjx.apphelper.utils.refresh.MySmartRefreshLayout;

/**
 * @作者 徐腾飞
 * @创建时间 2019/12/18  20:18
 * @更新者 HongJing
 * @更新时间 2019/12/18  20:18
 * @描述 带刷新的基类Activity
 */
public abstract class CommonBaseRefreshActivity extends CommonBaseTitleActivity implements OnRefreshListener {

    protected static MySmartRefreshLayout mBaseRefresh;
    private MyRrfreshHeader mBaseRefreshHeader;
    protected MyRefreshFooter mBaseRefreshFooter;
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
            // 刷新的事件
            mBaseRefresh.setOnRefreshListener(this);
            mBaseRefresh.setEnableLoadMore(false);
            mBaseRefresh.setEnableRefresh(true);
            // 默认不允许上拉加载
            mBaseRefresh.setEnableLoadMore(false);
            // 刷新完成的停留
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
        onRequestData();
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

    /**
     * 刷新完成的操作
     */
    public static void RefreshComplete() {
        if (mBaseRefresh != null) {
            LogUtil.e("使用了刷新完成的操作！");
            mBaseRefresh.finishRefresh();
            mBaseRefresh.resetNoMoreData();//setNoMoreData(false);//恢复上拉状态
        }
    }

}
