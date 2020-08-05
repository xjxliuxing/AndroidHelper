package com.xjx.helper.ui.home.fragments.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.xjx.helper.R;
import com.xjx.helper.base.CommonBaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Test2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Test2Fragment extends CommonBaseFragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private TextView tv_2;

    public Test2Fragment() {
        // Required empty public constructor
    }

    public static Test2Fragment newInstance(String param1) {
        Test2Fragment fragment = new Test2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_test2;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        tv_2 = rootView.findViewById(R.id.tv_2);
    }

    @Override
    protected void initData() {
        super.initData();

        tv_2.setText(mParam1);
    }
}
