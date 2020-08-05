package com.xjx.helper.ui.home.activity.todo.customviewui;
import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;

import rx.Subscription;

public class LoadProgressActivity extends CommonBaseTitleActivity {

    private android.widget.Button mBtn1, mBtn2;
    private com.xjx.helper.customview.SendProgressView mSpv;
    private Subscription subscribe;

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_load_progress;
    }

    @Override
    protected void initView() {
        super.initView();
        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mSpv = findViewById(R.id.spv);
    }

    @Override
    protected void initData() {
        super.initData();
        SwitchLoadingStatus(PlaceholderStatus.NONE);
        setTitleContent("进度条Progress");

        mBtn1.setOnClickListener(v -> mSpv.startAnimation());

        mBtn2.setOnClickListener(v -> mSpv.cancelAnimation());

//        mBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mSpv.startAnimation();
//
//                subscribe = Observable.interval(0, 1, TimeUnit.SECONDS)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action1<Long>() {
//                        @Override
//                        public void call(Long aLong) {
//                            if (aLong < (mSpv.getTime() + 1)) {
//                                mSpv.setProgress(aLong);
//                                LogUtil.e("------->:" + aLong);
//                            } else {
//                                if (subscribe.isUnsubscribed()) {
//                                    subscribe.unsubscribe();
//                                }
//                            }
//                        }
//                    });
//            }
//        });
    }
}
