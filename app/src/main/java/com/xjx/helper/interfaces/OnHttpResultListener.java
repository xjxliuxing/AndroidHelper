package com.xjx.helper.interfaces;

import com.xjx.helper.http.client.ApiException;

public interface OnHttpResultListener<T> {

    void onHttpSuccess(T t);

    void onHttpFailure(ApiException e);
}
