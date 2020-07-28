package com.xjx.apphelper.implement;

import com.xjx.apphelper.base.CommonBaseRefreshActivity;
import com.xjx.apphelper.base.CommonBaseRefreshFragment;
import com.xjx.apphelper.interfaces.OnRefreshCompletedListener;

/**
 * 实现完成刷新的操作实现类
 */
public class ImpRefreshCompleteListener implements OnRefreshCompletedListener {

    @Override
    public void onRefreshCompleted() {
        CommonBaseRefreshActivity.RefreshComplete();
        CommonBaseRefreshFragment.RefreshComplete();
    }
}
