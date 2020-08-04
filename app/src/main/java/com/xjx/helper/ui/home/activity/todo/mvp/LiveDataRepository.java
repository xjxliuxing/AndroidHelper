package com.xjx.helper.ui.home.activity.todo.mvp;

public class LiveDataRepository {
    private static volatile LiveDataRepository instance;

    private LiveDataSource dataSource;

    private LiveDataRepository(LiveDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LiveDataRepository getInstance(LiveDataSource dataSource) {
        if (instance == null) {
            instance = new LiveDataRepository(dataSource);
        }
        return instance;
    }
}
