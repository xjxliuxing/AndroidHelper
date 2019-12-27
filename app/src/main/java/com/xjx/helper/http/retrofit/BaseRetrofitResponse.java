package com.xjx.helper.http.retrofit;

import java.io.Serializable;

/**
 * Created by spc on 2017/4/26.
 * retrofit 返回统一处理
 */

public class BaseRetrofitResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status;
    private String msg;
    private T styles;
    private int fengtianflag;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return styles;
    }

    public void setResult(T result) {
        this.styles = result;
    }

    public int getFengtianflag() {
        return fengtianflag;
    }

    public void setFengtianflag(int fengtianflag) {
        this.fengtianflag = fengtianflag;
    }
}