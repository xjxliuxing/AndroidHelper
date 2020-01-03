package com.xjx.helper;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.xjx.helper.adapter.TestAdapter;
import com.xjx.helper.base.BaseRefreshActivity;
import com.xjx.helper.entity.StoreActivityBean;
import com.xjx.helper.global.CommonConstant;
import com.xjx.helper.http.client.ApiException;
import com.xjx.helper.http.client.ApiServices;
import com.xjx.helper.http.client.BaseResponse;
import com.xjx.helper.http.client.BaseResponseCallBack;
import com.xjx.helper.http.client.Page;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.RecycleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseRefreshActivity {

    private RecyclerView rv_list;
    private TestAdapter testAdapter;

    private List<StoreActivityBean> mList = new ArrayList<>();

    @Override
    protected int getRefreshLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("Main主界面");

        rv_list = findViewById(R.id.rv_list);
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

        ApiServices.test.getStoreActivity(setPageBody(map)).enqueue(new BaseResponseCallBack<BaseResponse<Page<StoreActivityBean>>>() {
            @Override
            public void onSuccess(BaseResponse<Page<StoreActivityBean>> response) {
                if (CommonConstant.DEFAULT_PAGE == 1) {
                    mList.clear();
                }
                mList.addAll(response.getReturnDataList().getData());
                testAdapter.setList(mList);
                testAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailured(Throwable t) {
                ApiException.onFiled(t);
            }
        });
    }
}
