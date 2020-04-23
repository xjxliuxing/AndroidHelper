package com.xjx.helper.ui.home.activity.tod.customview;

import android.content.Intent;
import android.view.View;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;

public class CustomViewActivity extends CommonBaseTitleActivity {

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_custom_view2;
    }

    @Override
    protected void initListener() {
        super.initListener();

        setTitleContent("自定义View的集合");
        SwitchLoadingStatus(PlaceholderStatus.NONE);

        findViewById(R.id.tv_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.tv_1:// 自定义drawableTextView
                intent.setClass(mContext, DrawableTextViewActivity.class);
                break;
        }

        startActivity(intent);
    }
}
