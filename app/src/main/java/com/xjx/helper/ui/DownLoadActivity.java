package com.xjx.helper.ui;

import android.view.View;
import android.widget.Button;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;

import java.io.File;

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
        String path = "https://www.bluemembers.com.cn/upload/CarCategoryGuide/新一代悦纳车型多媒体信息娱乐系统说明书.pdf";

        File file = getFile();
        download(mContext, path, file);
    }

    int currentProgress = 0;

    /**
     * @param context
     * @param url     下载路径
     * @param file    存入文件的地址
     */


}
