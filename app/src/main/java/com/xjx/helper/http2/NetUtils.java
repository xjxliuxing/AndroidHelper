package com.xjx.helper.http2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by erge 2019/9/18 11:19
 */
public class NetUtils {

    /**
     * 检查网络是否可用
     */
    public static boolean checkNetwork(Context context) {
        ConnectivityManager con = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission")
        NetworkInfo activeNetworkInfo = con.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
