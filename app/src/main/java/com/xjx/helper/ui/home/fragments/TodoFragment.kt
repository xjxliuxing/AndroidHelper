package com.xjx.helper.ui.home.fragments

import android.content.Intent
import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseFragment
import com.xjx.helper.ui.home.activity.tod.*
import com.xjx.helper.ui.home.activity.tod.animation.AnimationMapActivity
import com.xjx.helper.ui.home.activity.tod.customview.CustomHeadVipActivity
import com.xjx.helper.ui.home.activity.tod.customview.CustomViewMapActivity
import com.xjx.helper.ui.home.activity.tod.java.JavaMapActivity
import com.xjx.helper.ui.home.activity.tod.mvp.LiveDataTestActivity

/**
 * 待办的fragmen
 */
class TodoFragment : CommonBaseFragment() {

    override fun getLayout(): Int {
        return R.layout.fragment_todo
    }

    override fun initListener() {
        super.initListener()
        setOnClick(R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8,
                R.id.tv_9, R.id.tv_10, R.id.tv_11, R.id.tv_12, R.id.tv_13, R.id.tv_14, R.id.tv_15, R.id.tv_16,
                R.id.tv_17, R.id.tv_18, R.id.tv_19, R.id.tv_20, R.id.tv_21)
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        val intent = Intent()

        when (v?.id) {
            R.id.tv_1 -> {
                intent.setClass(mContext, CustomTimeActivity::class.java)
            }

            R.id.tv_2 -> {
                intent.setClass(mContext, CustomTime2Activity::class.java)
            }
            R.id.tv_3 -> {
                intent.setClass(mContext, TestActivity::class.java)
            }
            R.id.tv_4 -> {
                intent.setClass(mContext, RecycleViewDivederActivity::class.java)
            }
            R.id.tv_5 -> {
                intent.setClass(mContext, DownLoadActivity::class.java)
            }
            R.id.tv_6 -> {
                intent.setClass(mContext, PlayerActivity::class.java)
            }
            R.id.tv_7 -> {
                intent.setClass(mContext, com.xjx.helper.test.TestActivity::class.java)
            }
            R.id.tv_8 -> {
                intent.setClass(mContext, GridLayoutLayoutActivity::class.java)
            }
            R.id.tv_9 -> {
                intent.setClass(mContext, DrawableTextViewActivity::class.java)
            }
            R.id.tv_10 -> {
                intent.setClass(mContext, Patch9Activity::class.java)
            }
            R.id.tv_11 -> {
                intent.setClass(mContext, GridLayoutLayout2Activity::class.java)
            }
            R.id.tv_12 -> {
                intent.setClass(mContext, NetWorkCurrentKbActivity::class.java)
            }
            R.id.tv_13 -> {
                intent.setClass(mContext, CoortorLayoutActivity::class.java)
            }
            R.id.tv_14 -> {
                intent.setClass(mContext, CustomViewMapActivity::class.java)
            }
            R.id.tv_15 -> {
                intent.setClass(mContext, DialogActivity::class.java)
            }
            R.id.tv_16 -> {
                intent.setClass(mContext, CustomHeadVipActivity::class.java)
            }
            R.id.tv_17 -> {
                intent.setClass(mContext, ActivtyTestActivity::class.java)
            }
            R.id.tv_18 -> {
                intent.setClass(mContext, FragmentTestActivity::class.java)
            }
            R.id.tv_19 -> {
                intent.setClass(mContext, LiveDataTestActivity::class.java)
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
