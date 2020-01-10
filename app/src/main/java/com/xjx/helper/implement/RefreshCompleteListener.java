package com.xjx.helper.implement;

import com.xjx.helper.base.BaseRefreshActivity;
import com.xjx.helper.interfaces.OnRefreshCompletedListener;

/**
 * 实现完成刷新的操作
 */
public class RefreshCompleteListener implements OnRefreshCompletedListener {

    @Override
    public void onRefreshCompleted() {
        BaseRefreshActivity.RefreshComplete();
    }
}
