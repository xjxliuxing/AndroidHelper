package com.xjx.helper.http.retrofit;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by spc on 2017/4/26.
 */

public class RetryWhenNetworkException implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private int count = 3;//重新链接次数
    private long delay = 3000;//每次间隔
    private long increaseDelay = 500;//每次递增的时间间隔

    public RetryWhenNetworkException() {

    }


    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable
                .zipWith(Observable.range(1, count + 1), new Func2<Throwable, Integer, Wrapper>() {
                    @Override
                    public Wrapper call(Throwable throwable, Integer integer) {
                        return new Wrapper(throwable, integer);
                    }
                }).flatMap(new Func1<Wrapper, Observable<?>>() {
                    @Override
                    public Observable<?> call(Wrapper wrapper) {

                        if ((wrapper.throwable instanceof ConnectException
                                || wrapper.throwable instanceof UnknownHostException
                                || wrapper.throwable instanceof SocketTimeoutException
                                || wrapper.throwable instanceof TimeoutException))
                            if (wrapper.index < count + 1) { //如果超出重试次数也抛出错误 到error 处理
                                return Observable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);
                            }
                        return Observable.error(wrapper.throwable);
                    }
                });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
