package com.xjx.helper.test;

import android.view.View;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;
import com.xjx.helper.utils.LogUtil;

/**
 * 测试页面
 */
public class TestActivity extends CommonBaseTitleActivity {

    private android.widget.Button mButton;
    private android.widget.EditText mEt1;
    private android.widget.EditText mEt2;
    private android.widget.TextView mTv1;
    private android.widget.TextView mTv2;

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        super.initView();

        mButton = findViewById(R.id.button);
        mEt1 = findViewById(R.id.et_1);
        mEt2 = findViewById(R.id.et_2);
        mTv1 = findViewById(R.id.tv1);
        mTv2 = findViewById(R.id.tv2);
    }

    @Override
    protected void initData() {
        super.initData();
        SwitchLoadingStatus(PlaceholderStatus.NONE);

        String abc = "123";
        if (abc != null) {
            LogUtil.e("不为空");
        }
        if (abc.equals("123")) {
            LogUtil.e("123");
        } else {
            LogUtil.e("othier");
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value1 = mEt1.getText().toString();
                String value2 = mEt2.getText().toString();

                int parseInt1 = Integer.parseInt(value1);
                int parseInt2 = Integer.parseInt(value2);

                int i = parseInt1 / parseInt2;

                mTv1.setText("" + i);

                int i1 = parseInt1 % parseInt2;

                mTv2.setText("" + i1);
            }
        });

    }
}
