package com.xjx.helper.ui.home.activity

import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseActivity
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.ToastUtil
import com.xjx.helper.widget.CustomTime
import kotlinx.android.synthetic.main.activity_custom_time.*
import java.util.*

class CustomTime2Activity : CommonBaseTitleActivity() {

    var customTime: CustomTime? = null

    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_time2
    }

    override fun initData() {
        super.initData()

        SwitchLoadingStatus(PlaceholderStatus.NONE)

        setTitleContent("自定义时间选择器2")

        customTime()
    }

    override fun initListener() {
        super.initListener()
        btn_show.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.btn_show -> {
                if (customTime != null) {
                    customTime?.show()
                }
            }
        }
    }

    fun customTime() {

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

        endDate.set(year, month, (day_of_month + 20), hour, minute)


        customTime = CustomTime.getInstance(CommonBaseActivity.mContext, start, endDate)

        customTime?.setOnSelectorListener { year, month, day, hours, minutes ->
            ToastUtil.showToast("" + year + "年" + month + "月" + day + "日" + hours + "时" + minutes + "分")
        }
    }


}
