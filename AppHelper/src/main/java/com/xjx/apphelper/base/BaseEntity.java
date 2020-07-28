package com.xjx.apphelper.base;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者 徐腾飞
 * @创建时间 2019/2/25  9:51
 * @更新者 hp
 * @更新时间 2019/2/25  9:51
 * @描述 实体类的基类
 */
public abstract class BaseEntity implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
