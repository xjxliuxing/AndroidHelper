package com.xjx.helper.adapter;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.xjx.helper.R;
import com.xjx.helper.base.BaseRecycleAdapter;
import com.xjx.helper.base.BaseVH;

/**
 * @作者 徐腾飞
 * @创建时间 2020/3/10  10:44
 * @更新者 HongJing
 * @更新时间 2020/3/10  10:44
 * @描述 下载进度的adapter
 */
public class DownLoadAdapter extends BaseRecycleAdapter<String> {

    public DownLoadAdapter(Activity mContext) {
        super(mContext);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_download;
    }

    @Override
    protected void onBind(@NonNull BaseVH holder, String data, int position) {

        holder.getTextView(R.id.tv_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.getView(R.id.progress), position, data);
                }
            }
        });
    }

}
