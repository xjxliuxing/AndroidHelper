package com.xjx.helper.ui.home.fragments

import android.content.Intent
import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseFragment
import com.xjx.helper.ui.home.activity.tod.animation.AnimationMapActivity
import com.xjx.helper.ui.home.activity.tod.customview.CustomViewMapActivity
import com.xjx.helper.ui.home.activity.tod.java.JavaMapActivity
import com.xjx.helper.ui.home.activity.tod.widget.WidgetMapActivity

/**
 * 待办的fragmen
 */
class TodoFragment : CommonBaseFragment() {

    override fun getLayout(): Int {
        return R.layout.fragment_todo
    }

    override fun initListener() {
        super.initListener()
        setOnClick(R.id.tv_custom_widget, R.id.tv_14, R.id.tv_20, R.id.tv_21)
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        val intent = Intent()

        when (v?.id) {
            R.id.tv_custom_widget -> {
                intent.setClass(mContext, WidgetMapActivity::class.java)
            }

            R.id.tv_14 -> {
                intent.setClass(mContext, CustomViewMapActivity::class.java)
            }
            R.id.tv_20 -> {
                intent.setClass(mContext, AnimationMapActivity::class.java)
            }
            R.id.tv_21 -> {
                intent.setClass(mContext, JavaMapActivity::class.java)
            }
        }
        mContext.startActivity(intent)
    }

}
