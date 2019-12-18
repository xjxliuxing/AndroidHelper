package com.xjx.helper;

import com.xjx.helper.base.BaseRefreshActivity;

public class MainActivity extends BaseRefreshActivity {

    @Override
    protected int getRefreshLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("Main主界面");

    }

}
