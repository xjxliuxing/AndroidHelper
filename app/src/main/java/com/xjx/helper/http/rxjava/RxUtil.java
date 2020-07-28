package com.xjx.helper.http.rxjava;

import com.google.gson.Gson;
import com.xjx.helper.http.client.ApiException;
import com.xjx.apphelper.http.BaseResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description: RxUtil
 * Creator: yxc
 * date: 2016/9/21 18:47
 */
public class RxUtil {

    public static <T> Observable.Transformer<Response<T>, T> httpResult() {    //compose简化线程
        return new Observable.Transformer<Response<T>, T>() {
            @Override
            public Observable<T> call(Observable<Response<T>> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Func1<Response<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(Response<T> tHttpRespons) {
                                if (tHttpRespons != null) {
                                    boolean successful = tHttpRespons.isSuccessful();
                                    if (successful) {
                                        // 请求成功的操作
                                        T data = tHttpRespons.body();
                                        // String类型的值
                                        if (data instanceof String) {
                                            // 如果是String类型的就直接返回
                                            return createData(data);
                                        } else {
                                            // 其他类型
                                            //  把对象转换成json格式的对象
                                            Gson gson = new Gson();
                                            String toJson = gson.toJson(data);
                                            // 判断json兑现 是否 为空
                                            if (toJson.isEmpty()) {
                                                return Observable.error(new ApiException("http中转换的json对象为空!"));
                                            } else {
                                                // 转换为BaseResponse类，进行数据转换
                                                BaseResponse baseResponse = gson.fromJson(toJson, BaseResponse.class);

                                                if (baseResponse != null) {

                                                    // 检测token是否合法
//                                                    if (!CommonApp.getInstance().isDebug) {
//                                                        // 如果返回的msg == token错误或失效 ,就直接跳转到登录页
//                                                        if (baseResponse.getReturnMsg().equals(BaseResponseCallBack.TOKEN_INVALIDATE)) {
//                                                            if (HttpClient.mAuthorization != null) {
//                                                                HttpClient.mAuthorization.reLogin();
//                                                            }
//                                                        }
//                                                    }

                                                    int returnCode = baseResponse.getReturnCode();
                                                    if (returnCode == 1) {
                                                        // 只有code==1,才是成功，否则就是失败,目前不用去判断其他
                                                        return createData(data);
                                                    } else {
                                                        // 失败发送失败的Msg
                                                        String returnMsg = baseResponse.getReturnMsg();
                                                        return Observable.error(new ApiException(returnMsg, returnCode));
                                                    }
                                                } else {
                                                    return Observable.error(new ApiException("数据解析失败！"));
                                                }
                                            }
                                        }
                                    } else {

                                        // 请求失败的操作,返回不是200操作的原因
                                        String errorMessage = "";
                                        ResponseBody errorBody = tHttpRespons.errorBody();
                                        try {
                                            errorMessage = errorBody.string();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        int code = tHttpRespons.code();
                                        return Observable.error(new ApiException(errorMessage, code));
                                    }
                                } else {
                                    return Observable.error(new ApiException("HttpRequest对象为空！"));
                                }
                            }
                        });
            }
        };
    }

    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (subscriber != null) {
                    try {
                        subscriber.onNext(data);
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
            }
        });
    }

}
