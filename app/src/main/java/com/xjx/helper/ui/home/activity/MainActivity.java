package com.xjx.helper.ui.home.activity;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xjx.apphelper.utils.LogUtil;
import com.xjx.helper.R;
import com.xjx.apphelper.base.CommonBaseTitleActivity;
import com.xjx.apphelper.base.CommonFragmentPagerAdapter;
import com.xjx.apphelper.enums.PlaceholderStatus;
import com.xjx.helper.ui.home.fragments.HomeFragment;
import com.xjx.helper.ui.home.fragments.PersonalFragment;
import com.xjx.helper.ui.home.fragments.TodoFragment;


import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */
public class MainActivity extends CommonBaseTitleActivity {

    private List<Fragment> mListFragments = new ArrayList<>();
    private List<String> mListTiltle = new ArrayList<>();
    private ViewPager mVpContent;
    private BottomNavigationView mNavigation;

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();

        setTitleContent("首页");
        SwitchLoadingStatus(PlaceholderStatus.NONE);
//        rv_list = findViewById(R.id.rv_list);

//        View top = LayoutInflater.from(mContext).inflate(R.layout.item_test_1, null, false);
//        top.findViewById(R.id.tv_test).setOnClickListener(this);
//
//        top.findViewById(R.id.tv_test2).setOnClickListener(this);
//        setTopView(top);

        mVpContent = findViewById(R.id.vp_content);
        mNavigation = findViewById(R.id.navigation);
    }

    @Override
    protected void initData() {
        super.initData();

        mListFragments.add(new HomeFragment());
        mListFragments.add(new TodoFragment());
        mListFragments.add(new PersonalFragment());

        mListTiltle.add("首页");
        mListTiltle.add("待办");
        mListTiltle.add("个人中心");

        CommonFragmentPagerAdapter pagerAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), mListFragments, mListTiltle);

        mVpContent.setAdapter(pagerAdapter);
        // 避免重复创建加载数据
        mVpContent.setOffscreenPageLimit(mListFragments.size());
        // viewPager选中的监听
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //设置默认选中item
                mNavigation.getMenu().getItem(position).setChecked(true);
                LogUtil.e("onPageSelected:" + position);
                switch (position) {
                    case 0:
                        setTitleContent("首页");
                        break;

                    case 1:
                        setTitleContent("代办");
                        break;

                    case 2:
                        setTitleContent("个人中心");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 底部导航器选中的监听事件
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    // 首页
                    case R.id.navigation_home:
                        mVpContent.setCurrentItem(0);
                        break;

                    // 待办
                    case R.id.navigation_moment:
                        mVpContent.setCurrentItem(1);
                        break;

                    // 个人中心
                    case R.id.navigation_personal:
                        mVpContent.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }
}
