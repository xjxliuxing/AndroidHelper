package com.xjx.helper.ui.home.fragments.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseFragment;


/**
 * 测试fragment1
 */
public class Test1Fragment extends CommonBaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private TextView tv_1;

    public Test1Fragment() {
    }

    public Test1Fragment(String param) {
        mParam1 = param;
    }

//    public static Test1Fragment newInstance2(String param1) {
//        Test1Fragment fragment = new Test1Fragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_test1;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        tv_1 = rootView.findViewById(R.id.tv_1);
    }

    @Override
    protected void initData() {
        super.initData();

        tv_1.setText(mParam1);

    }
}
