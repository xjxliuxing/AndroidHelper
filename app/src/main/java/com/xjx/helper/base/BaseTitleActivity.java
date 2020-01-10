package com.xjx.helper.base;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xjx.helper.R;
import com.xjx.helper.utils.views.TextViewUtils;

/**
 * Activity的扩展了类，自带title布局，也可以使用自定义的title布局
 */
public abstract class BaseTitleActivity extends CommonBaseActivity {

    protected ImageView mIvBack; // 返回箭头
    protected TextView mTvLeft; // 返回文字
    protected TextView mTvTitle; // 标题头
    protected TextView mTvRight; // 右侧标题
    protected FrameLayout mTitleFlContent; // 主布局
    protected RelativeLayout mRlTitleRoot;// title的根布局
    private FrameLayout mFlTitleRightContent;

    protected boolean mIsFinishActivity = true;// 是否关闭Activity页面,默认直接关闭

    @Override
    protected int getLayout() {
        return R.layout.base_title_activity;
    }

    @Override
    protected void initView() {
        super.initView();

        // title的跟布局
        mRlTitleRoot = findViewById(R.id.rl_title_root);
        // 返回箭头
        mIvBack = findViewById(R.id.iv_title_back);
        // 返回文字
        mTvLeft = findViewById(R.id.tv_title_left);
        // 标题头
        mTvTitle = findViewById(R.id.tv_title);
        // 右侧标题的父布局
        mFlTitleRightContent = findViewById(R.id.fl_title_right_content);
        // 右侧标题
        mTvRight = findViewById(R.id.tv_title_right);

        // 展示内容布局
        mTitleFlContent = findViewById(R.id.fl_content);

        // 添加布局
        getLayoutInflater().inflate(getTitleLayout(), mTitleFlContent, true);
    }

    /**
     * @return 获取布局
     */
    protected abstract int getTitleLayout();

    @Override
    protected void initListener() {
        super.initListener();
        mIvBack.setOnClickListener(this);
        mFlTitleRightContent.setOnClickListener(this);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitle(String title) {
        TextViewUtils.setText(mTvTitle, title);
    }

    /**
     * 设置右侧标题
     *
     * @param rightTitle
     */
    protected void setRightTitle(String rightTitle) {
        TextViewUtils.setText(mTvRight, rightTitle);

        if (!TextUtils.isEmpty(rightTitle)) {
            if (mFlTitleRightContent != null) {
                mFlTitleRightContent.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置是否关闭页面
     *
     * @param finishActivity
     */
    public void setFinishActivity(boolean finishActivity) {
        mIsFinishActivity = finishActivity;
    }

    /**
     * 关闭Activity前的操作
     */
    protected void onFinishActivity() {

    }

    /**
     * 右侧标题的点击事件
     */
    protected void onRightTitleClick() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                // 返回键
                onFinishActivity();
                if (mIsFinishActivity) {
                    finish();
                }
                break;

            case R.id.fl_title_right_content:
                // 右侧标题
                onRightTitleClick();
                break;
        }
    }
}
