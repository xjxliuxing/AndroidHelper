package com.xjx.helper.interfaces;

/**
 * @作者 徐腾飞
 * @创建时间 2020/1/14  21:23
 * @更新者 HongJing
 * @更新时间 2020/1/14  21:23
 * @描述 下载进度的监听
 */
public interface ProgressResponseListener {

    void onStart();

    /**
     * @param currentProgress 当前的进度
     * @param totalLength     总的长度
     * @param isDone          是否请求完成
     */
    void onLoading(long currentProgress, long totalLength, boolean isDone);

    /**
     *  下载完成
     */
    void onComplete(String path);

    void onFailure(String message);
}
