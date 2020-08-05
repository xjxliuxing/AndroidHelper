package com.xjx.helper.test

import android.text.TextUtils
import android.view.View
import com.xjx.apphelper.base.CommonBaseTitleActivity
import com.xjx.apphelper.enums.PlaceholderStatus
import com.xjx.apphelper.utils.DateUtils2
import com.xjx.helper.R
import kotlinx.android.synthetic.main.activity_test_input.*

class TestInputActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_test_input
    }

    override fun initView() {
        super.initView()

        SwitchLoadingStatus(PlaceholderStatus.NONE)

        setTitleContent("测试输入数据")

        btn_test.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val content = et_input.text.toString()
                if (!TextUtils.isEmpty(content)) {
                    val longTimeForString = DateUtils2.getInstance().getLongTimeForString(content, "-")

                    tv_content.text = "获取的时间为：$longTimeForString"
                }

            }
        })
    }
}