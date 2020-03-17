package com.xjx.helper.adapter.todo;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.xjx.helper.R;
import com.xjx.helper.base.BaseRecycleAdapter;
import com.xjx.helper.base.BaseVH;
import com.xjx.helper.entity.todo.GridLayoutLayoutBean;
import com.xjx.helper.utils.GlideUtil;

public class GridLayoutLayoutAdapter extends BaseRecycleAdapter<GridLayoutLayoutBean> {

    public GridLayoutLayoutAdapter(Activity mContext) {
        super(mContext);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_gridlayoutlayout;
    }

    @Override
    protected void onBind(@NonNull BaseVH holder, GridLayoutLayoutBean data, int position) {

        ImageView imageView = holder.getImageView(R.id.iv_image);
        holder.setTextView(R.id.tv_title, "" + position);

        GlideUtil.getInstance().Load(mContext, data.getUrl(), imageView, 0);
    }
}
