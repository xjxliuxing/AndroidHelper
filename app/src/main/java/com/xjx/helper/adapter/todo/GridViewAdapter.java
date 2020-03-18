package com.xjx.helper.adapter.todo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xjx.helper.R;
import com.xjx.helper.base.BaseListAdapter;
import com.xjx.helper.base.BaseVH;
import com.xjx.helper.entity.todo.GridLayoutLayoutBean;
import com.xjx.helper.utils.GlideUtil;

import java.util.List;

public class GridViewAdapter extends BaseListAdapter<GridLayoutLayoutBean> {

    public GridViewAdapter(Context mContext, List<GridLayoutLayoutBean> mList) {
        super(mContext, mList);
    }

    @Override
    public int getLayout() {
        return R.layout.item_list_gridview;
    }

//    @Override
//    public void bindData(BaseVH viewHolder, GridLayoutLayoutBean bean, int position, View convertView, ViewGroup parent) {
//
//    }

    @Override
    public void bindData(BaseVH viewHolder, GridLayoutLayoutBean bean, int position, View convertView, ViewGroup parent) {
        ImageView imageView = viewHolder.getImageView(R.id.iv_image);
        GlideUtil.getInstance().Load(mContext, bean.getUrl(), imageView, 0);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            convertView = View.inflate(mContext, R.layout.item_list_gridview, null);
//            viewHolder = new ViewHolder();
//
//            viewHolder.imageView = convertView.findViewById(R.id.iv_image);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        GridLayoutLayoutBean bean = mList.get(position);
//
//        GlideUtil.getInstance().Load(mContext, bean.getUrl(), viewHolder.imageView, 0);
//        return convertView;
//    }
//
//    class ViewHolder {
//        ImageView imageView;
//    }

}
