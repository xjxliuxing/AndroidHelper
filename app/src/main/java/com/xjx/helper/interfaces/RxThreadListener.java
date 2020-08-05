package com.xjx.helper.interfaces;

public interface RxThreadListener<T> {
    T doingBackGround();

    void onResult(T t);
}
