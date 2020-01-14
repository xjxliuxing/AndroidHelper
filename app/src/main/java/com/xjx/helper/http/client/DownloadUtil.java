package com.xjx.helper.http.client;

import android.content.Context;

import com.xjx.helper.http.progress.ProgressResponseBody;
import com.xjx.helper.interfaces.ProgressResponseListener;
import com.xjx.helper.utils.LogUtil;

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

public class DownloadUtil {

    private ProgressResponseListener progressResponseListener;

    public DownloadUtil(ProgressResponseListener progressResponseListener) {
        this.progressResponseListener = progressResponseListener;
    }

    public void download(Context context, final String url, File file) {
        //1. 创建OKhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();

        OkHttpClient.Builder builder = HttpClient.getHttpClient();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拦截
                Response response = chain.proceed(chain.request());
                //包装响应体并返回
                return response.newBuilder()
                        .body(new ProgressResponseBody(response.body(), progressResponseListener))
                        .build();
            }
        });

        //2.建立Request对象,设置参数,请求方式如果是get,就不用设置,默认使用的就是get
        Request request = new Request.Builder()
                .url(url)//设置请求网址
                .get()
                .build();//建立request对象

        //3.创建一个Call对象,参数是request对象,发送请求
        Call call = okHttpClient.newCall(request);

        //4.异步请求,请求加入调度
        call.enqueue(new Callback() {
            @Override//请求失败回调
            public void onFailure(Call call, IOException e) {
                LogUtil.e("请求失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                long mTotalLength = responseBody.contentLength();//下载文件的总长度
                LogUtil.e("文件总长度：" + mTotalLength);
                InputStream inp = responseBody.byteStream();
                FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath());

                try {
                    byte[] bytes = new byte[2048];
                    int len = 0;
                    while ((len = inp.read(bytes)) != -1) {
                        currentProgress += len;
                        outputStream.write(bytes, 0, len);

                        LogUtil.e("当前大小为：" + ((currentProgress * 100) / mTotalLength));
                    }
                    LogUtil.e("Get下载成功！");

                } catch (Exception e) {
                    LogUtil.e("Get下载异常");

                } finally {
                    outputStream.close();
                    inp.close();
                    LogUtil.e("流关闭");
                }
            }
        });
    }

    private File getFile() {
        File cacheDir = mContext.getCacheDir();
        File file = new File(cacheDir, "abc.pdf");
        return file;
    }
}
