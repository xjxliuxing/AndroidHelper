package com.xjx.helper.ui.home.fragments

import android.content.Intent
import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseFragment
import com.xjx.helper.ui.home.activity.tod.*
import kotlinx.android.synthetic.main.fragment_todo.*


/**
 * 待办的fragmen
 */
class TodoFragment : CommonBaseFragment() {

    override fun getLayout(): Int {
        return R.layout.fragment_todo
    }

    override fun initListener() {
        super.initListener()
        tv_1.setOnClickListener(this)
        tv_2.setOnClickListener(this)
        tv_3.setOnClickListener(this)
        tv_4.setOnClickListener(this)
        tv_5.setOnClickListener(this)
        tv_6.setOnClickListener(this)
        tv_7.setOnClickListener(this)
        tv_8.setOnClickListener(this)
        tv_9.setOnClickListener(this)
        tv_10.setOnClickListener(this)
        tv_11.setOnClickListener(this)
        tv_12.setOnClickListener(this)
        tv_13.setOnClickListener(this)
        tv_14.setOnClickListener(this)
        tv_15.setOnClickListener(this)
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
                intent.setClass(mContext, CustomViewActivity::class.java)
            }
            R.id.tv_15 -> {
                intent.setClass(mContext, DialogActivity::class.java)
            }
        }
        mContext.startActivity(intent)
    }

}
