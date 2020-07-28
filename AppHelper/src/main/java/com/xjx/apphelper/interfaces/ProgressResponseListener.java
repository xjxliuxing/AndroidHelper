package com.xjx.apphelper.interfaces;

/**
 * @作者 徐腾飞
 * @创建时间 2020/1/14  21:23
 * @更新者 HongJing
 * @更新时间 2020/1/14  21:23
 * @描述 下载进度的监听
 */
public interface ProgressResponseListener {

    /**
     * 下载开始
     *
     * @param totalLength 下载的总长度
     * @param tag         下载目标的tag标记
     */
    void onStart(long totalLength, Object tag);

    /**
     * @param currentProgress 当前的进度
     * @param totalLength     总的长度
     * @param isDone          是否请求完成
     * @param tag             下载目标的tag标记
     */
    void onLoading(long currentProgress, long totalLength, boolean isDone, Object tag);

    /**
     * 下载完成
     *
     * @param tag 下载目标的tag标记
     */
    void onComplete(Object tag);

    /**
     * 下载失败
     *
     * @param message 失败的原因
     * @param tag     下载目标的tag
     */
    void onFailure(String message, Object tag);
}
