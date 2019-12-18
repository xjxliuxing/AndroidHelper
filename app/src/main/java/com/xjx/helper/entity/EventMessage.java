package com.xjx.helper.entity;

import android.os.Bundle;

/**
 * Created by Administrator on 2019/5/31.
 */

public class EventMessage {
    private int code;
    private String msg;
    private Bundle mBundle;

    public EventMessage(int code) {
        this.code = code;
    }

    public EventMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public EventMessage(int code, Bundle mBundle) {
        this.code = code;
        this.mBundle = mBundle;
    }

    public EventMessage(int code, String msg, Bundle mBundle) {
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
        return "EventMessage{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", mBundle=" + mBundle +
                '}';
    }
}
