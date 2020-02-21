package com.xjx.helper.base;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xjx.helper.R;
import com.xjx.helper.enums.PlaceholderStatus;
import com.xjx.helper.http.client.ApiException;
import com.xjx.helper.http.client.BaseResponse;
import com.xjx.helper.utils.views.TextViewUtils;
import com.xjx.helper.widget.PlaceHolderView;

import java.util.List;

/**
 * Activity的扩展了类，自带title布局，也可以使用自定义的title布局
 */
public abstract class CommonBaseTitleActivity extends CommonBaseActivity {

    protected ImageView mIvBack; // 返回箭头
    protected TextView mTvLeft; // 返回文字
    protected TextView mTvTitle; // 标题头
    protected TextView mTvRight; // 右侧标题
    protected FrameLayout mTitleFlContent; // 主布局
    protected RelativeLayout mRlTitleRoot;// title的根布局

    private FrameLayout mFlTitleRightContent; // title右侧的布局
    private PlaceHolderView mPlaceHolderView; // 占位图

    protected boolean mIsFinishActivity = true;// 是否关闭Activity页面,默认直接关闭
    private FrameLayout mFlTopTitleContent;

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

        // 内容之上的布局
        mFlTopTitleContent = findViewById(R.id.fl_top_title_content);

        // 展示内容布局
        mTitleFlContent = findViewById(R.id.fl_content);
        // 占位图的布局
        mPlaceHolderView = findViewById(R.id.placeHolderView);
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
     * 设置标题的内容
     *
     * @param titleContent
     */
    public void setTitleContent(String titleContent) {
        TextViewUtils.setText(mTvTitle, titleContent);
    }

    /**
     * 设置右侧标题
     *
     * @param rightTitle 右侧标题的内容
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
     * @param finishActivity 是否要关闭当前的页面
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

    /**
     * 设置内容布局上的View
     *
     * @param view
     */
    protected void setTopView(View view) {
        mFlTopTitleContent.setVisibility(View.VISIBLE);
        mFlTopTitleContent.addView(view);
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

    /**
     * 占位图加载成功的状态
     *
     * @param response 成功的返回对象
     */
    public void switchPlaceHolderSuccess(BaseResponse response) {
        if (response == null) {
            SwitchLoadingStatus(PlaceholderStatus.EMPTY, "");
        } else {
            Object dataList = response.getReturnDataList();
            if (dataList == null) {
                SwitchLoadingStatus(PlaceholderStatus.EMPTY, "");
            } else {
                if (dataList instanceof List) {
                    List list = (List) dataList;
                    int size = list.size();
                    if (size > 0) {
                        SwitchLoadingStatus(PlaceholderStatus.SUCCESS, "");
                    } else {
                        SwitchLoadingStatus(PlaceholderStatus.EMPTY, "");
                    }
                } else {
                    SwitchLoadingStatus(PlaceholderStatus.SUCCESS, "");
                }
            }
        }
    }

    /**
     * 占位图加载失败的状态
     *
     * @param t 失败的异常
     */
    public void switchPlaceHolderFailure(ApiException t) {
        if (t != null) {
            String message = t.getMessage();
            SwitchLoadingStatus(PlaceholderStatus.ERROR, message);
        }
    }

    /**
     * @param status  占位图的状态
     * @param message 网络返回的具体消息
     */
    protected void SwitchLoadingStatus(PlaceholderStatus status, String message) {
        // 设置布局的状态
        mPlaceHolderView.setPlaceholderState(status, message);
        // 点击重新连接的事件
        mPlaceHolderView.setReloadListener(this::onRequestData);

        switch (status) {
            case LOADING: // 加载中
            case EMPTY:   // 空布局
            case ERROR:   // 错误布局
                // 内容不可见
                mTitleFlContent.setVisibility(View.GONE);
                break;

            case SUCCESS:  // 加载成功
            case NONE:     // 不使用占位图
                //  内容可见
                mTitleFlContent.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * @param status 占位图的状态
     */
    protected void SwitchLoadingStatus(PlaceholderStatus status) {
        SwitchLoadingStatus(status, "");
    }

}
