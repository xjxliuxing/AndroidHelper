package com.xjx.helper.base;

import android.view.View;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xjx.helper.global.CommonConstant;
import com.xjx.helper.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 带上拉加载下拉刷新的fragment
 */
public abstract class CommonBaseRefreshListFragment<T> extends CommonBaseRefreshFragment implements OnLoadMoreListener {

    /**
     * 数据列表的对象
     */
    private List<T> mList = new ArrayList<>();

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

        // 重置page对象
        CommonConstant.DEFAULT_PAGE = 1;
    }

    @Override
    protected void initListener() {
        super.initListener();
        // 刷新布局
        if (mBaseRefresh != null) {
            // 默认能刷新
            mBaseRefresh.setEnableLoadMore(true);
            // 刷新的操作
            mBaseRefresh.setOnLoadMoreListener(this);
            mBaseRefresh.setEnableLoadMoreWhenContentNotFull(true);//是否在列表不满一页时候开启上拉加载功能
            mBaseRefresh.setEnableOverScrollBounce(true); // 设置是否启用越界回弹
            mBaseRefresh.setEnableFooterFollowWhenNoMoreData(true);//是否在全部加载结束之后Footer跟随内容1.0.4
            mBaseRefresh.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
            mBaseRefresh.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容

        }
    }


    /**
     * 刷新布局的操作
     *
     * @param refreshLayout 刷新布局的对象
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        CommonConstant.DEFAULT_PAGE = 1;
        // 刷新的时候清空集合数据
        mList.clear();

        if (mBaseRefresh != null) {
            mBaseRefresh.resetNoMoreData();//恢复没有更多数据的原始状态 1.0.4（1.1.0删除）
        }
        super.onRefresh(refreshLayout);
    }

    /**
     * 加载更多数据
     *
     * @param refreshLayout 刷新布局的对象
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
     * @param jsonObject 包装对象的object
     * @return 返回一个经过加工过的JsonObject对象
     */
    public Map<String, Object> setPageBody(Map<String, Object> jsonObject) {
        if (jsonObject != null) {
            jsonObject.put("page", CommonConstant.DEFAULT_PAGE);
            jsonObject.put("limit", CommonConstant.DEFAULT_LIMIT);
            return jsonObject;
        } else {
            return null;
        }
    }


    /**
     * 获取数据的集合
     */
    protected List<T> getData() {
        return mList;
    }

    private BaseRecycleAdapter<T> mBaseAdapter;

    protected void setAdapter(BaseRecycleAdapter<T> testAdapter) {
        mBaseAdapter = testAdapter;
    }

    /**
     * 把请求下来的数据设置到集合中去
     *
     * @param list 获取数据的对象
     */
    protected void setData(List<T> list) {

        if (mBaseRefresh == null) {
            return;
        }


        if (list != null) {
            int size = list.size();
            mList.addAll(list);
            mBaseAdapter.notifyDataSetChanged();

//            if (size < CommonConstant.DEFAULT_LIMIT) {

//            if (size <= 0) {
//                mBaseRefresh.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
//            } else {
//            }

            LogUtil.e("page:" + CommonConstant.DEFAULT_PAGE + "--->size:" + size);

            if (size == CommonConstant.DEFAULT_LIMIT) {
                mBaseRefresh.finishLoadMore();
            } else if (size < CommonConstant.DEFAULT_LIMIT) {
                mBaseRefresh.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
            }
        }
    }


}
