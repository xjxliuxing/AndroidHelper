package com.xjx.helper.ui.home.activity.tod;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseActivity;
import com.xjx.helper.utils.LogUtil;

/**
 * Activity的测试页面
 */
public class ActivtyTestActivity extends CommonBaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_activty_test;
    }

    @Override
    protected void initData() {
        super.initData();

        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();

        for (int i = 0; i < 5; i++) {
            buffer.append(i + " ");
        }
        LogUtil.e("正序：" + buffer.toString());


        for (int i = 5 - 1; i > -1; i--) {
            buffer2.append(" " + i);
        }
        LogUtil.e("倒叙：" + buffer2.toString());

    }
}
