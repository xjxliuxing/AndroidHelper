package com.xjx.apphelper.utils;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * <p>文件描述<p>
 * <p>作者：hp<p>
 * <p>创建时间：2019/1/19<p>
 * <p>更改时间：2019/1/19<p>
 */
public class FragmentUtil {

    /**
     * @param activity        FragmentActivity的上下文
     * @param containerViewId 要放置此片段的容器的可选标识符。如果为0，则不会将其放置在容器中。
     * @param fragment        fragment对象
     * @return 替换Fragment成功则返回true，否则就返回fales
     */
    public static boolean replace(FragmentActivity activity, @IdRes int containerViewId, Fragment fragment) {
        LogUtil.e("activity:" + activity.toString());
        LogUtil.e("containerViewId:" + containerViewId);
        LogUtil.e("fragment:" + fragment.toString());
        LogUtil.e("activity--isFinishing-:" + activity.isFinishing());

        boolean isSuccess = false;
        if ((activity != null) && (!activity.isFinishing()) && (containerViewId != 0) && (fragment != null)) {
            //1.获取Fragment管理器
            FragmentManager fm = activity.getSupportFragmentManager();
            //2.开启事务
            FragmentTransaction ft = fm.beginTransaction();
            //3.设置把Fragment显示到帧布局中
            ft.replace(containerViewId, fragment);
            //4.提交
            ft.commit();
            isSuccess = true;
        } else {
            throw new NullPointerException("替换Fragment的参数中有对象为空！");
        }
        return isSuccess;
    }

    /*************************************** 优化 Fragment 开始 *****************************************************/

    private static Fragment currentFragment = new Fragment(); // 当前的Fragmetn

    public static boolean switchFragment(FragmentActivity activity, @IdRes int containerViewId, Fragment targetFragment) {

        boolean isSuccess = false;

        if ((activity != null) && (targetFragment != null)) {

            FragmentTransaction tr = activity.getSupportFragmentManager().beginTransaction();

            // 如果目标的Fragment没有添加到FragmentLayout当中，就添加一次
            if (!targetFragment.isAdded()) {

                // 使用目标的Fragment替换掉当前的Fragment
                if (currentFragment != null) {
                    tr.hide(currentFragment)
                            .add(containerViewId, targetFragment)
                            .commit();

                    LogUtil.e("没有Fragment片段！");
                    isSuccess = true;
                }

            } else {
                // 如果Fragment已经添加到了Layout中，就直接隐藏当前的fragmnt，去展示目标的Fragment
                tr
                        .hide(currentFragment)
                        .show(targetFragment)
                        .commit();

                LogUtil.e("有Fragment片段！");
                isSuccess = true;
            }
            // 目标的Fragment就是当前的Fragment
            currentFragment = targetFragment;

        } else {
            isSuccess = false;
            LogUtil.e("替换Fragment的参数中有对象为空！");
        }

        return isSuccess;
    }

    public static boolean RemoveFragment(FragmentActivity activity, Fragment targetFragment) {

        boolean isSuccess = false;

        if ((activity != null) && (!activity.isFinishing()) && (targetFragment != null)) {

            FragmentTransaction tr = activity.getSupportFragmentManager().beginTransaction();
            if (targetFragment.isAdded()) {
                tr.remove(targetFragment);
                isSuccess = true;
            }

        } else {
            isSuccess = false;
        }

        return isSuccess;
    }

    /*************************************** 优化 Fragment 结束 *****************************************************/

}
