package com.xjx.helper.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xjx.helper.R;
import com.xjx.helper.adapter.DownLoadAdapter;
import com.xjx.helper.base.CommonBaseTitleActivity;
import com.xjx.helper.enums.PlaceholderStatus;
import com.xjx.helper.interfaces.OnItemClickListener;
import com.xjx.helper.interfaces.ProgressResponseListener;
import com.xjx.helper.utils.ConvertUtil;
import com.xjx.helper.utils.DownloadUtil;
import com.xjx.helper.utils.FileUtil;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.utils.RecycleUtil;
import com.xjx.helper.widget.RecycleViewDivider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 下载页面
 */
public class DownLoadActivity extends CommonBaseTitleActivity {

    private RecyclerView mRvList;
    private List<String> mList = new ArrayList();

    @Override
    protected int getTitleLayout() {
        return R.layout.activity_download;
    }

    @Override
    protected void initView() {
        super.initView();
        mRvList = findViewById(R.id.rv_list);

        setTitleContent("下载页面");
        SwitchLoadingStatus(PlaceholderStatus.NONE);
    }

    @Override
    protected void initData() {
        super.initData();

        for (int i = 0; i < 5; i++) {
            String path = "https://www.bluemembers.com.cn/upload/CarCategoryGuide/新一代悦纳车型多媒体信息娱乐系统说明书.pdf";
//            String path = "http://oss.pgyer.com/0703dfa726530033c437d162a90dea3c.apk?auth_key=1583819821-51b3d32414a994a9a81aff3fc9cbfa9a-0-291a4904a6f5b7953d90bcd33b3b0258&response-content-disposition=attachment%3B+filename%3Dapp-release.apk";
            mList.add(path);
        }

        DownLoadAdapter adapter = new DownLoadAdapter(mContext);
        adapter.setList(mList);
        float dp = ConvertUtil.toDp(5);

        RecycleViewDivider divider = new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL, R.color.transparent, (int) dp);
        mRvList.addItemDecoration(divider);

        RecycleUtil
                .getInstance(mContext, mRvList)
                .setVertical()
                .setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @SuppressLint("CheckResult")
            @Override
            public void onItemClick(View view, int position, String s) {
                if (view instanceof ProgressBar) {
                    ProgressBar progressBar = (ProgressBar) view;

                    RxPermissions rxPermissions = new RxPermissions(mContext);
                    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            if (granted) {
                                downLoad(s, progressBar, position);
                            } else {
                                LogUtil.e("至少有一个权限被拒绝！");
                            }
                        }
                    });
                }
            }
        });
    }

    private void downLoad(String s, ProgressBar progressBar, int position) {

        String sdPaht = FileUtil.getInstance().getSdPaht(mContext);

//        File file = new File(sdPaht, "test.apk");
        File file = new File(sdPaht, "test.pdf");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DownloadUtil util = new DownloadUtil(new ProgressResponseListener() {
            @Override
            public void onStart(long totalLength, Object tag) {
                progressBar.setMax((int) totalLength);
            }

            @Override
            public void onLoading(long currentProgress, long totalLength, boolean isDone, Object tag) {
                progressBar.setProgress((int) currentProgress);
            }

            @Override
            public void onComplete(Object tag) {
                LogUtil.e("position: " + tag + " 完成了任务！");
            }

            @Override
            public void onFailure(String message, Object tag) {
                LogUtil.e("message:" + message);
            }
        }, position);
        util.download(s, file.getAbsolutePath());
    }

}
