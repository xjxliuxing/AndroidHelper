package com.xjx.helper.http.logic;

import com.google.gson.JsonObject;
import com.xjx.helper.entity.StoreActivityBean;
import com.xjx.helper.http.client.Page;
import com.xjx.helper.http.client.BaseResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

/**
 * 测试的逻辑类
 */
public interface LogicTest {

    //云店模块---店铺活动
    @Headers("module_type:1")
    @GET("yd/lotteryActivity/getLotteryActivityList")
    Call<BaseResponse<Page<StoreActivityBean>>> getStoreActivity(@QueryName Map<String,Object> map);

}
