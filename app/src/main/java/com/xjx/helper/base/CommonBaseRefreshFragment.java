package com.xjx.helper.base;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xjx.helper.R;
import com.xjx.helper.enums.PlaceholderStatus;
import com.xjx.helper.http.client.ApiException;
import com.xjx.helper.http.client.BaseResponse;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.refresh.MyRefreshFooter;
import com.xjx.helper.utils.refresh.MyRrfreshHeader;
import com.xjx.helper.utils.refresh.MySmartRefreshLayout;
import com.xjx.helper.widget.PlaceHolderView;

import java.util.List;

/**
 * 带刷新的fragment
 */
public abstract class CommonBaseRefreshFragment extends CommonBaseFragment implements OnRefreshListener {

    protected static MySmartRefreshLayout mBaseRefresh;
    private MyRrfreshHeader mBaseRefreshHeader;
    protected MyRefreshFooter mBaseRefreshFooter;
    private FrameLayout mRefreshFlContent;

    private FrameLayout mFlTopTitleContent;
    private PlaceHolderView mPlaceHolderView; // 占位图

    @Override
    protected int getLayout() {
        return R.layout.base_refresh_fragment;
    }

    /**
     * @return 获取刷新界面的布局
     */
    protected abstract int getRefreshLayout();

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        // Smartrefresh的根布局
        mBaseRefresh = rootView.findViewById(R.id.base_refresh);
        // Smartrefresh的布局头
        mBaseRefreshHeader = rootView.findViewById(R.id.base_refresh_header);
        // smartrefresh的脚布局
        mBaseRefreshFooter = rootView.findViewById(R.id.base_refresh_footer);
        // smartrefresh 的展示内容的布局
        mRefreshFlContent = rootView.findViewById(R.id.fl_refresh_content);

        // 内容之上的布局
        mFlTopTitleContent = rootView.findViewById(R.id.fl_top_title_content);
        // 占位图的布局
        mPlaceHolderView = rootView.findViewById(R.id.placeHolderView);

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

    /**
     * 数据加载成功，占位图的状态
     *
     * @param response 成功的返回对象
     */
    public void switchPlaceHolderSuccess(BaseResponse response) {
        if (response == null) {
            LoadingStatus(PlaceholderStatus.EMPTY, "");
        } else {
            Object dataList = response.getReturnDataList();
            if (dataList == null) {
                LoadingStatus(PlaceholderStatus.EMPTY, "");
            } else {
                if (dataList instanceof List) {
                    List list = (List) dataList;
                    int size = list.size();
                    if (size > 0) {
                        LoadingStatus(PlaceholderStatus.SUCCESS, "");
                    } else {
                        LoadingStatus(PlaceholderStatus.EMPTY, "");
                    }
                } else {
                    LoadingStatus(PlaceholderStatus.SUCCESS, "");
                }
            }
        }
    }

    /**
     * 数据加载失败，占位图的状态
     *
     * @param t 失败的异常
     */
    public void switchPlaceHolderFailure(ApiException t) {
        if (t != null) {
            String message = t.getMessage();
            LoadingStatus(PlaceholderStatus.ERROR, message);
        }
    }

    /**
     * @param status  占位图的状态
     * @param message 网络返回的具体消息
     */
    protected void LoadingStatus(PlaceholderStatus status, String message) {
        // 设置布局的状态
        mPlaceHolderView.setPlaceholderState(status, message);
        // 点击重新连接的事件
        mPlaceHolderView.setReloadListener(this::onRequestData);

        switch (status) {
            case LOADING: // 加载中
            case EMPTY:   // 空布局
            case ERROR:   // 错误布局
                // 内容不可见
                mRefreshFlContent.setVisibility(View.GONE);
                break;

            case SUCCESS:  // 加载成功
            case NONE:     // 不使用占位图
                //  内容可见
                mRefreshFlContent.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * @param status 占位图的状态
     */
    protected void LoadingStatus(PlaceholderStatus status) {
        LoadingStatus(status, "");
    }

}
