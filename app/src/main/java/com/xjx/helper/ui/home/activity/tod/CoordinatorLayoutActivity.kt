package com.xjx.helper.ui.home.activity.tod

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus

/**
 *  @作者           徐腾飞
 *  @创建时间       2020/3/23  13:25
 *  @更新者         HongJing
 *  @更新时间       2020/3/23  13:25
 *  @描述           协调布局
 */
class CoortorLayoutActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_coordinator_layout
    }

    override fun initData() {
        super.initData()
        SwitchLoadingStatus(PlaceholderStatus.NONE)

        setTitleContent("协调布局")


    }

}
