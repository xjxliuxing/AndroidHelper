package com.xjx.apphelper.utils;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xjx.apphelper.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2019/3/22.
 */
@SuppressLint("CheckResult")
public class PermissionUtil {

    private static PermissionUtil permissionUtil;

    // 返回的list对象组
    private List<Boolean> resultList = new ArrayList<>();
    private boolean result = false;

    public static PermissionUtil getInstance() {
        if (permissionUtil == null) {
            permissionUtil = new PermissionUtil();
        }
        return permissionUtil;
    }

    /**
     * @param activity
     * @param permissionGroup
     * @return 同时请求多个权限，合并单个详细的结果
     */
    public List<Boolean> RequestActivityPermission(FragmentActivity activity, final String[] permissionGroup) {
        if (resultList == null) {
            resultList = new ArrayList<>();
        }
        resultList.clear();

        if (activity == null || permissionGroup == null || permissionGroup.length < 0) {
            LogUtil.e("请求权限的对象为空 ！");
        } else {
            // 1 : 构建请求对象
            RxPermissions rxPermissions = new RxPermissions(activity); // where this is an Activity instance

            rxPermissions.requestEach(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            String name = permission.name;
                            LogUtil.e("naem:OK" + name);
                            resultList.add(true);
                        } else {
                            String name = permission.name;
                            LogUtil.e("naem:Fauld" + name);
                            resultList.add(false);
                        }
                    });
        }

        return resultList;
    }

    /**
     * 同时请求多个权限，合并获取结果
     *
     * @param activity
     * @param permissionGroup
     */
    public boolean RequestPermissionAll(FragmentActivity activity, final String[] permissionGroup) {
        if (resultList == null) {
            resultList = new ArrayList<>();
        }
        resultList.clear();

        if (activity == null || permissionGroup == null || permissionGroup.length < 0) {
            LogUtil.e("请求权限的对象为空 ！");

        } else {
            // 1 : 构建请求对象
            RxPermissions rxPermissions = new RxPermissions(activity); // where this is an Activity instance
            rxPermissions.request(permissionGroup).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean granted) throws Exception {
                    if (granted) {

                        resultList.add(true);
                    } else {
                        resultList.add(false);
                    }
                }
            });
        }
        if (resultList.contains(false)) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    /***************************************例子***********************************************************/

    /**
     * 例子---合并请求多个权限，获取详细的单个权限的授权情况
     */
    private void Lz(FragmentActivity activity, String[] strings) {
        // 1 : 构建请求对象
        RxPermissions rxPermissions = new RxPermissions(activity); // where this is an Activity instance

        rxPermissions.requestEach(strings)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            String name = permission.name;
                            LogUtil.e("naem:OK" + name);
                        } else {
                            String name = permission.name;
                            LogUtil.e("naem:Fauld" + name);
                        }
                    }
                });
    }

    /**
     * 例子 --- 请求权限，监听是否点击不在提示的操作！
     */
    private void LzAgain(final FragmentActivity activity, String[] strings) {
        // 1 : 构建请求对象
        RxPermissions rxPermissions = new RxPermissions(activity); // where this is an Activity instance

        rxPermissions.requestEachCombined(strings).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    // LogUtil.e("所有的权限都同意了！");
                } else if (permission.shouldShowRequestPermissionRationale) {

                    LogUtil.e("有的同意，有的拒绝！");
                    ToastUtil.showToast(activity.getResources().getString(R.string.permission_failed));
                } else {
                    LogUtil.e("设置了不在提示的按钮后再拒绝！");
                    ToastUtil.showToast(activity.getResources().getString(R.string.permission_again));
                }
            }
        });
    }

    /**
     * 例子 --- 直接请求多个权限，获取所有的合并结果，只有有个不通过，就直接返回false
     */
    private void getAllPermission(FragmentActivity activity, String[] strings) {
        // 1 : 构建请求对象
        RxPermissions rxPermissions = new RxPermissions(activity); // where this is an Activity instance

        rxPermissions.request(strings).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {

                if (granted) {
                    // LogUtil.e("所有的权限都同意了！");
                } else {
                    LogUtil.e("至少有一个权限被拒绝！");
                }
            }
        });
    }

}
