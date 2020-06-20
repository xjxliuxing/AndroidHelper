package com.xjx.helper.ui.home.activity.tod.customview

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.LogUtil
import com.xjx.helper.widget.custom.CustomRandomLayout

/**
 * 自定义随机布局
 */
class CustomRandomLayoutActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_random_layout
    }

    override fun initData() {
        super.initData()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("自定义随机布局")

        val textView1 = TextView(mContext)
        textView1.text = "测试1"
        textView1.setPadding(20, 20, 20, 20)
        textView1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_1))
        val textView2 = TextView(mContext)
        textView2.text = "测试2"
        textView2.setPadding(20, 20, 20, 20)
        textView2.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_1))
        val textView3 = TextView(mContext)
        textView3.text = "测试3"
        textView3.setPadding(20, 20, 20, 20)
        textView3.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_1))
        val textView4 = TextView(mContext)
        textView4.text = "测试4"
        textView4.setPadding(20, 20, 20, 20)
        textView4.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_1))

        val layout = findViewById<CustomRandomLayout<String>>(R.id.cs_random_layout1)

        layout.addViewAtRandomXY(textView1, "测试1")
        layout.addViewAtRandomXY(textView2, "测试2")
        layout.addViewAtRandomXY(textView3, "测试3")
        layout.addViewAtRandomXY(textView4, "测试4")

        layout.setOnRandomItemClickListener { view, str ->
            LogUtil.e("---->:$str")
        }
    }
}