package com.xjx.helper.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xjx.helper.R;
import com.xjx.helper.base.BaseRecycleAdapter;
import com.xjx.helper.base.BaseVH;
import com.xjx.helper.entity.StoreActivityBean;
import com.xjx.helper.utils.GlideUtil;
import com.xjx.helper.utils.ToastUtil;

public class TestAdapter extends BaseRecycleAdapter<StoreActivityBean> {

    public TestAdapter(Activity mContext) {
        super(mContext);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_test;
    }

    @Override
    protected void onBind(@NonNull BaseVH holder, StoreActivityBean data, int position) {
        // 封面
        GlideUtil.getInstance().Load(mContext, data.getImg(), holder.getImageView(R.id.iv_activity), 0);

        //  显示状态：-1不现实0已结束1进行中
        int showStatus = data.getShowStatus();
        TextView tv_status = holder.getTextView(R.id.tv_status);
        switch (showStatus) {
            case -1:
                tv_status.setVisibility(View.GONE);
                break;

            case 0:
                tv_status.setVisibility(View.VISIBLE);
                tv_status.setText("已结束");
                tv_status.setBackgroundResource(R.drawable.bg_radius_left_50dp_gray);
                break;

            case 1:
                tv_status.setVisibility(View.VISIBLE);
                tv_status.setText("进行中");
                tv_status.setBackgroundResource(R.drawable.bg_radius_left_50dp_gold);
                break;
        }
        // 活动的名字
        holder.setTextView(R.id.tv_activity_title, data.getName());

        holder.getImageView(R.id.iv_share).setOnClickListener(view -> {
            ToastUtil.showToast("分享");
        });
    }

}
