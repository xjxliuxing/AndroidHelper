package com.xjx.apphelper.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import com.xjx.apphelper.http.progress.ProgressResponseBody;
import com.xjx.apphelper.interfaces.ProgressResponseListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.functions.Consumer;
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
    private OkHttpClient.Builder builder;
    private String mSavePath;// 下载文件里的路径
    private File file;
    private Object tag;
    private boolean isHavePermission = false; // 是否拥有读写权限，如果没有读写权限，则无法下载

    /**
     * @param listener 下载进度的监听
     * @param tag      下载目标的tag，如果是列表下载的话，建议使用position，如果是单个下载，这个用不用无所谓
     */
    public DownloadUtil(ProgressResponseListener listener, Object tag) {
        this(listener, tag, null, false);
        isHavePermission = true;
    }

    @SuppressLint("CheckResult")
    public DownloadUtil(ProgressResponseListener listener, Object tag, FragmentActivity activity, boolean isCheckoutPermissions) {
        this.mListener = listener;
        this.tag = tag;
        if (isCheckoutPermissions) {
            RxPermissions rxPermissions = new RxPermissions(activity);
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean granted) throws Exception {
                    if (granted) {
                        isHavePermission = true;
                    } else {
                        LogUtil.e("至少有一个权限被拒绝！");
                        isHavePermission = false;
                    }
                }
            });
        }
    }

    /**
     * @param url        下载地址的url
     * @param tragetPath 需要保存的路径
     */
    public void download(String url, final String tragetPath) {
        if (TextUtils.isEmpty(url) || (TextUtils.isEmpty(tragetPath))) {
            Log.e(TAG, "下载地址或者下载路径为空");
            return;
        }

        if (!isHavePermission) {
            Log.e(TAG, "没有读写权限，无法下载！");
            return;
        }

        setTargetPath(tragetPath);

        // 这里使用的逻辑都是一样的，所以不去重复的创建
        builder = new OkHttpClient().newBuilder();
        if (mListener != null) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    //拦截
                    Response response = chain.proceed(chain.request());
                    //包装响应体并返回
                    return response
                            .newBuilder()
                            .body(new ProgressResponseBody(response.body(), mListener, tag))
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
        file = new File(tragetPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //4.异步请求,请求加入调度
        call.enqueue(new Callback() {
            @Override//请求失败回调
            public void onFailure(Call call, IOException e) {
                if (mListener != null) {
                    mListener.onFailure(e.getMessage(), tag);
                    if (!call.isCanceled()) {
                        call.cancel();
                    }
                    if (file != null) {
                        file.delete();
                    }
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                FileOutputStream outputStream = null;

                if (response.body() != null) {
                    // 获取响应体
                    ResponseBody responseBody = response.body();
                    // 获取网络返回的流媒体
                    InputStream inputStream = responseBody.byteStream();
                    try {
                        // 设置输入文件
                        outputStream = new FileOutputStream(tragetPath);

                        byte[] bytes = new byte[2048];
                        int len = 0;
                        while ((len = inputStream.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, len);
                        }

                    } catch (Exception e) {
                        LogUtil.e("error:" + e.getMessage());
                        mListener.onFailure(e.getMessage(), tag);
                        if (!call.isCanceled()) {
                            call.cancel();
                        }
                    } finally {
                        inputStream.close();
                        if (outputStream != null) {
                            outputStream.close();
                        }
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
     * @param mSavePath 存储文件的路径
     */
    public void setTargetPath(String mSavePath) {
        this.mSavePath = mSavePath;
    }
}
