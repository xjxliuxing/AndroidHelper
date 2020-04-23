package com.xjx.helper.ui.home.activity.tod.customview;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;

public class DrawableTextViewActivity extends CommonBaseTitleActivity {

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_drawable_text_view2;
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleContent("DrawableTextView");
        SwitchLoadingStatus(PlaceholderStatus.NONE);

    }
}
