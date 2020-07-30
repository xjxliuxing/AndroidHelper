package com.xjx.apphelper.http;

import androidx.annotation.Nullable;

import com.xjx.apphelper.utils.LogUtil;
import com.xjx.apphelper.utils.ToastUtil;

/**
 * 异常类型的封装
 */
public class ApiException extends Throwable {

    // 错误信息提示
    public static final String NET_UNAVAILABLE = "您的手机未链接网络";
    public static final String NET_ERROR = "网络发生异常";
    public static final String TIME_OUT = "链接超时";
    public static final String USER_CANCELED = "您取消了本次访问";
    public static final String UNKNOWN_ERROR = "发生未知错误";

    private int code;
    private String message;

    public ApiException(String message) {
        this.message = message;
    }

    public ApiException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiException(String message, int code) {
        this.code = code;
        this.message = message;
    }

    /**
     * 弹出提示
     *
     * @param t
     */
    public static void ToastError(Throwable t) {
        ToastUtil.showToast(t.getMessage());
        LogUtil.e("ToastError:" + t.getMessage());
        t.printStackTrace();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
