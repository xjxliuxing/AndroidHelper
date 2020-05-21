package com.xjx.helper.ui.home.activity.tod.customview;

import android.view.View;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;
import com.xjx.helper.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoadProgressActivity extends CommonBaseTitleActivity {

    private android.widget.Button mBtn;
    private com.xjx.helper.customview.SendProgressView mSpv;
    private Subscription subscribe;

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_load_progress;
    }

    @Override
    protected void initView() {
        super.initView();
        mBtn = findViewById(R.id.btn);
        mSpv = findViewById(R.id.spv);
    }

    @Override
    protected void initData() {
        super.initData();
        SwitchLoadingStatus(PlaceholderStatus.NONE);
        setTitleContent("进度条Progress");

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                if (aLong < (mSpv.getTime() + 1)) {
                                    mSpv.setProgress(aLong);
                                    LogUtil.e("------->:" + aLong);
                                } else {
                                    if (subscribe.isUnsubscribed()) {
                                        subscribe.unsubscribe();
                                    }
                                }
                            }
                        });
            }
        });
    }
}
