package com.xjx.helper.ui.home.activity

import android.view.View
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import kotlinx.android.synthetic.main.activity_test.*
import java.text.SimpleDateFormat
import java.util.*


class TestActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return com.xjx.helper.R.layout.activity_test
    }

    override fun initListener() {
        super.initListener()
        button.setOnClickListener(this)
    }

    override fun initData() {
        super.initData()

        SwitchLoadingStatus(PlaceholderStatus.NONE)

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            com.xjx.helper.R.id.button -> {

                val current = Calendar.getInstance()
                current.time = Date()//填入当前时间

                //当前年
                val year = current.get(Calendar.YEAR)
                //当前月
                val month = current.get(Calendar.MONTH)
                //当前月的第几天：即当前日
                val day_of_month = current.get(Calendar.DAY_OF_MONTH)
                //当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
                val hour = current.get(Calendar.HOUR_OF_DAY)
                //当前分
                val minute = current.get(Calendar.MINUTE)

                // 开始时间
                val start = Calendar.getInstance()
                start.set(year, month, day_of_month + 1, hour, minute)

                val endDate = Calendar.getInstance()
                endDate.set(year, month, day_of_month + 10, hour, minute)

                val s = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                tv1.text = "当前时间：" + start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH) + 1) + "-" + start.get(Calendar.DAY_OF_MONTH) + " " + start.get(Calendar.HOUR_OF_DAY) + ":" + start.get(Calendar.MINUTE)
                tv2.text = "当前时间：" + endDate.get(Calendar.YEAR) + "-" + (endDate.get(Calendar.MONTH) + 1) + "-" + endDate.get(Calendar.DAY_OF_MONTH) + " " + endDate.get(Calendar.HOUR_OF_DAY) + ":" + endDate.get(Calendar.MINUTE)


            }
        }
    }


}
