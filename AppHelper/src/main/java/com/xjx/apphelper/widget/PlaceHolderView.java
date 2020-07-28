package com.xjx.apphelper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.xjx.apphelper.R;
import com.xjx.apphelper.enums.PlaceholderStatus;
import com.xjx.apphelper.interfaces.OnClickListeners;

/**
 * 加载页面占位图
 */
public class PlaceHolderView extends FrameLayout {

    private Group mGroupLoading;
    private ImageView mIvNoData;
    private TextView mTvEmpty;
    private TextView mTvError;
    protected TextView mTvReload;
    private ConstraintLayout mClPlaceHolder;

    public PlaceHolderView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public PlaceHolderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        // 把自定义的布局添加到站位图中去
        View inflate = LayoutInflater.from(context).inflate(R.layout.common_base_place_holder, this);
        // 根布局
        mClPlaceHolder = inflate.findViewById(R.id.cl_place_holder);
        // 加载中的布局
        mGroupLoading = inflate.findViewById(R.id.group_loading);
        // 数据异常的icon
        mIvNoData = inflate.findViewById(R.id.iv_no_data);
        // 空数据的布局
        mTvEmpty = inflate.findViewById(R.id.tv_empty);
        // 数据错误的布局
        mTvError = inflate.findViewById(R.id.tv_error);
        // 重新刷新的按钮
        mTvReload = inflate.findViewById(R.id.tv_reload);

        // 重连的点击事件
        mTvReload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClick();
                }
            }
        });
    }

    /**
     * @param status  占位图的状态
     * @param message 网络返回的具体消息
     */
    public void setPlaceholderState(PlaceholderStatus status, String message) {

        switch (status) {
            // 加载中的状态
            case LOADING:
                mClPlaceHolder.setVisibility(VISIBLE);
                mGroupLoading.setVisibility(View.VISIBLE);
                mIvNoData.setVisibility(View.GONE);
                mTvEmpty.setVisibility(View.GONE);
                mTvError.setVisibility(View.GONE);
                mTvReload.setVisibility(View.GONE);
                break;

            // 正常加载成功状态的显示 和 不使用占位图的状态显示
            case SUCCESS:
            case NONE:
                mClPlaceHolder.setVisibility(GONE);
                mGroupLoading.setVisibility(View.GONE);
                mIvNoData.setVisibility(View.GONE);
                mTvEmpty.setVisibility(View.GONE);
                mTvError.setVisibility(View.GONE);
                mTvReload.setVisibility(View.GONE);

                break;
            case EMPTY:
                mClPlaceHolder.setVisibility(VISIBLE);
                mGroupLoading.setVisibility(View.GONE);
                mIvNoData.setVisibility(View.VISIBLE);
                mTvEmpty.setVisibility(View.VISIBLE);
                mTvEmpty.setText("暂无数据哦~");
                mTvError.setVisibility(View.GONE);
                mTvReload.setVisibility(View.VISIBLE);
                break;

            case ERROR:
                mClPlaceHolder.setVisibility(VISIBLE);
                mGroupLoading.setVisibility(View.GONE);
                mIvNoData.setVisibility(View.VISIBLE);
                mTvEmpty.setVisibility(View.GONE);
                mTvError.setVisibility(View.VISIBLE);
                mTvError.setText(message);
                mTvReload.setVisibility(View.VISIBLE);
                break;
        }
    }

    private OnClickListeners mClickListener;

    public void setReloadListener(OnClickListeners onClickListener) {
        mClickListener = onClickListener;
    }
}
