package com.xjx.helper.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.xjx.helper.utils.LogUtil;

/**
 * @作者 徐腾飞
 * @创建时间 2019/12/18  17:47
 * @更新者 HongJing
 * @更新时间 2019/12/18  17:47
 * @描述 Activity的最底层的基类，所有的Activity都要集成自这个类，便于控制和管理
 */
public abstract class CommonBaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected FragmentActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        // 当前Activity的全路径名称
        String mCanonicalName = getClass().getCanonicalName();
        LogUtil.e("当前的页面：Activvity -> 为: " + mCanonicalName);

        // 设置布局之前的操作
        initLayoutBefore();
        // 设置布局
        setContentView(getLayout());

        // 初始化View
        initView();
        // 初始化事件
        initListener();
        // 初始化数据
        initData();
        // 自动加载一次
        onRequestData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContext = this;
    }

    /**
     * 设置布局之前的操作
     */
    protected void initLayoutBefore() {

    }

    protected abstract int getLayout();

    /**
     * 初始化View
     */
    protected void initView() {
    }

    /**
     * 初始化事件
     */
    protected void initListener() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 请求和刷新网络数据
     */
    protected void onRequestData() {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
