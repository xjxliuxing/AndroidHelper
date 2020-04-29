package com.xjx.helper.ui.home.activity.tod.mvp;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LiveDataViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MvpModel.class)) {

            return (T) new MvpModel(LiveDataRepository.getInstance(new LiveDataSource()));

        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
