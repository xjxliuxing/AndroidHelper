package com.xjx.helper.base;

import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xjx.helper.R;
import com.xjx.helper.global.CommonConstant;
import com.xjx.helper.interfaces.OnRefreshCompletedListener;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.refresh.MyRefreshFooter;
import com.xjx.helper.utils.refresh.MyRrfreshHeader;
import com.xjx.helper.utils.refresh.MySmartRefreshLayout;

import java.util.Map;

/**
 * @作者 徐腾飞
 * @创建时间 2019/12/18  20:18
 * @更新者 HongJing
 * @更新时间 2019/12/18  20:18
 * @描述 带刷新的基类Activity
 */
public abstract class BaseRefreshActivity extends BaseTitleActivity implements OnRefreshLoadMoreListener {

    protected static MySmartRefreshLayout mBaseRefresh;
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
            // 刷新的事件
            mBaseRefresh.setOnRefreshLoadMoreListener(this);
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
        CommonConstant.DEFAULT_PAGE = 1;
        onRequestData();
    }

    /**
     * 加载更多数据
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        // 每次网络递增加1
        CommonConstant.DEFAULT_PAGE++;

        onRequestData();
    }

    /**
     * 如果页面需要分页，则需要使用这个方法，去动态的控制数据，如果不需要分页则不需使用该方法
     *
     * @param jsonObject
     * @return 返回一个经过加工过的JsonObject对象
     */
    public Map<String, Object> setPageBody(Map<String, Object> jsonObject) {
        if (jsonObject != null) {
            jsonObject.put("page", CommonConstant.DEFAULT_PAGE);
            jsonObject.put("limit", CommonConstant.DEFAULT_LIMIT);

            LogUtil.e("jsonObject:" + jsonObject);
            return jsonObject;
        } else {
            return null;
        }
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
            mBaseRefresh.finishLoadMore();
        }
    }

}
