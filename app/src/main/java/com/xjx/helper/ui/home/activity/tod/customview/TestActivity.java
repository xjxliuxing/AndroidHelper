package com.xjx.helper.ui.home.activity.tod.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.xjx.helper.R;
import com.xjx.helper.customview.ProgressView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ProgressView viewById = findViewById(R.id.custom_pbssss);

    }
}