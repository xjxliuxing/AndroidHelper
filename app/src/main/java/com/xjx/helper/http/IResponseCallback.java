package com.xjx.helper.http;

/**
 * Created by erge 2019/9/18 10:53
 */
public interface IResponseCallback<T> {

    void onSuccess(T response);
    void onFailure(ApiException error);

}
