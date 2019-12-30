package com.xjx.helper.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StyleRes;

import com.xjx.helper.R;
import com.xjx.helper.base.BaseEventMessage;
import com.xjx.helper.global.CommonConstant;

import org.greenrobot.eventbus.EventBus;

public class DialogUtil {

    private static DialogUtil dialogUtil;
    private Activity mActivity;
    private Dialog dialog;
    private View mRootView;

    /**
     * @return 设置全局唯一的dialog
     */
    public static DialogUtil getInstance(Activity activity) {
        dialogUtil = new DialogUtil(activity);
        return dialogUtil;
    }

    public static DialogUtil getInstance(Activity activity, boolean DimEnabled) {
        dialogUtil = new DialogUtil(activity, DimEnabled);
        return dialogUtil;
    }

    public DialogUtil(Activity activity) {
        this.mActivity = activity;

        // 避免重复出现弹窗
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialog = new Dialog(mActivity, R.style.base_dialog);

        // dialog展示时候的监听
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                EventBus.getDefault().post(new BaseEventMessage(CommonConstant.CODE_DIALOG_UTIL_SHOW));
            }
        });

        // dialog 关闭时候的监听
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EventBus.getDefault().post(new BaseEventMessage(CommonConstant.CODE_DIALOG_UTIL_CLOSE));
            }
        });
    }

    public DialogUtil(Activity activity, boolean DimEnabled) {
        this.mActivity = activity;

        // 避免重复出现弹窗
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        dialog = new Dialog(mActivity, R.style.dialog_hint);

        // dialog展示时候的监听
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                EventBus.getDefault().post(new BaseEventMessage(CommonConstant.CODE_DIALOG_UTIL_SHOW));
            }
        });

        // dialog 关闭时候的监听
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EventBus.getDefault().post(new BaseEventMessage(CommonConstant.CODE_DIALOG_UTIL_CLOSE));
            }
        });
    }

    public DialogUtil setContentView(@LayoutRes int resource) {

        mRootView = LayoutInflater.from(mActivity).inflate(resource, (ViewGroup) mActivity.findViewById(android.R.id.content), false);
        if (mRootView != null) {
            // 先移除，然后在添加，避免崩溃
            if (mRootView != null) {
                ViewGroup parent = (ViewGroup) mRootView.getParent();
                if (parent != null) {
                    parent.removeAllViews();
                }
            }

            dialog.setContentView(mRootView);

            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.WRAP_CONTENT;

            //弹窗点击周围空白处弹出层自动消失弹窗消失(false时为点击周围空白处弹出层不自动消失)
            dialog.setCanceledOnTouchOutside(true);

            //保持布局状态
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiOptions |= 0x00001000;
            dialog.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        }

        return dialogUtil;
    }

    /**
     * 设置dialog的高度，默认的设置为
     *
     * @param height
     * @return
     */
    public DialogUtil setHeight(int height) {

        if (dialog != null) {
            if (height <= 0) {
                dialog.getWindow().getAttributes().height = WindowManager.LayoutParams.MATCH_PARENT;
            } else {
                dialog.getWindow().getAttributes().height = height;
            }
        }

        return dialogUtil;
    }

    public DialogUtil setGravity(int gravity) {
        if (dialog != null) {
            dialog.getWindow().setGravity(gravity);
        }
        return dialogUtil;
    }

    /**
     * @param resId
     * @return 设置弹窗的动画效果
     */
    public DialogUtil setWindowAnimations(@StyleRes int resId) {
        if (resId != 0) {
            dialog.getWindow().setWindowAnimations(resId);

        }
        return dialogUtil;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public DialogUtil setClose(@IdRes int id) {
        if (mRootView != null) {
            View view = this.mRootView.findViewById(id);
            if (view != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }
        }
        return dialogUtil;
    }

    /**
     * 设置title内容，title的id必须是：R.id.tv_title
     *
     * @param conten
     * @return
     */
    public DialogUtil setTitle(int id, String conten) {
        if (!TextUtils.isEmpty(conten)) {
            if (mRootView != null) {
                TextView textView = mRootView.findViewById(id);
                if (textView != null) {
                    textView.setText(conten);
                }
            }
        }
        return dialogUtil;
    }

    public DialogUtil setMsg(int id, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if (mRootView != null) {
                TextView tvMsg = mRootView.findViewById(id);
                if (tvMsg != null) {
                    tvMsg.setText(msg);
                }
            }
        }
        return dialogUtil;
    }

    /**
     * @param viewId 对象id
     * @return 获取指定类型的View或者ViewGrounp
     */
    public <T> T getChildView(int viewId) {
        if (mRootView != null) {
            return (T) mRootView.findViewById(viewId);
        } else {
            return null;
        }
    }

    /**
     * @param bindDataListener
     * @return 用来绑定子View数据的接口
     */
    public DialogUtil setBindViewData(BindDataListener bindDataListener) {
        if (bindDataListener != null) {
            bindDataListener.onBindData(mRootView);
        }
        return dialogUtil;
    }

    /**
     * @param cancel
     * @return 弹窗点击周围空白处弹出层自动消失弹窗消失(false时为点击周围空白处弹出层不自动消失)
     */
    public DialogUtil setCanceledOnTouchOutside(boolean cancel) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
        }
        return dialogUtil;
    }

    /**
     * @param flag
     * @return 设置点击返回键的时候，是否可以取消， true：可以取消，false ：不可以取消
     */
    public DialogUtil setCancelable(boolean flag) {
        if (dialog != null) {
            dialog.setCancelable(flag);
        }
        return dialogUtil;
    }

    public View getRootView() {
        if (mRootView != null) {
            return mRootView;
        } else {
            return null;
        }
    }

    public DialogUtil show() {
        if ((dialog != null) && (mActivity != null) && (!mActivity.isFinishing()) && (!dialog.isShowing())) {
            dialog.show();
        }
        return dialogUtil;
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * @param leftTitle
     * @param leftClickListener
     * @return 左侧的点击事件
     */
    public DialogUtil setLeftClickListener(int id, String leftTitle, final LeftClickListener leftClickListener) {
        if (mRootView != null) {
            TextView leftView = mRootView.findViewById(id);
            if (leftView != null) {
                // 设置数据
                leftView.setText(leftTitle);
                // 点击事件
                leftView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (leftClickListener != null) {
                            leftClickListener.onLeftItemClick();
                        }
                    }
                });
            }
        }
        return dialogUtil;
    }

    /**
     * @param middleContext
     * @param middleClickListener
     * @return 中间点击事件
     */
    public DialogUtil setMiddleClickListener(int id, String middleContext, final MiddleClickListener middleClickListener) {
        if (mRootView != null) {
            TextView middle = mRootView.findViewById(id);
            if (middle != null) {
                middle.setText(middleContext);
                middle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (middleClickListener != null) {
                            middleClickListener.onMiddleItemClick();
                        }
                    }
                });
            }
        }
        return dialogUtil;
    }

    /**
     * @param rightTitle
     * @param rightClickListener
     * @return 右侧的点击事件
     */
    public DialogUtil setRightClickListener(int id, String rightTitle, final RightClickListener rightClickListener) {
        if (mRootView != null) {
            TextView tvRight = mRootView.findViewById(id);
            if (tvRight != null) {
                tvRight.setText(rightTitle);
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rightClickListener != null) {
                            rightClickListener.onRightItemClick();
                        }
                    }
                });
            }
        }
        return dialogUtil;
    }

    public interface LeftClickListener {
        void onLeftItemClick();
    }

    public interface MiddleClickListener {
        void onMiddleItemClick();
    }

    public interface RightClickListener {
        void onRightItemClick();
    }

    public interface BindDataListener {
        void onBindData(View rootView);
    }

}
