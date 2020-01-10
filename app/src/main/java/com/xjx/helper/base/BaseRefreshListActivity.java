package com.xjx.helper.base;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xjx.helper.global.CommonConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @作者 徐腾飞
 * @创建时间 2020/1/5  21:18
 * @更新者 XJX
 * @更新时间 2020/1/5  21:18
 * @描述 针对列表类型的刷新页面的activity的基类，这种类型的基类只适用于单个列表的刷新，
 * 如果有多个列表刷新的话，建议使用单独的页面去做一些操作
 */
public abstract class BaseRefreshListActivity<T> extends BaseRefreshActivity implements OnLoadMoreListener {
    /**
     * 数据列表的对象
     */
    protected List<T> mList = new ArrayList<>();

    @Override
    protected void initListener() {
        super.initListener();
        // 刷新布局
        if (mBaseRefresh != null) {
            // 默认能刷新
//            mBaseRefresh.setEnableLoadMore(true);
            // 刷新的操作
            mBaseRefresh.setOnLoadMoreListener(this);
            mBaseRefresh.setEnableFooterFollowWhenNoMoreData(true);//是否在全部加载结束之后Footer跟随内容1.0.4
            mBaseRefresh.setEnableLoadMoreWhenContentNotFull(true);
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
        if (mBaseRefresh != null) {
            mBaseRefresh.resetNoMoreData();//恢复没有更多数据的原始状态 1.0.4（1.1.0删除）
        }
        super.onRefresh(refreshLayout);
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

    /**
     * 把请求下来的数据设置到集合中去
     *
     * @param list
     */
    protected void setData(List<T> list) {

        if (mBaseRefresh == null) {
            return;
        }

        // 如果是第一页的话，就清空集合
        if (CommonConstant.DEFAULT_PAGE == 1) {
            mList.clear();
        }

        if (list != null) {
            int size = list.size();
            mList.addAll(list);

            if (size < CommonConstant.DEFAULT_LIMIT) {
                mBaseRefresh.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
            } else {
                mBaseRefresh.finishLoadMore();
            }
        }
    }
}
