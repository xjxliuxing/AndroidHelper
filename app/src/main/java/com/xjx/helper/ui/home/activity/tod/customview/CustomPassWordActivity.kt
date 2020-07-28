package com.xjx.helper.ui.home.activity.tod.customview

import com.xjx.helper.R
import com.xjx.apphelper.base.CommonBaseTitleActivity
import com.xjx.apphelper.enums.PlaceholderStatus

/**
 * 自定义密码输入框
 */
class CustomPassWordActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_password;
    }

    override fun initData() {
        super.initData()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("自定义密码输入框")
    }

}