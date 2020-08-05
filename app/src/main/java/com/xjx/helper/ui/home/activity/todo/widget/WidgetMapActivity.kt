package com.xjx.helper.ui.home.activity.todo.widget

import NetWorkCurrentKbActivity
import android.content.Intent
import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus

class WidgetMapActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_widget_map
    }

    override fun initData() {
        super.initData()
        SwitchLoadingStatus(PlaceholderStatus.NONE)

        setTitleContent("自定义组件的集合")

        setOnClick(R.id.tv_widget_1, R.id.tv_widget_2, R.id.tv_widget_3)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        val intent = Intent()
        when (v?.id) {
            R.id.tv_widget_1 -> {
                intent.setClass(mContext, CustomTime2Activity::class.java)

            }
            R.id.tv_widget_2 -> {
                intent.setClass(mContext, RecycleViewDivederActivity::class.java)

            }
            R.id.tv_widget_3 -> {
                intent.setClass(mContext, NetWorkCurrentKbActivity::class.java)
            }
        }
        startActivity(intent)
    }
}