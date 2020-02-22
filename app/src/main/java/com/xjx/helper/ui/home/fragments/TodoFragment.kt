package com.xjx.helper.ui.home.fragments

import android.content.Intent
import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseFragment
import com.xjx.helper.ui.home.activity.CustomTime2Activity
import com.xjx.helper.ui.home.activity.CustomTimeActivity
import com.xjx.helper.ui.home.activity.TestActivity
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
        }

        mContext.startActivity(intent)
    }

}
