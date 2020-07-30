package com.xjx.apphelper.interfaces;

import com.xjx.apphelper.http.ApiException;

import java.util.List;

public interface OnHttpResultListListener<T> {

    void onHttpListSuccess(List<T> list);

    void onHttpListFailure(ApiException e);
}
