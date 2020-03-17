package com.xjx.helper.ui.home.activity.tod

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus

class DrawableTextViewActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_drawable_text_view
    }

    override fun initView() {
        super.initView()
        SwitchLoadingStatus(PlaceholderStatus.NONE)

    }
}
