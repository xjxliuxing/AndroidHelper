package com.xjx.helper.ui.home.activity.todo.customviewui

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus

class RoundActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_round
    }

    override fun initListener() {
        super.initListener()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
    }
}