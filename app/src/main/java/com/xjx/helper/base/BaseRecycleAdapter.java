package com.xjx.helper.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xjx.helper.interfaces.OnChildClickListener;
import com.xjx.helper.interfaces.OnItemBundleClickListener;
import com.xjx.helper.interfaces.OnItemClickListener;
import com.xjx.helper.utils.LogUtil;

import java.util.List;

/**
 * 基类的RecycleView 的 Adapter,只能做一些赋值的操作，如果是一二些 checkBox之类的操作，就只能使用基础的写法了
 *
 * @param <T> 数据的类型
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseVH> {

    protected Activity mContext;
    protected List<T> mList;
    protected OnItemBundleClickListener mItemBundleClickListener;
    protected OnItemClickListener<T> mItemClickListener;
    protected OnChildClickListener<T> mOnChildClickListener;

    public BaseRecycleAdapter(Activity mContext) {
        this.mContext = mContext;
    }

    public BaseRecycleAdapter(Activity mContext, List<T> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setList(List<T> mList) {
        this.mList = mList;
        if (mList != null) {
            LogUtil.e("------------------------------------------------size: " + mList.size() + " ----------------------------------");
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * @return 返回一个RecycleView的布局
     */
    protected abstract int getLayout();

    /**
     * RecycleView的点击事件,可以通过Bundle传递任意类型数据
     *
     * @param itemBundleClickListener
     */
    public void setItemBundleClickListener(OnItemBundleClickListener itemBundleClickListener) {
        this.mItemBundleClickListener = itemBundleClickListener;
    }

    /**
     * RecycleView的点击事件，只能传递本条目的数据
     *
     * @param itemClickListener
     */
    public void setItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    /**
     * @param onChildClickListener item的子view点击事件
     */
    public void setOnChildClickListener(OnChildClickListener<T> onChildClickListener) {
        this.mOnChildClickListener = onChildClickListener;
    }

    @NonNull
    @Override
    public BaseVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(mContext).inflate(getLayout(), viewGroup, false);
        BaseVH vh = new BaseVH(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVH holder, int position) {
        T t = mList.get(position);
        if (t == null) {
            return;
        }
        onBind(holder, t, position);
    }

    /**
     * 子类必须只需要实现该方法去绑定数据即可
     */
    protected abstract void onBind(@NonNull BaseVH holder, T data, int position);
}
