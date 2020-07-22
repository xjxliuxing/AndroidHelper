package com.xjx.helper.test

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.LogUtil

/**
 * kotlin的测试类
 */
class TestKotlin : CommonBaseTitleActivity() {

    private var str: String? = "null"

    override fun getTitleLayout(): Int {
        return R.layout.activity_animation_map
    }

    override fun initView() {
        super.initView()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("测试Kotlin")
    }

    override fun initData() {
        super.initData()
        str?.let {
            LogUtil.e("我是数据不为空的逻辑$str")
        }

        str.let {
            LogUtil.e("我是数据为空的逻辑$str")
        }
    }

}