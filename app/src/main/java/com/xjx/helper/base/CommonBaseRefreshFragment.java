package com.xjx.helper.base;

import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xjx.helper.R;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.refresh.MyRefreshFooter;
import com.xjx.helper.utils.refresh.MyRrfreshHeader;
import com.xjx.helper.utils.refresh.MySmartRefreshLayout;

/**
 * 带刷新的fragment
 */
public abstract class CommonBaseRefreshFragment extends CommonBaseFragment implements OnRefreshListener {

    protected static MySmartRefreshLayout mBaseRefresh;
    private MyRrfreshHeader mBaseRefreshHeader;
    protected MyRefreshFooter mBaseRefreshFooter;
    private FrameLayout mRefreshFlContent;

    @Override
    protected int getLayout() {
        return R.layout.base_refresh_fragment;
    }

    /**
     * @return 获取刷新界面的布局
     */
    protected abstract int getRefreshLayout();

    @Override
    protected void initView() {
        super.initView();
        // Smartrefresh的根布局
        mBaseRefresh = mRootView.findViewById(R.id.base_refresh);
        // Smartrefresh的布局头
        mBaseRefreshHeader = mRootView.findViewById(R.id.base_refresh_header);
        // smartrefresh的脚布局
        mBaseRefreshFooter = mRootView.findViewById(R.id.base_refresh_footer);
        // smartrefresh 的展示内容的布局
        mRefreshFlContent = mRootView.findViewById(R.id.fl_refresh_content);

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
            // 默认不允许上拉加载
            mBaseRefresh.setEnableLoadMore(false);
            mBaseRefresh.setEnableRefresh(true);

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
