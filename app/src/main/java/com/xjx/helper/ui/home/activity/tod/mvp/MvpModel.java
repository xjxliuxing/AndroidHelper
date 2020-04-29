package com.xjx.helper.ui.home.activity.tod.mvp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MvpModel extends ViewModel {
    // 数据类型
    private MutableLiveData<LiveDataSource> mContent = new MutableLiveData<>();
    private LiveDataRepository factory;

    LiveData<LiveDataSource> getLoginFormState() {
        return mContent;
    }

    MvpModel(LiveDataRepository loginRepository) {
        this.factory = loginRepository;
    }
}
