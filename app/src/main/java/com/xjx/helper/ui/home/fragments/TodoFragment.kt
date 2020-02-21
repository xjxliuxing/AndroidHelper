package com.xjx.helper.ui.home.fragments

import android.content.Intent
import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseFragment
import com.xjx.helper.ui.home.activity.CustomTimeActivity
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
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        val intent = Intent()

        when (v?.id) {
            R.id.tv_1 -> {
                intent.setClass(mContext, CustomTimeActivity::class.java)
            }
        }

        mContext.startActivity(intent)
    }

}
