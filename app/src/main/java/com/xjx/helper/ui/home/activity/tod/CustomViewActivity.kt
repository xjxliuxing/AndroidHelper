package com.xjx.helper.ui.home.activity.tod

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus

class CustomViewActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_view
    }

    override fun initData() {
        super.initData()

        SwitchLoadingStatus(PlaceholderStatus.NONE)

        setTitleContent("自定义View")


    }

}
