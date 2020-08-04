package com.xjx.helper.ui.home.activity.todo.customviewui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.xjx.helper.R;
import com.xjx.apphelper.base.CommonBaseTitleActivity;
import com.xjx.apphelper.base.CommonFragmentPagerAdapter;
import com.xjx.apphelper.enums.PlaceholderStatus;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerGirdViewActivity extends CommonBaseTitleActivity {

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_view_pager_gird_view;
    }

    @Override
    protected void initData() {
        super.initData();
        setTitleContent("ViewPagerGird");
        SwitchLoadingStatus(PlaceholderStatus.NONE);

        ViewPager vp = findViewById(R.id.vp);

        ArrayList<String> strings = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
            strings.add("" + i);
        }

        ArrayList<Fragment> fragments = new ArrayList<>();

        Vp1Fragment fragment1 = new Vp1Fragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("page", 0);
        List<String> strings1 = strings.subList(0, 8);
        ArrayList<String> strings3 = new ArrayList<>();
        strings3.addAll(strings1);
        bundle1.putStringArrayList("list1", strings3);
        fragment1.setArguments(bundle1);

        Vp1Fragment fragment2 = new Vp1Fragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("page", 1);
        List<String> strings2 = strings.subList(8, strings.size() - 1);
        ArrayList<String> strings4 = new ArrayList<>();
        strings4.addAll(strings2);
        bundle2.putStringArrayList("list2", strings4);
        fragment2.setArguments(bundle2);

        fragments.add(fragment1);
        fragments.add(fragment2);
        CommonFragmentPagerAdapter pagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments, null);
        vp.setAdapter(pagerAdapter);
    }


}


