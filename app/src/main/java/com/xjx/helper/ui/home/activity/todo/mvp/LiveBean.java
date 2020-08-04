package com.xjx.helper.ui.home.activity.todo.mvp;

public class LiveBean {
    private String title;
    private int number;
    private int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "LiveBean{" +
                "title='" + title + '\'' +
                ", number=" + number +
                ", tag=" + tag +
                '}';
    }
}
