package com.xjx.helper.ui;

import android.view.View;
import android.widget.Button;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;

/**
 * 下载页面
 */
public class DownLoadActivity extends CommonBaseTitleActivity {

    private Button btn_down;

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_download;
    }

    @Override
    protected String getTitleContent() {
        return "下载页面";
    }

    @Override
    protected void initView() {
        super.initView();
        btn_down = findViewById(R.id.btn_down);

        LoadingStatus(PlaceholderStatus.NONE, "");
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_down.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_down:
                downLoad();
                break;
        }
    }

    private void downLoad() {

    }
}
