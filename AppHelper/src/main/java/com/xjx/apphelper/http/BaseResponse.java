package com.xjx.apphelper.http;

public class BaseResponse<T> {

    // code值--成功
    public static final int REQUEST_SUCCESS = 1;
    // token失效
    public static final String TOKEN_INVALIDATE = "token错误或失效";
    // 其他值还不知道后台是如何定义的，等以后扩展值含义，在ResponseCallback类中做统一处理

    /**
     * code返回值
     */
    private int returnCode;

    /**
     * 接口请求状态值，当为1时表示数据正常
     */
    private int returnStatus;

    /**
     * 接口返回的提示语句
     */
    private String returnMsg;

    /**
     * 具体的接口数据内容
     * 列表数据和普通数据都是使用该字段
     */
    private T returnDataList;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public int getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public T getReturnDataList() {
        return returnDataList;
    }

    public void setReturnDataList(T returnDataList) {
        this.returnDataList = returnDataList;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "returnCode=" + returnCode +
                ", returnStatus=" + returnStatus +
                ", returnMsg='" + returnMsg + '\'' +
                ", returnDataList=" + returnDataList +
                '}';
    }
}
