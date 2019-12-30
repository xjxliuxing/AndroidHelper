package com.xjx.helper;

import com.xjx.helper.base.BaseRefreshActivity;
import com.xjx.helper.entity.StoreActivityBean;
import com.xjx.helper.http.client.ApiException;
import com.xjx.helper.http.client.ApiServices;
import com.xjx.helper.http.client.BaseResponse;
import com.xjx.helper.http.client.BaseResponseCallBack;
import com.xjx.helper.http.client.Page;
import com.xjx.helper.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseRefreshActivity {

    @Override
    protected int getRefreshLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitle("Main主界面");

    }

    @Override
    protected void RequestData() {
        super.RequestData();

        Map<String, String> mParameters = new HashMap<>();
        mParameters.put("angent_id", "ff808081647099c101648d5526980084");
        mParameters.put("page", "1");
        mParameters.put("limit", "3");

        ApiServices.test.getStoreActivity(mParameters).enqueue(new BaseResponseCallBack<BaseResponse<Page<StoreActivityBean>>>() {
            @Override
            public void onSuccess(BaseResponse<Page<StoreActivityBean>> response) {
                LogUtil.e("onSuccess:" + response.toString());
            }

            @Override
            public void onFailured(Throwable t) {
                ApiException.onFiled(t);
            }
        });
    }
}
