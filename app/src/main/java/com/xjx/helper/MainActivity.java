package com.xjx.helper;

import com.xjx.helper.base.BaseTitleActivity;

public class MainActivity extends BaseTitleActivity {

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("Main主界面");

    }

}
