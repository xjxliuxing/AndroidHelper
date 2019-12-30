package com.xjx.helper.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import rx.Subscription;

/**
 * Created by erge 2019-09-17 13:52
 */
public class NetCallManager {


    // 存Call的集合：如果直接使用Retrofit的call则存到该集合中
    private final HashMap<String, Call> callsMap = new HashMap<>();
    // 存Subscription的集合：Retrofit配合RxJava使用，请求不再是Call，而是Subscription
    private final HashMap<String, Subscription> subscriptionsMap = new HashMap<>();


    /**
     * 向callsMap中添加一个请求
     *
     * @param callName 该请求的名称
     * @param call     具体的网络请求
     */
    public void putCall(String callName, Call call) {
        if (callsMap.containsKey(callName)) {
            cancelCall(callName);
        }
        callsMap.put(callName, call);
    }

    /**
     * 向subscriptionsMap中添加一个请求
     *
     * @param callName     该请求的名称
     * @param subscription 具体的网络请求
     */
    public void putCall(String callName, Subscription subscription) {
        if (subscriptionsMap.containsKey(callName)) {
            cancelCall(callName);
        }
        subscriptionsMap.put(callName, subscription);
    }

    /**
     * 取消一个网络请求
     *
     * @param callName 该网络请求的名称
     */
    public void cancelCall(String callName) {
        cancelCall(callsMap.get(callName));
        cancelCall(subscriptionsMap.get(callName));
        callsMap.remove(callName);
        subscriptionsMap.remove(callName);
    }

    /**
     * 取消一个网络请求
     *
     * @param call 具体的网络请求
     */
    public void cancelCall(Call call) {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
    }

    /**
     * 取消一个网络请求
     *
     * @param subscription 具体的网络请求
     */
    public void cancelCall(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /**
     * 取消所有的网络请求
     */
    public void cancelAll() {
        Iterator iter = callsMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Call value = (Call) entry.getValue();
            value.cancel();
        }
    }

    /**
     * 获取一个网络请求
     *
     * @param name 网络请求的名称
     */
    public Call getCall(String name) {
        return callsMap.get(name);
    }


}
