package com.xjx.helper.ui.home.activity.todo.customviewui;

import com.xjx.helper.R;
import com.xjx.apphelper.base.CommonBaseTitleActivity;
import com.xjx.apphelper.enums.PlaceholderStatus;

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
