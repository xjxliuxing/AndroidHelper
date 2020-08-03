package com.xjx.helper.ui.home.activity.tod.customviewui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.xjx.apphelper.utils.LogUtil;
import com.xjx.apphelper.utils.ToastUtil;
import com.xjx.helper.R;
import com.xjx.apphelper.base.BaseListAdapter;
import com.xjx.apphelper.base.BaseVH;
import com.xjx.apphelper.base.CommonBaseFragment;

import java.util.ArrayList;
import java.util.List;

public class Vp1Fragment extends CommonBaseFragment {

    private ArrayList<String> list = new ArrayList<>();
    private GridView gv;
    private OwnerManualDetailAdapter adapter;

    public Vp1Fragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_vp1;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        gv = rootView.findViewById(R.id.gv);
    }

    @Override
    protected void initData() {
        super.initData();

        Bundle arguments = getArguments();

        if (arguments != null) {
            list.clear();

            int page = arguments.getInt("page");
            if (page == 0) {
                list = arguments.getStringArrayList("list1");
            } else if (page == 1) {
                list = arguments.getStringArrayList("list2");
            }

            LogUtil.e("page:" + page + "--->:list:" + list);
        }

        if (adapter == null) {
            adapter = new OwnerManualDetailAdapter(mContext, list);
            gv.setAdapter(adapter);
        } else {
            adapter.setList(list);
        }
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.showToast("position:" + position + "---->" + list.get(position));
            }
        });
    }


    public class OwnerManualDetailAdapter extends BaseListAdapter<String> {

        public OwnerManualDetailAdapter(Context mContext, List<String> mList) {
            super(mContext, mList);
        }

        @Override
        public int getLayout() {
            return R.layout.item_vp_gird;
        }

        @Override
        public void bindData(BaseVH viewHolder, String ownerManualBean, int position, View convertView, ViewGroup parent) {
            TextView tv_content = viewHolder.itemView.findViewById(R.id.tv_content);
            tv_content.setText(ownerManualBean);
        }
    }

}
