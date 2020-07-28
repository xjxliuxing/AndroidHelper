package com.xjx.apphelper.base;

import android.view.View;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xjx.apphelper.http.BaseResponse;
import com.xjx.apphelper.http.BaseResponseCallBack;
import com.xjx.apphelper.http.Page;
import com.xjx.helper.http.client.ApiException;
import com.xjx.apphelper.interfaces.OnHttpResultListListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 带上拉加载下拉刷新的fragment
 */
public abstract class CommonBaseRefreshListFragment<T> extends CommonBaseRefreshFragment implements
        OnLoadMoreListener, OnHttpResultListListener {

    private final String TAG = "BaseRefreshList";

    /**
     * 数据列表的对象
     */
    private List<T> mList = new ArrayList<>();

    protected int mPageSize = 10;// 每页的数量
    protected int mPage = 0;//页数

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

    }

    @Override
    protected void initData() {
        super.initData();

        // 默认加载数据
        getHttpResult();
    }

    @Override
    protected void initListener() {
        super.initListener();
        // 刷新布局
        if (mBaseRefresh != null) {
            // 默认能刷新
            mBaseRefresh.setEnableLoadMore(true);
            mBaseRefresh.setEnableRefresh(true);
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
        super.onRefresh(refreshLayout);
        mPage = 0;
        // 刷新的时候清空集合数据
        mList.clear();

        getHttpResult();

        if (mBaseRefresh != null) {
            mBaseRefresh.resetNoMoreData();//恢复没有更多数据的原始状态 1.0.4（1.1.0删除）
        }
    }

    /**
     * 加载更多数据
     *
     * @param refreshLayout 刷新布局的对象
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        // 每次网络递增加1
        ++mPage;
        getHttpResult();
    }


    /**
     * 如果页面需要分页，则需要使用这个方法，去动态的控制数据，如果不需要分页则不需使用该方法
     *
     * @param jsonObject 包装对象的object
     * @return 返回一个经过加工过的JsonObject对象
     */
    public Map<String, Object> setPageBody(Map<String, Object> jsonObject) {
        if (jsonObject != null) {
            jsonObject.put("page", mPage);
            jsonObject.put("limit", mPageSize);
            return jsonObject;
        } else {
            return null;
        }
    }

    private BaseRecycleAdapter<T> mBaseAdapter;

    protected void setAdapter(BaseRecycleAdapter<T> testAdapter) {
        mBaseAdapter = testAdapter;
        mBaseAdapter.setList(mList);
    }

    public abstract Call<BaseResponse<Page<T>>> getHttp();

    /**
     * 手动设置page的size
     *
     * @param pageSize 指定的size
     */
    protected void setPageSize(int pageSize) {
        this.mPageSize = pageSize;
    }

    /**
     * 获取网络数据
     */
    public void getHttpResult() {

        getHttp().enqueue(new BaseResponseCallBack<BaseResponse<Page<T>>>() {
            @Override
            public void onSuccess(BaseResponse<Page<T>> response) {
                // 改变页面状态
                boolean success = switchPlaceHolderSuccess(response);
                if (success) {
                    Page<T> returnDataList = response.getReturnDataList();
                    if (returnDataList != null) {
                        List<T> data = returnDataList.getData();
                        if (data != null && data.size() > 0) {
                            onHttpListSuccess(data);

                            // 添加数据，刷新adapter
                            mList.addAll(data);
                            if (mBaseAdapter != null) {
                                mBaseAdapter.notifyDataSetChanged();
                            }


                            if (data.size() < mPageSize) {
                                mBaseRefresh.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
                            } else {
                                mBaseRefresh.finishLoadMore();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailured(ApiException t) {
                switchPlaceHolderFailure(t);
                onHttpListFailure(t);
            }
        });
    }

}
