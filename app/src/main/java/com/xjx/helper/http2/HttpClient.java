package com.xjx.helper.http2;

import com.google.gson.Gson;
import com.xjx.helper.BuildConfig;
import com.xjx.helper.implement.ImpRefreshCompleteListener;
import com.xjx.helper.http2.convert.GsonConvert;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 网络管理工具
 */

public class HttpClient {

    private final static int CONNECT_TIMEOUT = 30;
    private final static int READ_TIMEOUT = 30;
    private final static int WRITE_TIMEOUT = 30;
    private static OkHttpClient.Builder builder;
    public static Gson gson = new Gson();

    // 刷新的实现类，因为每次网路请求都会用到，所以就放到之类，保证每次都会被初始化
    public static ImpRefreshCompleteListener completeListener = new ImpRefreshCompleteListener();

    public static synchronized OkHttpClient.Builder getHttpClient() {
        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }
        return builder;
    }

    public static Retrofit createAPIRetrofit() {
        OkHttpClient.Builder builder = getHttpClient();

        OkHttpClient client = builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
//                .addInterceptor(new AutoInterceptor())
                .addInterceptor(new HttpLogInterceptor2())
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConvert.create()) // 自定义，重写转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit;
    }
}

