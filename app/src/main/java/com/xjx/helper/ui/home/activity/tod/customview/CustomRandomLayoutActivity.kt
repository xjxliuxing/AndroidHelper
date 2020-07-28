package com.xjx.helper.ui.home.activity.tod.customview

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.xjx.helper.R
import com.xjx.apphelper.base.CommonBaseTitleActivity
import com.xjx.apphelper.enums.PlaceholderStatus
import com.xjx.apphelper.utils.LogUtil
import com.xjx.helper.widget.custom.CustomRandomLayout
import kotlinx.android.synthetic.main.activity_custom_random_layout.*

/**
 * 自定义随机布局
 */
class CustomRandomLayoutActivity : CommonBaseTitleActivity() {

    val mList: ArrayList<TextView> = arrayListOf()
    val mList2: ArrayList<TextView> = arrayListOf()

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

        mList.add(textView1)
        mList.add(textView2)
        mList.add(textView3)
        mList.add(textView4)

        for (item in mList.indices) {
            layout.addViewAtRandomXY(mList[item], mList[item].text.toString())
        }

        layout.setOnRandomItemClickListener { view, str ->
            LogUtil.e("---->:$str")
        }

        for (item in mList.indices) {
            val text = TextView(mContext)
            val paramts = LinearLayout.LayoutParams(30, 3)
            paramts.leftMargin = 20
            text.layoutParams = paramts
            text.setBackgroundColor(ContextCompat.getColor(mContext, R.color.blue_1))

            mList2.add(text)
            cl_layout.addView(text)
        }
    }
}