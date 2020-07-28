package com.xjx.helper.ui.home.activity.tod.java

import android.content.Intent
import android.view.View
import com.xjx.helper.R
import com.xjx.apphelper.base.CommonBaseTitleActivity
import com.xjx.apphelper.enums.PlaceholderStatus

/**
 * java的集合类
 */
class JavaMapActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_java_map
    }

    override fun initView() {
        super.initView()
        setTitleContent("Java 的集合类")
        SwitchLoadingStatus(PlaceholderStatus.NONE)
    }

    override fun initListener() {
        super.initListener()
        setOnClick(R.id.tv_1)
    }


    override fun onClick(v: View?) {
        super.onClick(v)
        val intent = Intent()
        when (v?.id) {
            R.id.tv_1 -> {
                intent.setClass(mContext, ThreadExecutorActivity::class.java)
            }
        }
        startActivity(intent)
    }

}