package com.xjx.apphelper.utils;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.xjx.apphelper.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DialogFragments extends DialogFragment {
    private int layout;
    public View mRootView;

    private DialogFragments(int layout) {
        this.layout = layout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(layout, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(getDialog()).requestWindowFeature(Window.FEATURE_NO_TITLE);

//        ViewPager vp_guide = view.findViewById(R.id.vp_guide);
//
//        List<Fragment> mFragments = new ArrayList<Fragment>();
//        mFragments.add(new Blank1Fragment());
//        mFragments.add(new Blank2Fragment());
//
//        List<String> mTitles = new ArrayList<String>();
//        mTitles.add("");
//        mTitles.add("");

//        CommonFragmentPagerAdapter pagerAdapter2 = new CommonFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitles);
//
//        vp_guide.setAdapter(pagerAdapter2);
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        if (window != null) {
            // 一定要设置Background，如果不设置，window属性设置无效
            window.setBackgroundDrawable(new ColorDrawable(80000000));
            DisplayMetrics dm = new DisplayMetrics();
            if (getActivity() != null) {
                WindowManager windowManager = getActivity().getWindowManager();
                if (windowManager != null) {
                    windowManager.getDefaultDisplay().getMetrics(dm);
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.BOTTOM;
                    // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(params);
                }
            }
        }
    }

    public static void showDialog(FragmentManager fragmentManager, int layout) {
        DialogFragments dialog = new DialogFragments(layout);
        dialog.show(fragmentManager, "tag");
    }

}
