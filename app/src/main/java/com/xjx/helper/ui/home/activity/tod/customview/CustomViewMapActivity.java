package com.xjx.helper.ui.home.activity.tod.customview;

import android.content.Intent;
import android.view.View;

import com.xjx.helper.R;
import com.xjx.apphelper.base.CommonBaseTitleActivity;
import com.xjx.apphelper.enums.PlaceholderStatus;

/**
 * 自定义view的集合
 */
public class CustomViewMapActivity extends CommonBaseTitleActivity {

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_custom_view_map;
    }

    @Override
    protected void initListener() {
        super.initListener();

        setTitleContent("自定义View的集合");
        SwitchLoadingStatus(PlaceholderStatus.NONE);

        setOnClick(R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6,
                R.id.tv_7);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.tv_1:// 自定义drawableTextView
                intent.setClass(mContext, DrawableTextViewActivity.class);
                break;
            case R.id.tv_2:// 自定义RecycleView布局
                intent.setClass(mContext, CustomRecycleViewActivity.class);
                break;
            case R.id.tv_3:// 自定义RecycleView布局
                intent.setClass(mContext, ViewPagerGirdViewActivity.class);
                break;
            case R.id.tv_4:// 自定义RecycleView布局
                intent.setClass(mContext, LoadProgressActivity.class);
                break;
            case R.id.tv_5:// 自定义RecycleView布局
                intent.setClass(mContext, CustomPassWordActivity.class);
                break;
            case R.id.tv_6:// 自定义RecycleView布局
                intent.setClass(mContext, CustomProgressViewActivity.class);
                break;
            case R.id.tv_7:// 自定义随机布局
                intent.setClass(mContext, CustomRandomLayoutActivity.class);
                break;
        }

        startActivity(intent);
    }
}
