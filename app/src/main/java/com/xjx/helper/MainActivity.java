package com.xjx.helper;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xjx.helper.adapter.TestAdapter;
import com.xjx.helper.base.CommonBaseRefreshListActivity;
import com.xjx.helper.entity.StoreActivityBean;
import com.xjx.helper.http.client.ApiException;
import com.xjx.helper.http.client.ApiServices;
import com.xjx.helper.http.client.BaseResponse;
import com.xjx.helper.http.client.BaseResponseCallBack;
import com.xjx.helper.http.client.Page;
import com.xjx.helper.ui.DownLoadActivity;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.RecycleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends CommonBaseRefreshListActivity<StoreActivityBean> {

    private RecyclerView rv_list;
    private TestAdapter testAdapter;
    private TextView tv_test;

    @Override
    protected int getRefreshLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("Main主界面");

        rv_list = findViewById(R.id.rv_list);

        View top = LayoutInflater.from(mContext).inflate(R.layout.item_test_1, null, false);
        top.findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, DownLoadActivity.class);
                startActivity(intent);
            }
        });
        setTopView(top);
    }

    @Override
    protected String getTitleContent() {
        return "首页";
    }

    @Override
    protected void initData() {
        super.initData();

        testAdapter = new TestAdapter(mContext);
        RecycleUtil
                .getInstance(mContext, rv_list)
                .setVertical()
                .setAdapter(testAdapter);
    }

    @Override
    protected void onRequestData() {

        Map<String, Object> map = new HashMap<>();
        map.put("angent_id", "ff808081647099c101648d5526980084");

        Map<String, Object> stringObjectMap = setPageBody(map);
        LogUtil.e("map:" + stringObjectMap);

        ApiServices.test.getStoreActivity(stringObjectMap).enqueue(new BaseResponseCallBack<BaseResponse<Page<StoreActivityBean>>>() {
            @Override
            public void onSuccess(BaseResponse<Page<StoreActivityBean>> response) {
                switchPlaceHolderSuccess(response);
                List<StoreActivityBean> data = response.getReturnDataList().getData();
                setData(data);
                testAdapter.setList(getData());
            }

            @Override
            public void onFailured(ApiException t) {
                switchPlaceHolderFailure(t);
                ApiException.onFiled(t);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_test:
                intent.setClass(mContext, DownLoadActivity.class);

                break;
        }
        startActivity(intent);
    }
}
