package com.xjx.helper.http.client;

import com.xjx.helper.global.CommonBaseApp;
import com.xjx.helper.http.NetUtils;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 网络返回数据的封装
 *
 * @param <T>
 */
public abstract class BaseResponseCallBack<T> implements Callback<T>, HttpResponseCallBackListener<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        // 完成刷新的操作，不管是成功还是失败，都要走入到这个方法中
        HttpClient.completeListener.onRefreshCompleted();

        if (response != null) {
            // 是否网络请求成功
            boolean successful = response.isSuccessful();
            if (successful) {
                T body = response.body();
                // 转换响应类
                if (body instanceof BaseResponse) {
                    BaseResponse responseBody = (BaseResponse) body;
                    // 判断返回值
                    int returnCode = responseBody.getReturnCode();
                    if (returnCode == BaseResponse.REQUEST_SUCCESS) {
                        onSuccess(body);
                    } else {
                        // 返回值不正常的情况
                        onFailured(new ApiException(returnCode, responseBody.getReturnMsg()));
                    }
                } else {
                    // 如果不使用BfaseResponseBody作为返回对象，就直接返回对象
                    onSuccess(body);
                }
            } else {
                // 返回的消息
                String errorMessage = response.message();
                if (response.errorBody() != null) {
                    try {
                        // 返回的错误响应
                        errorMessage = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                int code = response.code();
                // 回调失败接口
                onFailured(new ApiException(code, errorMessage));
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        // 完成刷新的操作，不管是成功还是失败，都要走入到这个方法中
        HttpClient.completeListener.onRefreshCompleted();

        if (!NetUtils.checkNetwork(CommonBaseApp.getContext())) {
            onFailured(new ApiException(ApiException.NET_UNAVAILABLE));
        } else {
            if (t instanceof SocketException || t instanceof UnknownHostException) {
                onFailured(new ApiException(ApiException.NET_ERROR)); // 链接错误
            } else if (t instanceof SocketTimeoutException) {
                onFailured(new ApiException(ApiException.TIME_OUT)); // 超时
            } else if (t instanceof IOException && "Canceled".equals(t.getMessage())) {
                onFailured(new ApiException(ApiException.USER_CANCELED)); // 用户主动取消
            } else {
                onFailured(new ApiException(ApiException.UNKNOWN_ERROR + "：" + t.getMessage())); // 未知错误
            }
        }
    }

}
