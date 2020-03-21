package com.xjx.helper.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.Timer;
import java.util.TimerTask;


public class NetworkCurrentKbUtil {

    private Context context;
    private static TimerTask task;
    private static Timer timer;
    private static OnNetWorkChangeListener mOnNetWorkChangeListener;
    private int what = 1976;
    private long oldKb;
    private static boolean isPerform = false;

    public static NetworkCurrentKbUtil getInstance(Context context) {
        NetworkCurrentKbUtil networkUtil = new NetworkCurrentKbUtil(context);
        return networkUtil;
    }

    private NetworkCurrentKbUtil(Context context) {
        this.context = context;
    }

    /**
     * 获取当前网络的使用情况，返回当前的字节数，单位：kb
     *
     * @param onNetWorkChangeListener 接口对象
     */
    public synchronized void getCurrentNetworkSpeed(OnNetWorkChangeListener onNetWorkChangeListener) {

        if (!isPerform) {

            // 第一次获取字节数
            oldKb = getUidRxBytes();
            if (oldKb > 0) {

                mOnNetWorkChangeListener = onNetWorkChangeListener;

                if (task == null) {
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            LogUtil.e("old:kb:" + oldKb);
                            // 获取最新的字节数
                            long newKb = getUidRxBytes();
                            LogUtil.e("newKb:kb:" + newKb);

                            // 得到字节数的差值，也就是当前的网速
                            long currentKb = newKb - oldKb;

                            LogUtil.e("当前的网速是:" + currentKb + " kb");

                            // 把数据发送到UI线程中去，这样就可以直接去使用了
                            if (mHandler != null) {
                                Message message = mHandler.obtainMessage();
                                message.what = what;
                                message.obj = currentKb;
                                mHandler.sendMessage(message);
                            }

                            // 重新赋值
                            oldKb = newKb;
                        }
                    };
                }


                if (timer == null) {
                    timer = new Timer();
                }
                // 延迟一秒之后，每间隔一秒发送一次
                if (!isPerform) {
                    timer.schedule(task, 1000, 1000);
                    isPerform = true;
                }
            }

            // 重置状态
            isPerform = true;
        }
    }


    /**
     * @return 获取总的接受字节数，包含Mobile和WiFi等
     */
    @SuppressLint("WrongConstant")
    private long getUidRxBytes() { //获取总的接受字节数，包含Mobile和WiFi等
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai = null;
        try {
            ai = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return TrafficStats.getUidRxBytes(ai.uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);
    }


    /**
     * 移除消息
     */
    public void remove() {
        if (isPerform) {
            isPerform = false;
        }

        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (mHandler != null) {
            mHandler.removeMessages(this.what);
            mHandler.removeCallbacksAndMessages(null);
        }

    }

    public void clear() {
        remove();
        isPerform = false;
        mHandler = null;
    }

    public interface OnNetWorkChangeListener {
        void onChanged(long currentKb);
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == NetworkCurrentKbUtil.this.what) {
                long obj = (long) msg.obj;
                if (mOnNetWorkChangeListener != null) {
                    mOnNetWorkChangeListener.onChanged(obj);
                }
            }
        }
    };
}
