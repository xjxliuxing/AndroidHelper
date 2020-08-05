package com.xjx.helper.base;

import android.os.Bundle;

/**
 * Created by Administrator on 2019/5/31.
 */

public class BaseEventMessage {
    private int code;
    private String msg;
    private Bundle mBundle;

    public BaseEventMessage(int code) {
        this.code = code;
    }

    public BaseEventMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseEventMessage(int code, Bundle mBundle) {
        this.code = code;
        this.mBundle = mBundle;
    }

    public BaseEventMessage(int code, String msg, Bundle mBundle) {
        this.code = code;
        this.msg = msg;
        this.mBundle = mBundle;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Bundle getmBundle() {
        return mBundle;
    }

    public void setmBundle(Bundle mBundle) {
        this.mBundle = mBundle;
    }

    @Override
    public String toString() {
        return "BaseEventMessage{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", mBundle=" + mBundle +
                '}';
    }
}
