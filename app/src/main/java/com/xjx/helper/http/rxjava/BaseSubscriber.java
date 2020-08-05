package com.xjx.helper.http.rxjava;


import com.xjx.helper.global.App;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.ToastUtil;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/15.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        if (!NetworkUtil.isNetworkConnected()) {
            // 如果没有网络，就直接停止发射数据访问
            ToastUtil.showToast( "网络连接失败，请检查网络设置！");
            return;
        } else {
            // 如果有网络，但是网络没法连接，或者是2G网络那种特别慢的情况下，就直接不让使用，否则
            int netWorkType = NetworkUtil.getNetWorkType(App.getContext());
            if (netWorkType == NetworkUtil.NETWORKTYPE_INVALID
                    || netWorkType == NetworkUtil.NETWORKTYPE_WAP
                    || netWorkType == NetworkUtil.NETWORKTYPE_2G) {
                ToastUtil.showToast( "网络连接失败，请检查网络设置！");
                return;
            }
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        if (t != null) {
            onSuccess(t, true);
        }
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e("HttpError:" + e.getMessage());
        onFailed(e);
    }

    @Override
    public void onCompleted() {
        onComplete();
    }

    public abstract void onSuccess(T t);

    public T onSuccess(T t, boolean isSuccess) {
        return t;
    }

    public abstract void onFailed(Throwable e);

    public void onComplete() {

    }

}
