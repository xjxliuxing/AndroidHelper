package com.xjx.helper.ui.home.activity.tod.mvp;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;
import com.xjx.helper.utils.recyeliview.RecycleUtil;
import com.xjx.helper.utils.recyeliview.RecycleViewBottomDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试LiveData界面
 * <p>
 * 目标：测试数据能不能同步
 */
public class LiveDataTestActivity extends CommonBaseTitleActivity {

    private RecyclerView mRvList;
    private LiveDataAdapter liveDataAdapter;

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_live_data_test;
    }

    @Override
    protected void initView() {
        super.initView();
        SwitchLoadingStatus(PlaceholderStatus.NONE);
        setTitleContent("Mvp测试界面");

        mRvList = findViewById(R.id.rv_list);
    }

    private List<LiveBean> mList = new ArrayList<>();
    private List<LiveBean> mList1 = new ArrayList<>();
    private List<LiveBean> mList2 = new ArrayList<>();
    private List<LiveBean> mList3 = new ArrayList<>();

    @Override
    protected void initData() {
        super.initData();


        for (int i = 0; i < 10; i++) {
            LiveBean liveBean = new LiveBean();
            liveBean.setTitle("我是title " + i);
            liveBean.setNumber(0);
            if (i==1) {
                liveBean.setTag(1);
            } else if (i == 2 ) {
                liveBean.setTag(2);
            } else if (i  == 3) {
                liveBean.setTag(3);
            }
            mList.add(liveBean);
        }


        liveDataAdapter = new LiveDataAdapter(mContext);

        RecycleUtil
                .getInstance(mContext, mRvList)
                .setVertical()
                .setDivder(new RecycleViewBottomDivider(mContext, LinearLayoutManager.VERTICAL, R.color.white, 20))
                .setAdapter(liveDataAdapter);




        mList2.clear();
        for (int i = 0; i < mList.size(); i++) {
            LiveBean liveBean = mList.get(i);
            if (liveBean.getTag() == 2) {
                mList2.add(liveBean);
            }
        }



        mList3.clear();
        for (int i = 0; i < mList.size(); i++) {
            LiveBean liveBean = mList.get(i);
            if (liveBean.getTag() == 3) {
                mList3.add(liveBean);
            }
        }



        findViewById(R.id.tv_test1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveDataAdapter.setList(mList);

            }
        });
        findViewById(R.id.tv_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveDataAdapter.setList(mList2);
            }
        });
        findViewById(R.id.tv_test3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveDataAdapter.setList(mList3);
            }
        });


        MvpModel mvpModel = ViewModelProviders.of(this, new LiveDataViewModelFactory()).get(MvpModel.class);
        mvpModel.getLoginFormState().observe(this, new Observer<LiveDataSource>() {
            @Override
            public void onChanged(LiveDataSource liveDataSource) {

            }
        });

    }
}
