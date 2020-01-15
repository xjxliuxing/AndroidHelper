package com.xjx.helper.http.client;

import android.content.Context;
import android.text.TextUtils;

import com.xjx.helper.http.progress.ProgressResponseBody;
import com.xjx.helper.interfaces.ProgressResponseListener;
import com.xjx.helper.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @作者 徐腾飞
 * @创建时间 2020/1/15  10:32
 * @更新者 HongJing
 * @更新时间 2020/1/15  10:32
 * @描述 封装了具有下载进度的下载工具
 */
public class DownloadUtil {

    private String TAG = "DownLoad";
    // 监听进度的接口
    private ProgressResponseListener mListener;
    private static DownloadUtil util;
    private Context context;
    private OkHttpClient.Builder builder;
    private String mSavePath;// 下载文件里的路径

    public DownloadUtil(Context context, ProgressResponseListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    public void download(String url, String tragetPath) {
        if (TextUtils.isEmpty(url) || (TextUtils.isEmpty(tragetPath))) {
            LogUtil.e(TAG, "下载地址或者下载路径为空");
            return;
        }

        // 这里使用的逻辑都是一样的，所以不去重复的创建
        if (builder == null) {
            builder = new OkHttpClient().newBuilder();
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    //拦截
                    Response response = chain.proceed(chain.request());
                    //包装响应体并返回
                    return response
                            .newBuilder()
                            .body(new ProgressResponseBody(response.body(), mListener))
                            .build();
                }
            });
        }

        //2.建立Request对象,设置参数,请求方式如果是get,就不用设置,默认使用的就是get
        Request request = new Request.Builder()
                .url(url)//设置请求网址
                .get()
                .build();//建立request对象

        //3.创建一个Call对象,参数是request对象,发送请求
        OkHttpClient build = builder.build();
        Call call = build.newCall(request);

        //4.异步请求,请求加入调度
        call.enqueue(new Callback() {
            @Override//请求失败回调
            public void onFailure(Call call, IOException e) {
                if (mListener != null) {
                    mListener.onFailure(e.getMessage());
                    if (!call.isCanceled()) {
                        call.cancel();
                    }
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.body() != null) {
                    // 获取响应体
                    ResponseBody responseBody = response.body();
                    // 获取网络返回的流媒体
                    InputStream inputStream = responseBody.byteStream();
                    // 设置输入文件
                    FileOutputStream outputStream = new FileOutputStream(tragetPath);
                    try {
                        byte[] bytes = new byte[2048];
                        int len = 0;
                        while ((len = inputStream.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, len);
                        }

                    } catch (Exception e) {
                        mListener.onFailure(e.getMessage());
                        if (!call.isCanceled()) {
                            call.cancel();
                        }
                    } finally {
                        inputStream.close();
                        outputStream.close();

                        File file = new File(tragetPath);
                        boolean canRead = file.canRead();
                        boolean canWrite = file.canWrite();

                        LogUtil.e("canRead:" + canRead);
                        LogUtil.e("canWrite:" + canWrite);
                    }
                }
            }
        });
    }

    public String getTargetPath() {
        return mSavePath;
    }

    /**
     * 设置存储下载文件的路径
     *
     * @param mSavePath
     */
    public void setTargetPath(String mSavePath) {
        this.mSavePath = mSavePath;
    }
}
