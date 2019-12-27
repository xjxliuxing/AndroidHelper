package com.xjx.helper.http;

import com.xjx.helper.global.CommonConstant;

/**
 * Created by erge 2019-10-23 14:40
 */
public class PageRequestBody {

    // 每页请求的数量
    private int limit = CommonConstant.DEFAULT_PAGE_SIZE;
    // 请求的第几页，从1开始
    private int page;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PageRequestBody{" +
                "limit=" + limit +
                ", page=" + page +
                '}';
    }
}
