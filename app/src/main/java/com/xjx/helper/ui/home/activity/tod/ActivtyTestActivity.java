package com.xjx.helper.ui.home.activity.tod;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseActivity;
import com.xjx.helper.utils.LogUtil;

/**
 * Activity的测试页面
 */
@SuppressLint("HandlerLeak")
public class ActivtyTestActivity extends CommonBaseActivity {

    private Integer type;

    @Override
    protected int getLayout() {
        return R.layout.activity_activty_test;
    }

    private Handler mHandler1 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            LogUtil.e("1111111");

            if (msg.what == 111) {
                mHandler1.sendEmptyMessageDelayed(111, 3000);
            }

        }
    };


    private Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            LogUtil.e("222222");

            if (msg.what == 222) {
                mHandler2.sendEmptyMessageDelayed(222, 3000);
            }

        }
    };


    @Override
    protected void initView() {
        super.initView();

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler2.removeMessages(222);
                mHandler2.removeCallbacksAndMessages(null);

                mHandler1.sendEmptyMessage(111);

                LogUtil.e("---->type:" + type);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler1.removeMessages(111);
                mHandler1.removeCallbacksAndMessages(null);

                mHandler2.sendEmptyMessage(222);
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();

        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();

        for (int i = 0; i < 5; i++) {
            buffer.append(i + " ");
        }
        LogUtil.e("正序：" + buffer.toString());


        for (int i = 5 - 1; i > -1; i--) {
            buffer2.append(" " + i);
        }
        LogUtil.e("倒叙：" + buffer2.toString());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mHandler1.removeCallbacksAndMessages(null);
        mHandler2.removeCallbacksAndMessages(null);

    }
}
