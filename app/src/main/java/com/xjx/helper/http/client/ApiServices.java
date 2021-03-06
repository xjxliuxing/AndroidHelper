package com.xjx.helper.http.client;

import com.xjx.helper.http.logic.LogicTest;
import com.xjx.helper.http2.HttpClient;

import retrofit2.Retrofit;

public interface ApiServices {

    Retrofit retrofit = HttpClient.createAPIRetrofit();

    LogicTest test = retrofit.create(LogicTest.class);
}
