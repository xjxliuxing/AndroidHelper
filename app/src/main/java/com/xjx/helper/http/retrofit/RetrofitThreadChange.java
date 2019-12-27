package com.xjx.helper.http.retrofit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by spc on 2017/4/26.
 */

public class RetrofitThreadChange <T> implements Observable.Transformer<T, T> {
    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.subscribeOn(Schedulers.io())
                .retryWhen(new RetryWhenNetworkException())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
