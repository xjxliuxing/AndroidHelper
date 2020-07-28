package com.xjx.helper.entity.todo;

import android.os.Parcel;
import android.os.Parcelable;

import com.xjx.apphelper.base.BaseEntity;

/**
 * @作者 徐腾飞
 * @创建时间 2020/3/12  9:51
 * @更新者 HongJing
 * @更新时间 2020/3/12  9:51
 * @描述 车主手册的bean
 */
public class GridLayoutLayoutBean extends BaseEntity {

    private String id;
    private String url;
    private boolean isDownload;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.url);
        dest.writeByte(this.isDownload ? (byte) 1 : (byte) 0);
    }

    public GridLayoutLayoutBean() {
    }

    protected GridLayoutLayoutBean(Parcel in) {
        this.id = in.readString();
        this.url = in.readString();
        this.isDownload = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GridLayoutLayoutBean> CREATOR = new Parcelable.Creator<GridLayoutLayoutBean>() {
        @Override
        public GridLayoutLayoutBean createFromParcel(Parcel source) {
            return new GridLayoutLayoutBean(source);
        }

        @Override
        public GridLayoutLayoutBean[] newArray(int size) {
            return new GridLayoutLayoutBean[size];
        }
    };

    @Override
    public String toString() {
        return "GridLayoutLayoutBean{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", isDownload=" + isDownload +
                '}';
    }
}
