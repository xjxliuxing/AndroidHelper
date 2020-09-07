package com.xjx.helper.ui.home.activity.todo.animation;

import android.content.Intent;
import android.view.View;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;

/**
 * 动画的集合类
 */
public class AnimationMapActivity extends CommonBaseTitleActivity {
    
    @Override
    protected int getTitleLayout() {
        return R.layout.activity_animation_map;
    }
    
    @Override
    protected void initListener() {
        super.initListener();
        setOnClick(R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4);
    }
    
    @Override
    protected void initData() {
        super.initData();
        SwitchLoadingStatus(PlaceholderStatus.NONE);
        
        setTitleContent("自定义动画集合");
    }
    
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_1:
                intent.setClass(mContext, CustomGifViewActivity.class);
                break;
            case R.id.tv_2:
                intent.setClass(mContext, TranslationXActivity.class);
                break;
            case R.id.tv_3:
                intent.setClass(mContext, CustomDialogActivity.class);
                break;
            case R.id.tv_4:
                intent.setClass(mContext, RadialGradientActivity.class);
                break;
        }
        
        startActivity(intent);
    }
}
