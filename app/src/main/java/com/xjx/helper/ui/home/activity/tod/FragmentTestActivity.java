package com.xjx.helper.ui.home.activity.tod;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;
import com.xjx.helper.ui.home.fragments.todo.Test1Fragment;
import com.xjx.helper.ui.home.fragments.todo.Test2Fragment;

/**
 * Fragment的测试类
 */
public class FragmentTestActivity extends CommonBaseTitleActivity {

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_fragment_test;
    }

    @Override
    protected void initData() {
        super.initData();

        SwitchLoadingStatus(PlaceholderStatus.NONE);



        getSupportFragmentManager().beginTransaction().add(R.id.fl_content1, new Test1Fragment("传进来的文字")).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.fl_content1, Test1Fragment.newInstance2("传进来的文字")).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content2, Test2Fragment.newInstance("传进来的文字")).commit();

    }
}
