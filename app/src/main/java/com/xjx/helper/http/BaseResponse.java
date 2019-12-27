package com.xjx.helper.http;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 对接口返回结果做统一接收
 * <p>
 * Created by erge 2019/9/18 4:24 PM
 */
public class BaseResponse<T> implements Parcelable {

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

    public BaseResponse() {

    }

    protected BaseResponse(Parcel in) {
        returnCode = in.readInt();
        returnStatus = in.readInt();
        returnMsg = in.readString();
    }

    public static final Creator<BaseResponse> CREATOR = new Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel in) {
            return new BaseResponse(in);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };

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

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "returnStatus=" + returnStatus +
                ", returnMsg='" + returnMsg + '\'' +
                ", returnDataList=" + returnDataList +
                ", returnCode=" + returnCode +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(returnCode);
        dest.writeInt(returnStatus);
        dest.writeString(returnMsg);
    }
}
