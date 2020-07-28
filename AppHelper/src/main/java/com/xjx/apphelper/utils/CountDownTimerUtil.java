package com.xjx.apphelper.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2019/5/29.
 * 倒计时的工具类
 */

public class CountDownTimerUtil {

    private static String tag = "CountDownTimerUtil";

    private static CountDownTimerUtil timerUtil;

    private int current_state;
    private int state_idle = 1; // 闲置的状态
    private int state_playing = 2; // 正在播放的状态
    private int state_prepare = 3; // 准备状态
    private int state_parse = 4; // 暂停状态

    private int what_msg = 996;
    private String msg_arg1 = "msg_arg1";
    private String msg_arg2 = "msg_arg2";

    private long currentTimes;

    private MyHandler myHandler;

    public static CountDownTimerUtil getInstance() {
        if (timerUtil == null) {
            timerUtil = new CountDownTimerUtil();
            LogUtil.e(tag, "产生了新的计时器对象！");
        }
        return timerUtil;
    }

    public CountDownTimerUtil start(long millisInFuture, long countDownInterval) {

        if (myHandler == null) {
            myHandler = new MyHandler();
        }

        Message message = myHandler.obtainMessage();
        Bundle bundle = new Bundle();
        message.what = what_msg;
        bundle.putLong(msg_arg1, millisInFuture);
        bundle.putLong(msg_arg2, countDownInterval);
        message.setData(bundle);

        myHandler.sendMessage(message);
        current_state = state_prepare;

        return timerUtil;
    }

    public void pause() {
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
            current_state = state_parse;
            myHandler = null;
        }
    }

    public void stop() {
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
            current_state = state_idle;
        }
        if (timerUtil != null) {
            timerUtil = null;
        }
    }

    /**
     * @return 获取 当前减去后的值 ,只有在暂停的状态才去获取数据，因为只有是暂停了，才会有数据，否则没有去数据的意义
     */
    public long getCurrentDownTimer() {
        if (current_state == state_parse) {
            return getCurrentTimes();
        } else {
            return 0;
        }
    }

    public CountDownTimerUtil setCountDownTimerListener(CountDownTimerListener countDownTimerListener) {
        this.mCountDownTimerListener = countDownTimerListener;
        return timerUtil;
    }

    private CountDownTimerListener mCountDownTimerListener;

    public interface CountDownTimerListener {

        void onTick(long millisUntilFinished);

        void onFinish();
    }

    private void updateMessage(long millisInFuture, long countDownInterval) {

        if (myHandler == null) {
            myHandler = new MyHandler();
            LogUtil.e(tag, "重新构建handler！");
        }

        // 每次自动 减掉 间隔时间
        millisInFuture -= countDownInterval;

        Message message = myHandler.obtainMessage();
        Bundle bundle = new Bundle();
        message.what = what_msg;
        bundle.putLong(msg_arg1, millisInFuture);
        bundle.putLong(msg_arg2, countDownInterval);
        message.setData(bundle);

        myHandler.sendMessageDelayed(message, countDownInterval);

        current_state = state_prepare;
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == what_msg) {

                current_state = state_playing;

                Bundle data = msg.getData();

                if (data != null) {
                    long arg1 = data.getLong(msg_arg1);
                    long arg2 = data.getLong(msg_arg2);

//                    LogUtil.e(tag, "arg1:"+arg1);

                    if (arg1 >= arg2) {

                        setCurrentTimes(arg1);

                        if (mCountDownTimerListener != null) {
                            mCountDownTimerListener.onTick(arg1);
                        }

                        // 循环遍历
                        updateMessage(arg1, arg2);

                    } else {
                        if (mCountDownTimerListener != null) {
                            mCountDownTimerListener.onFinish();
//                            LogUtil.e(tag, "onFinish");
                            if (myHandler != null) {
                                myHandler.removeCallbacksAndMessages(null);
                                myHandler = null;
                            }
                            current_state = state_idle;
                        }

                    }
                }

            }
        }
    }

    public long getCurrentTimes() {
        return currentTimes;
    }

    public void setCurrentTimes(long currentTimes) {
        this.currentTimes = currentTimes;
    }
}
