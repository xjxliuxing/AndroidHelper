package com.xjx.helper.http;

import java.io.Serializable;
import java.util.List;

/**
 * 分页请求的Java Bean
 *
 * @param <T>
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    // 每条数据
    private List<T> data;
    // 总共有多少条数据
    private int count;
    // 现在该字段后台不给返回，需要自己根据每页请求的个数和总条数计算
    private int totalPages;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Page{" +
                "data=" + data +
                ", count=" + count +
                ", totalPages=" + totalPages +
                '}';
    }
}
