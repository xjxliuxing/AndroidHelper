package com.xjx.helper.ui.home.activity.tod

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus

class Patch9Activity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_patch9
    }

    override fun initData() {
        super.initData()
        SwitchLoadingStatus(PlaceholderStatus.NONE)


    }
}
