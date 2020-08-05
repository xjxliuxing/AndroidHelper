package com.xjx.helper.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 适用于ListView、GridView、等
 *
 * @param <T> 数据的泛型
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> mList;
    protected Context mContext;

    public BaseListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BaseListAdapter( Context mContext,List<T> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseVH viewHolder;
        if (convertView == null) {
            // 拿到view对象
            convertView = View.inflate(mContext, getLayout(), null);
            // view对象赋值
            viewHolder = new BaseVH(convertView);
            // 设置tag
            convertView.setTag(viewHolder);
        } else {
            // 根据tag，获取holder对象
            viewHolder = (BaseVH) convertView.getTag();
        }

        // 设置数据
        T t = mList.get(position);
        bindData(viewHolder, t, position, convertView, parent);

        return convertView;
    }

    // 获取布局对象
    public abstract int getLayout();

    // 设置数据
    public abstract void bindData(BaseVH viewHolder, T t, int position, View convertView, ViewGroup parent);

}
