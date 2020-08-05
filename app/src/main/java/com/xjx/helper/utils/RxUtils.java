package com.xjx.helper.utils;

import com.xjx.helper.interfaces.RxThreadListener;

import rx.Observable;

public class RxUtils<T> {

    public RxUtils<T> getInstance() {
        return this;
    }

    /**
     * 异步完成任务后携带需要的数据返回到主线程上面
     *
     * @param threadListener 回调的监听
     */
    public void thread(RxThreadListener<T> threadListener) {
        Observable.create((Observable.OnSubscribe<T>) subscriber -> {
            if (threadListener != null) {
                T t = threadListener.doingBackGround();
                subscriber.onNext(t);
            }
        }).subscribeOn(rx.schedulers.Schedulers.io())
                .unsubscribeOn(rx.schedulers.Schedulers.io())
                .subscribe(t -> {
                    if (threadListener != null) {
                        threadListener.onResult(t);
                    }
                });
    }
}
