package com.xjx.helper.base;

import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xjx.helper.R;
import com.xjx.helper.utils.ToastUtil;
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
public abstract class BaseRefreshActivity extends BaseTitleActivity {

    private MySmartRefreshLayout mBaseRefresh;
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
        mBaseRefresh = findViewById(R.id.base_refresh);
        mBaseRefreshHeader = findViewById(R.id.base_refresh_header);
        mBaseRefreshFooter = findViewById(R.id.base_refresh_footer);
        mRefreshFlContent = findViewById(R.id.fl_refresh_content);
    }

    @Override
    protected void initViewAfter() {
        super.initViewAfter();
        getLayoutInflater().inflate(getRefreshLayout(), mRefreshFlContent, true);
    }

    @Override
    protected void initListener() {
        super.initListener();
        if (mBaseRefresh != null) {
            mBaseRefresh.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    ToastUtil.showToast("~~~~~~~~~~~~~");
                }
            });
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

}
