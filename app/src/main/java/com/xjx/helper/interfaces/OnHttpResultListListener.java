package com.xjx.helper.interfaces;

import com.xjx.helper.http2.ApiException;

import java.util.List;

public interface OnHttpResultListListener<T> {

    void onHttpListSuccess(List<T> list);

    void onHttpListFailure(ApiException e);
}
