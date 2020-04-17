package com.xjx.helper.interfaces;

import com.xjx.helper.http.client.ApiException;

import java.util.List;

public interface OnHttpResultListener<T> {

    void onHttpSuccess(List<T> list);

    void onHttpFailure(ApiException e);
}
