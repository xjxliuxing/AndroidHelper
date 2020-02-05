package com.xjx.helper.implement;

import com.xjx.helper.base.CommonBaseRefreshActivity;
import com.xjx.helper.base.CommonBaseRefreshFragment;
import com.xjx.helper.interfaces.OnRefreshCompletedListener;

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
