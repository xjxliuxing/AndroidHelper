package com.xjx.helper.http.client;

/**
 * 网络请求接口成功和失败的回调
 *
 * @param <T>
 */

public interface HttpResponseCallBackListener<T> {

    void onSuccess(T response);

    void onFailured(ApiException t);
}
