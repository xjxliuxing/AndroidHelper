package com.xjx.helper.ui.home.activity.todo.customviewui

import com.xjx.apphelper.base.CommonBaseTitleActivity
import com.xjx.apphelper.enums.PlaceholderStatus
import com.xjx.helper.R

class RoundActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_round
    }

    override fun initListener() {
        super.initListener()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
    }
}