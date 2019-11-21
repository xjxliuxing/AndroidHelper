package com.xjx.helper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
//        final View viewById = findViewById(R.id.iv_test2);
//        viewById.post(new Runnable() {
//            @Override
//            public void run() {
//
//                int width = viewById.getWidth();
//                Log.e("XJX", "width:Value:" + width);
//
//                int height = viewById.getHeight();
//                Log.e("XJX", "height:Value:" + height);
//
//                int paddingLeft = viewById.getPaddingLeft();
//                int paddingTop = viewById.getPaddingTop();
//
//                Log.e("XJX", "paddingLeft:Value:" + paddingLeft);
//                Log.e("XJX", "paddingTop:Value:" + paddingTop);
//
//                ViewGroup viewById1 = findViewById(R.id.rl_content);
//                int paddingLeft1 = viewById1.getPaddingLeft();
//
//                Log.e("XJX", "paddingLeft---Group---:Value:" + paddingLeft1);
//            }
//        });

    }
}
