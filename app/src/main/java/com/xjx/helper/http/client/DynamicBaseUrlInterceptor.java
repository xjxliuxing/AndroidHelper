package com.xjx.helper.http.client;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/3/29.
 * 动态改变BaseUrl的拦截器
 */

public class DynamicBaseUrlInterceptor implements Interceptor {
    private HttpUrl newBaseUrl = null;

    @Override
    public Response intercept(Chain chain) throws IOException {

        if (chain != null) {
            //获取request
            Request request = chain.request();
            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldUrl = request.url();

            //获取request的创建者builder
            Request.Builder builder = request.newBuilder()
                    .addHeader("Content-Type", "application/json");

            // 根据指定head的key去获取head的参数
//            List<String> headers = request.headers(NetConstants.BASE_URL_TYPE);
//            if (headers != null && headers.size() > 0) {
//
//                // 获取请求头
//                String headerValue = headers.get(0);
//
//                if (TextUtils.equals(headerValue, NetConstants.RUL_YD)) { // 改变微信登录的BaseURL
//                    newBaseUrl = HttpUrl.parse(BuildConfig.SERVER_URL_YD);
//                } else {
//                    newBaseUrl = oldUrl;
//                }
//
//                //重建新的HttpUrl，修改需要修改的url部分
//                HttpUrl newFullUrl = oldUrl
//                        .newBuilder()
//                        .scheme(newBaseUrl.scheme())//更换网络协议
//                        .host(newBaseUrl.host())//更换主机名
//                        .port(newBaseUrl.port())//更换端口
//                        .build();
//
//                //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
////                builder.removeHeader(TYPE_BASE_URL);
//
//                return chain.proceed(builder.url(newFullUrl).build());
//            } else {
//                return chain.proceed(request);
//            }
        }
        return null;
    }
}
