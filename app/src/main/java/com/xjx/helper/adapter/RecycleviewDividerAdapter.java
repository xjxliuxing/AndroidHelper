package com.xjx.helper.adapter;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.xjx.helper.R;
import com.xjx.helper.base.BaseRecycleAdapter;
import com.xjx.helper.base.BaseVH;

public class RecycleviewDividerAdapter extends BaseRecycleAdapter<String> {

    public RecycleviewDividerAdapter(Activity mContext) {
        super(mContext);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_divider;
    }

    @Override
    protected void onBind(@NonNull BaseVH holder, String data, int position) {
        holder.setTextView(R.id.tv_content, data);
    }
}
