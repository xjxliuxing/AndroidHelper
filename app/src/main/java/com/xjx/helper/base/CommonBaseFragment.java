package com.xjx.helper.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xjx.helper.utils.LogUtil;

/**
 * Fragment的基类，所有的fragment都继承自这个基类
 */
public abstract class CommonBaseFragment extends Fragment implements View.OnClickListener {

    protected Activity mContext;
    protected View mRootView;
    protected Bundle arguments;

    /**
     * 用来关联activity的，可以在这里获取acitivity传递的值，也可以通过getactivity（）
     * 去获取activity传递的数据，此方法只会在初始化的时候调用，且只会调用一次。
     *
     * @param context context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    /**
     * 和activity的作用是一样的，只有在创建Fragment的时候才去调用，可以在里面处理一些参数的回调，
     * 例如onSavaeIntantStake（）的参数，可以通过这里去调用
     *
     * @param savedInstanceState boundle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取传递过来的参数
        arguments = getArguments();

        // 当前Activity的全路径名称
        String mCanonicalName = getClass().getCanonicalName();
        LogUtil.e("当前的页面：Fragment -> 为: " + mCanonicalName);

    }

    /**
     * @param inflater           inflater
     * @param container          viewGrounp
     * @param savedInstanceState bundle
     * @return 给fragment返回一个布局，此方法里面不能做耗时的操作，否则会影响返回view的速度。
     * 使用的话，可以先进行view的判断，没有view的话再去重新new，可以加快布局的显示
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.e("--->onCreateView");
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayout(), container, false);
        }
        return mRootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 当activity中的onCreate（）方法走完之后，才去调用的这个方法。一般我们做操作都是在这个方法里面去执行的，因为如果要和activity进行交互的话，大多都要用到acitivity的ui控件，而ui控件一般都是在activity中的onCreate（）方法中进行初始化的，
     * 所以进行和activity的操作的时候，一般都要放到这里面进行曲操作
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 初始化View
        initView();
        // 初始化事件
        initListener();
        // 自定调用请求网络操作
        onRequestData();
        // 初始化数据
        initData();
    }

    protected abstract int getLayout();

    /**
     * 初始化View
     */
    protected void initView() {
    }

    /**
     * 初始化事件
     */
    protected void initListener() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 请求和刷新网络数据
     */
    protected void onRequestData() {
    }

    @Override
    public void onStart() {
        super.onStart();
        mContext = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * 销毁onCreateView（）创建的视图，一般在这个方法里面去处理重复加载页面的逻辑。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
    }

    /**
     * 和activity一样，销毁当前的fragment页面。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (arguments != null) {
            arguments = null;
        }
    }

    /**
     * 解除和activity的关联。
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v) {

    }
}
