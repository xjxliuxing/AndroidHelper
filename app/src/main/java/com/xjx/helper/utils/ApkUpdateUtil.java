package com.xjx.helper.utils;//package com.xjx.helper.utils;
//
//import android.Manifest;
//import android.app.Dialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.provider.Settings;
//import android.DividerGridItemDecoration.TextUtils;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.core.content.FileProvider;
//import androidx.fragment.app.FragmentActivity;
//
//import com.xjx.helper.R;
//
//import java.io.File;
//import java.util.ArrayList;
//
///**
// * Created by Administrator on 2019/4/10.
// */
//
//public class ApkUpdateUtil {
//
//    private static FragmentActivity mContext;
//    private static File mFileResult;
//    private ProgressBar progressBar;
//    private Dialog dialog;
//    private TextView tvBfb;
//    private String url;
//    private DialogUtil dialogUtil;
//    private static ApkUpdateUtil apkUpdateUtil;
//
//    public static ApkUpdateUtil setUpdateApk(@NonNull FragmentActivity activity) {
//        mContext = activity;
//        apkUpdateUtil = new ApkUpdateUtil();
//        return apkUpdateUtil;
//    }
//
//    public ApkUpdateUtil() {
//        // 1：检测需要的下载
//        checkPermission();
//    }
//
//    /**
//     * 检测所需要的权限
//     */
//    private void checkPermission() {
//        // 1 : 构建请求对象
//        RxPermissions rxPermissions = new RxPermissions(mContext); // where this is an Activity instance
//
//        // 测试联网权限和读写权限 和安装权限
//        rxPermissions.request(
//                Manifest.permission.INTERNET,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//        ).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean granted) throws Exception {
//                if (granted) {
//                    LogUtil.e("有下载更新Apk的权限！");
//                    //  LogUtil.e("所有的权限都同意了！");
//
//                    //  2: 查看远程版本
//                    checkVersion();
//                } else {
//                    LogUtil.e("没有下载更新Apk的权限！");
//                    ToastUtil.showToast(mContext, "请给与权限后再尝试更新！");
//                }
//            }
//        });
//    }
//
//    // 2：检测版本
//    public void checkVersion() {
//
//        final RequestParams params = new RequestParams(BaseUrlManager.BASE_URL);
//        params.setMultipart(true);
//        params.setAsJsonContent(true);
//        List<KeyValue> list = new ArrayList<>();
//        list.add(new KeyValue("service", "App.Jgcontent.GetAndroidVersion"));
//        //设置编码格式为UTF-8，保证参数不乱码
//        final MultipartBody body = new MultipartBody(list, "UTF-8");
//        params.setRequestBody(body);
//
//        x.http().post(params, new Callback.CommonCallback<String>() {
//
//            @Override
//            public void onSuccess(String result) {
//                if (!TextUtils.isEmpty(result)) {
//                    UpdateEntity updateEntity = JSON.parseObject(result, UpdateEntity.class);
//                    if (updateEntity != null) {
//                        int ret = updateEntity.getRet();
//                        if (ret == 200) {
//                            UpdateEntity.DataBean data = updateEntity.getData();
//                            if (data != null) {
//                                int version = data.getVersion();
//
//                                int versionCode = AppVersionUtil.getInstance(mContext).getVersionCode();
//
//                                if (version > versionCode) {
//                                    url = data.getUrl();
//                                    LogUtil.e("下载新版本！");
//                                    dialogUtil = DialogUtil
//                                            .getInstance(mContext)
//                                            .setContentView(R.layout.common_dialog_left_right_title)
//                                            .seActivitytTitle("发现新版本")
//                                            .setMsg("为了给您更好的体验，请下载最新版本！")
//                                            .setLeftClickListener("取消", new DialogUtil.LeftClickListener() {
//                                                @Override
//                                                public void onLeftItemClick() {
//                                                    dialogUtil.dismiss();
//                                                }
//                                            }).setRightClickListener("确定", new DialogUtil.RightClickListener() {
//                                                @Override
//                                                public void onRightItemClick() {
//
//                                                    //  后台下载Apk
//                                                    if (!TextUtils.isEmpty(url)) {
//                                                        downLoadApk(url);
//                                                        dialogUtil.dismiss();
//                                                    } else {
//                                                        LogUtil.e("下载路径为空！");
//                                                    }
//
//                                                    dialogUtil.dismiss();
//                                                }
//                                            }).show();
//                                } else {
//                                    LogUtil.e("已经是最新版本！");
//                                }
//                            }
//                        } else {
//                            LogUtil.e("检测版本失败：" + updateEntity.getMsg());
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//            }
//        });
//    }
//
//    public void downLoadApk(String apkUrl) {
//
////        apkUrl = "http://192.168.1.8:8080/app.apk";
////        apkUrl = "http://192.168.2.137:8080/app_2.apk";
//        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_update_app, null, false);
//        progressBar = view.findViewById(R.id.pb_gress);
//        tvBfb = view.findViewById(R.id.tv_bfb);
//
//        dialog = new Dialog(mContext, R.style.base_dialog);
//
//        dialog.setContentView(view);
//        dialog.getWindow().setGravity(Gravity.CENTER);
//        dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
//        dialog.getWindow().getAttributes().alpha = 1;
//        //弹窗点击周围空白处弹出层自动消失弹窗消失(false时为点击周围空白处弹出层不自动消失)
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
//
//        String sdCardPath = FileUtil.getInstance(mContext).getSDCardPath();
//        // LogUtil.e("Sd卡根目录为：" + sdCardPath);
//
//        if (TextUtils.isEmpty(sdCardPath)) {
//            ToastUtil.showToast(mContext, "Sd卡路径为空，请在设置界面选择手动下载!");
//            return;
//        }
//
//        File app = new File(sdCardPath, "app.apk");
//
//        // 删除原来的重新下载
//        if (app.exists()) {
//            app.delete();
//        }
//
//        RequestParams params = new RequestParams(apkUrl);
//        params.setSaveFilePath(app.getAbsolutePath());
//
//        x.http().get(params, new Callback.ProgressCallback<File>() {
//            @Override
//            public void onWaiting() {
//                LogUtil.e("下载onWaiting！");
//            }
//
//            @Override
//            public void onStarted() {
//                LogUtil.e("下载onStarted！");
//
//                if (dialog != null && mContext != null && (!mContext.isFinishing())) {
//                    if (!dialog.isShowing()) {
//                        dialog.show();
//                    }
//                }
//            }
//
//            @Override
//            public void onLoading(long total, long current, boolean isDownloading) {
//                LogUtil.e("当前的下载进度为：" + current);
//                LogUtil.e("总进度为：" + total);
//                progressBar.setProgress((int) current);
//                progressBar.setMax((int) total);
//
//                if ((current > 0) && (total > 0)) {
//
//                    int pro = (int) ((current * 100) / total);
//
//                    LogUtil.e("current:" + current);
//                    LogUtil.e("total:" + total);
//
//                    if (tvBfb != null) {
//                        tvBfb.setText(pro + "%");
//                    }
//                }
//            }
//
//            @Override
//            public void onSuccess(File resultFile) {
//                LogUtil.e("paths;" + resultFile.getPath());
//                LogUtil.e("下载完成：" + resultFile.getAbsolutePath());
//                mFileResult = resultFile;
//                openApk();
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                LogUtil.e("下载失败：" + ex.getMessage().toString());
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                LogUtil.e("下载取消了:" + cex.getMessage().toString());
//
//            }
//
//            @Override
//            public void onFinished() {
//                LogUtil.e("下载完成！");
//                if (dialog != null) {
//                    dialog.dismiss();
//                }
//            }
//        });
//
//    }
//
//    /**
//     * 自动安装apk文件
//     */
//    public void openApk() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            // 检测安装来源
//            boolean b = mContext.getPackageManager().canRequestPackageInstalls();
//            if (b) {
//                if (mFileResult != null) {
//                    installApk(mFileResult);//安装应用的逻辑(写自己的就可以)
//                }
//            } else {
//                //请求安装未知应用来源的权限
//                Uri uri = Uri.parse("package:" + mContext.getPackageName());
//                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri);
//                mContext.startActivityForResult(intent, CommonUser.CODE_APK_INSTALL);
//            }
//        } else {
//            if (mFileResult != null) {
//                installApk(mFileResult);//安装应用的逻辑(写自己的就可以)
//            }
//        }
//
//        // 获取发送的消息
////        App.getApp().getHandlerCallBack(new HandlerCallBackListener() {
////            @Override
////            public void heandleMessage(Message msg) {
////                switch (msg.what) {
////                    case CODE_APK_INSTALL_SUCCESS:
////                        LogUtil.e("收到发送的---成功--信息");
////                        if (mFileResult != null) {
////                            installApk(mFileResult);
////                        }
////                        break;
////
////                    case CODE_APK_INSTALL_FAILED:
////                        LogUtil.e("收到发送的---失败--信息");
////                        ToastUtil.showToast(App.getApp(), "请同意【安装未知应用】权限，否则应用将无法安装！");
////                        break;
////
////                }
////            }
////        });
//    }
//
//    protected void installApk(File file) {
//        LogUtil.e("开始执行安装: " + file.getPath());
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            LogUtil.e("版本大于 N ，开始使用 fileProvider 进行安装");
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            // 动态获取包名
//            String packageName = mContext.getApplicationInfo().packageName;
//            // 设置FileProvider的rui的路径
//            Uri uriForFile = FileProvider.getUriForFile(mContext, packageName + ".FileProvider", file);
//            LogUtil.e("uri:" + uriForFile);
//            intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
//            mContext.startActivity(intent);
//        } else {
//
//            Intent intent2 = new Intent();
//            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent2.setAction(Intent.ACTION_VIEW);
//            intent2.addCategory(Intent.CATEGORY_DEFAULT);
//            intent2.setType("application/vnd.android.package-archive");
//
//            Uri parse = Uri.parse("file://" + file.getAbsolutePath());
//            intent2.setData(parse);
//            intent2.setDataAndType(parse, "application/vnd.android.package-archive");
//
//            mContext.startActivity(intent2);
//        }
//
//    }
//}
