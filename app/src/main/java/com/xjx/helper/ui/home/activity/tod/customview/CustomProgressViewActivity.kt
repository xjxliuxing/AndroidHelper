package com.xjx.helper.ui.home.activity.tod.customview

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import kotlinx.android.synthetic.main.activity_custom_progress.*

/**
 *带进度条的View
 */
class CustomProgressViewActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_progress;
    }

    override fun initView() {
        super.initView()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("进度条的View")
    }

    override fun onRestart() {
        super.onRestart()

        if (custom_pbssss != null) {
            custom_pbssss.setProgress(50f)
        }
    }

}