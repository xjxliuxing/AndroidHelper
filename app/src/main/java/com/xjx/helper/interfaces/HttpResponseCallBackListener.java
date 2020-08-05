package com.xjx.helper.interfaces;

import com.xjx.helper.http2.ApiException;

/**
 * 网络请求接口成功和失败的回调
 *
 * @param <T>
 */

public interface HttpResponseCallBackListener<T> {

    void onSuccess(T response);

    void onFailured(ApiException t);
}
