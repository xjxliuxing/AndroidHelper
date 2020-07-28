package com.xjx.helper.http.client;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.xjx.apphelper.global.CommonBaseApp;
import com.xjx.apphelper.http.BaseResponse;
import com.xjx.apphelper.http.HttpClient;
import com.xjx.apphelper.utils.LogUtil;
import com.xjx.apphelper.utils.SpUtil;
import com.xjx.helper.global.CommonConstant;
import java.io.EOFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by spc on 2017/6/17.
 */

public class AutoInterceptor implements Interceptor {

    // 标记状态，默认只有在debug模式下才会打印数据
    private boolean State = (CommonBaseApp.getInstance().isDebug());
    private String TAG = "HttpClient";
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private MediaType mContentType;
    private String string;
    private String method;
    private static final String TYPE_POST = "POST";
    private static final String TYPE_GET = "GET";
    private Charset charset;
    private RequestBody requestBody;
    private Response response;

    @Override
    public Response intercept(Chain chain) throws IOException {

        /**
         * 获取请求体
         */
        // 获取请求对象
        Request request = chain.request();

        // 添加请求头
        String token = SpUtil.getString(CommonConstant.TOKEN);
        token = "39d5bf3e9b5fc7e41f949063fb139309";
        if (!TextUtils.isEmpty(token)) {
            request = request.newBuilder()
                    .addHeader("Authorization", token)
                    .addHeader("plat_number", "1")
                    .build();
        }

        // 获取请求方法
        method = request.method();
        // 获取请求的完整路径，如果是get则包含参数，如果是post就不包含
        HttpUrl url = request.url();//http://127.0.0.1/test/upload/img?userName=xiaoming&userPassword=12345

        // 获取请求体
        requestBody = request.body();
        // 请求体类型
        if (requestBody != null && requestBody.contentType() != null) {
            mContentType = requestBody.contentType();
        }

        long t1 = System.nanoTime();//请求发起的时间

        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            LogUtil.e("拦截器报错：" + e.getMessage());
            throw e;
        }

        // 获取请求网络的时间
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t1);

        // 拦截token验证
        try {
            /**
             * 获取响应体
             */
            //不能直接使用response.body（）.string()的方式输出日志,因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一个新的response给应用层处理
//            ResponseBody responseBody = response.peekBody(1024 * 1024);

            if (response != null) {
                ResponseBody responseBody = response.body();
                // 响应体类型
                MediaType contentType = responseBody.contentType();
                // 设置响应体
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        LogUtil.e(TAG, "拦截器设置字符类型错误：" + e.getMessage());
                        return response;
                    }
                }

                // 拦截token
                Gson gson = HttpClient.gson;
                BaseResponse baseResponse = gson.fromJson(string, BaseResponse.class);
                if (baseResponse != null) {
                    if (baseResponse.getReturnCode() != BaseResponse.REQUEST_SUCCESS) {
                        if ((!TextUtils.isEmpty(baseResponse.getReturnMsg()))) {
                            // 如果返回的msg == token错误或失效 ,就直接跳转到登录页
                            if (baseResponse.getReturnMsg().equals(BaseResponse.TOKEN_INVALIDATE)) {
//                            if (HttpClient.mAuthorization != null) {
//                                HttpClient.mAuthorization.reLogin();
//                            }
                            }
                        }
                    }
                }

                // 获取返回的数据
                if ((HttpHeaders.hasBody(response)) && (responseBody != null)) {
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();

                    if (isPlaintext(buffer)) {
                        string = buffer.clone().readString(charset);
                    } else {
                        return response;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 打印log数据
         */
        //只有在debug模式下才会去打印数据
        if (State) {
            // 请求头
            String heads = getHeards(request);
            // 请求参数
            String parameters = getParameters(request);

            LogUtil.e(TAG, String.format(Locale.CHINA,
                    "请求方式:【 %s 】" +
                            "%n请求地址:【 %s 】" +
                            "%n请求类型:【 %s 】" +
                            "%n请求头  :【 %s 】" +
                            "%n请求参数:【 %s 】" +
                            "%n响应时间:【 %s ms 】" +
                            "%n返回内容:【 %s 】 ",
                    request.method(),
                    url,
                    mContentType,
                    heads,
                    parameters,
                    tookMs,
                    string
            ));
        }
        return response;
    }

    //get请求 添加公共参数 签名
    private static Request addGetParams(Request request) {
        //添加公共参数
        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter("var", "Android" + "...")
                .build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    //post 添加签名和公共参数
    private Request addPostParams(Request request) throws UnsupportedEncodingException {
        if (request.body() instanceof FormBody) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            FormBody formBody = (FormBody) request.body();

            //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }
            formBody = bodyBuilder
                    .addEncoded("var", "Android" + "...")
                    .build();
            request = request.newBuilder().post(formBody).build();
        }
        return request;
    }

    /**
     * @param request
     * @return 获取请求头信息
     */
    private String getHeards(Request request) {

        StringBuilder sb_heads = new StringBuilder();
        Headers headers = request.headers();
        Set<String> names = headers.names();
        if (names != null && names.size() > 0) {
            for (String headName : names) {
                String value = headers.get(headName);
                sb_heads.append(headName + " = ");
                sb_heads.append(value + " ,");
            }
            sb_heads.delete(sb_heads.length() - 1, sb_heads.length());

            return sb_heads.toString();
        }
        return "";
    }

    /**
     * @param request
     * @return 获取post请求方式下的参数信息
     */
    private String getParameters(Request request) {
        String paraments = "";
        // 打印请求的参数
        if (TYPE_POST.equals(method)) {
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                if (isPlaintext(buffer)) {
                    paraments = buffer.readString(charset);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (TextUtils.equals(method, TYPE_GET)) {
            // get方式的亲求参数，入宫是post方式就为空
            HttpUrl url = request.url();
            String encodedQuery = url.encodedQuery();
            if (!TextUtils.isEmpty(encodedQuery)) {
                String replace = encodedQuery.replace("&", ", ");
                paraments = replace;
            }
        }
        return paraments;
    }

    /**
     * Returns true if the body in question probably contains human readable DividerGridItemDecoration. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}
