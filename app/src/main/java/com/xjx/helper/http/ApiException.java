package com.xjx.helper.http;

import com.xjx.helper.global.BaseApp;
import com.xjx.helper.utils.ToastUtil;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 网络请求给app展示的错误信息类
 * Created by erge 2019/9/18 10:45
 */
public class ApiException extends Throwable {

    // 服务器后台返回的code值
    private String state;
    private String message;

    public ApiException(String message) {
        super(message);
        this.message = message;
    }

    public ApiException(String message, String state) {
        super(message);
        this.state = state;
        this.message = message;
    }

    // 错误信息提示
    public static final String NET_UNAVAILABLE = "您的手机未链接网络";
    public static final String NET_ERROR = "网络发生异常";
    public static final String TIME_OUT = "链接超时";
    public static final String USER_CANCELED = "您取消了本次访问";
    public static final String UNKNOWN_ERROR = "发生未知错误";

    /**
     * 请求异常
     *
     * @param t
     */
    public static void onException(Throwable t) {
        if (!NetUtils.checkNetwork(BaseApp.getContext())) {
            onFiled(new ApiException(NET_UNAVAILABLE));
        } else {
            if (t instanceof SocketException || t instanceof UnknownHostException) {
                onFiled(new ApiException(NET_ERROR)); // 链接错误
            } else if (t instanceof SocketTimeoutException) {
                onFiled(new ApiException(TIME_OUT)); // 超时
            } else if (t instanceof IOException && "Canceled".equals(t.getMessage())) {
                onFiled(new ApiException(USER_CANCELED)); // 用户主动取消
            } else {
                onFiled(new ApiException(UNKNOWN_ERROR)); // 未知错误
            }
        }
    }

    private static void onFiled(Throwable t) {
        ToastUtil.showToast(t.getMessage());
        t.printStackTrace();
    }
}
