package com.xjx.helper;

import androidx.recyclerview.widget.RecyclerView;

import com.xjx.helper.adapter.TestAdapter;
import com.xjx.helper.base.CommonBaseRefreshListActivity;
import com.xjx.helper.entity.StoreActivityBean;
import com.xjx.helper.http.client.ApiException;
import com.xjx.helper.http.client.ApiServices;
import com.xjx.helper.http.client.BaseResponse;
import com.xjx.helper.http.client.BaseResponseCallBack;
import com.xjx.helper.http.client.Page;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.RecycleUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends CommonBaseRefreshListActivity<StoreActivityBean> {

    private RecyclerView rv_list;
    private TestAdapter testAdapter;

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
}
