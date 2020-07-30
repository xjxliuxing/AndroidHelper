package com.xjx.apphelper.interfaces;

public interface RxThreadListener<T> {
    T doingBackGround();

    void onResult(T t);
}
