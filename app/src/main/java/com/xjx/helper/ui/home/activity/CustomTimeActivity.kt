package com.xjx.helper.ui.home.activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import kotlinx.android.synthetic.main.activity_custom_time.*
import java.util.*

/**
 * 自定义时间选择器
 */
class CustomTimeActivity : CommonBaseTitleActivity() {

    var pvCustomTime: TimePickerView? = null

    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_time
    }

    override fun initData() {
        super.initData()

        SwitchLoadingStatus(PlaceholderStatus.NONE)

        setTitleContent("自定义时间选择器")

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
                if (pvCustomTime != null) {
                    pvCustomTime?.show()
                }

            }
        }
    }

    fun customTime() {
        val selectedDate = Calendar.getInstance()//系统当前时间
        val startDate = Calendar.getInstance()
        startDate.set(2014, 1, 23)
        val endDate = Calendar.getInstance()
        endDate.set(2027, 2, 28)
        //时间选择器 ，自定义布局
        pvCustomTime = TimePickerBuilder(this, OnTimeSelectListener { date, v ->
            //选中事件回调
//            btn_CustomTime.setText(getTime(date))
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time) { v ->
                    val tvSubmit = v.findViewById<View>(R.id.tv_finish) as TextView
                    val ivCancel = v.findViewById<View>(R.id.iv_cancel) as ImageView
                    tvSubmit.setOnClickListener {
                        pvCustomTime?.returnData()
                        pvCustomTime?.dismiss()
                    }
                    ivCancel.setOnClickListener {
                        pvCustomTime?.dismiss()
                    }
                }
                .setContentTextSize(18)
                .setType(booleanArrayOf(false, false, false, true, true, true))
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(-0xdb5263)
                .build()
    }
}
