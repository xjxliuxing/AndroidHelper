package com.xjx.helper.ui.home.activity.todo.customviewui

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus

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