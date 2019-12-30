package com.xjx.helper.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xjx.helper.utils.LogUtil;

/**
 * @作者 徐腾飞
 * @创建时间 2019/12/18  17:47
 * @更新者 HongJing
 * @更新时间 2019/12/18  17:47
 * @描述 Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected static Activity mContext;
    /**
     * 当前Activvity类的全路径类名
     */
    private String mCanonicalName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        this.mCanonicalName = getClass().getCanonicalName();
        LogUtil.e("当前的页面：Activvity -> 为: " + mCanonicalName);

        // 设置布局之前的操作
        initLayoutBefore();
        // 设置布局
        setContentView(getLayout());

        // 初始化View
        initView();
        // 初始化事件
        initListener();
        // 请求数据,首次进入页面，自动刷新一次
        onRequestData();
        // 初始化数据
        initData();
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
     * 请求网络数据
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
