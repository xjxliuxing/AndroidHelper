package com.xjx.helper.ui.home.activity.todo.customviewui;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.xjx.helper.R;
import com.xjx.helper.base.BaseRecycleAdapter;
import com.xjx.helper.base.BaseVH;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;

import java.util.ArrayList;
import java.util.List;

public class CustomRecycleViewActivity extends CommonBaseTitleActivity {
    private androidx.recyclerview.widget.RecyclerView mRvList;
    private ArrayList<String> mList;

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_custom_recycle_view;
    }

    @Override
    protected void initData() {
        super.initData();
        SwitchLoadingStatus(PlaceholderStatus.NONE);
        setTitleContent("自定义RecycleView布局");

        mRvList = findViewById(R.id.rv_list);

        mList = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            mList.add("" + i);
        }
        Ap ap = new Ap(mContext, mList);

        CustomLayoutManager manager = new CustomLayoutManager(2, 4);
        manager.getRecycleView(mRvList);
        mRvList.setLayoutManager(manager);
        mRvList.setAdapter(ap);


//        mRvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LogUtil.e("----->滑动结束--->dx2:" + dx);
//
//            }
//        });

    }

    class Ap extends BaseRecycleAdapter<String> {
        public Ap(Activity mContext, List<String> mList) {
            super(mContext, mList);
        }

        @Override
        protected int getLayout() {
            return R.layout.item_custom_rv;
        }

        @Override
        protected void onBind(@NonNull BaseVH holder, String data, int position) {
            holder.setTextView(R.id.tv_text, data);
        }
    }
}
