package com.xjx.helper.ui;

import android.Manifest;
import android.view.View;
import android.widget.Button;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;
import com.xjx.helper.http.client.DownloadUtil;
import com.xjx.helper.interfaces.ProgressResponseListener;
import com.xjx.helper.utils.FileUtil;
import com.xjx.helper.utils.LogUtil;

import java.io.File;
import java.io.IOException;

import io.reactivex.functions.Consumer;

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
    protected void initView() {
        super.initView();
        btn_down = findViewById(R.id.btn_down);

        setTitleContent("下载页面");
        LoadingStatus(PlaceholderStatus.NONE);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_down.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_down:

                RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance

                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            downLoad();

                        } else {
                            LogUtil.e("至少有一个权限被拒绝！");
                        }
                    }
                });

                break;
        }
    }

    private void downLoad() {

        String path = "https://www.bluemembers.com.cn/upload/CarCategoryGuide/新一代悦纳车型多媒体信息娱乐系统说明书.pdf";

        DownloadUtil downloadUtil = new DownloadUtil(mContext, new ProgressResponseListener() {
            @Override
            public void onStart() {
                LogUtil.e("开始下载");
            }

            @Override
            public void onLoading(long currentProgress, long totalLength, boolean isDone) {
                LogUtil.e("current:" + currentProgress + " total:" + totalLength + " isdown:" + isDone);
            }

            @Override
            public void onComplete() {
                LogUtil.e("下载结束");
            }

            @Override
            public void onFailure(String message) {
                LogUtil.e("下载失败");
            }
        });

        String sdPaht = FileUtil.getInstance().getSdPaht(mContext);

        File file = new File(sdPaht, "sss.pdf");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        downloadUtil.download(path, file.getAbsolutePath());

    }

    int currentProgress = 0;

    /**
     * @param context
     * @param url     下载路径
     * @param file    存入文件的地址
     */

}
