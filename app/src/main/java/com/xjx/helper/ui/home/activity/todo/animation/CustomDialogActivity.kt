package com.xjx.helper.ui.home.activity.todo.animation

import android.view.LayoutInflater
import android.widget.TextView
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.LogUtil
import com.xjx.helper.widget.custom.CalendarChooser
import com.xjx.helper.widget.custom.CustomScrollSelectorDialog
import kotlinx.android.synthetic.main.activity_custom_dialog.*
import java.util.*

class CustomDialogActivity : CommonBaseTitleActivity() {
    
    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_dialog
    }
    
    override fun initData() {
        super.initData()
        
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("自定义Dialog的动画")
        
        val mDialogLayout = LayoutInflater.from(mContext).inflate(R.layout.item_c62xp_charging, null, false)
        val mTimeLayout = LayoutInflater.from(mContext).inflate(R.layout.pickerview_custom_control_air, null, false)
        
        // 设置日历选择器对象
        val chooser = CalendarChooser(mContext)
        val instance = Calendar.getInstance()
        val instance2 = Calendar.getInstance()
        instance.add(Calendar.DATE, 1)
        chooser.setCurrentCalendar(instance)
        instance2.add(Calendar.DATE, 5)
        chooser.setItemShow(year = true, month = true, day = true, hour = true, minute = true, second = true)
        // chooser.setEndCalendar(instance2)
        chooser.build()
        
        sltv.setIds(intArrayOf(R.id.tv_selector_start_time, R.id.tv_selector_end_time), intArrayOf(R.id.tv_selector_start_time, R.id.tv_selector_end_time))
        sltv.setCustomLayout(mContext, mDialogLayout, chooser)
        sltv.setSaveListener(object : CustomScrollSelectorDialog.SaveListener {
            override fun onSave(textView: TextView, year: String, month: String, day: String, hour: String, minute: String, second: String) {
                val value = "year:$year  month:$month  day:$day  hour:$hour  minute:$minute  second:$second"
                LogUtil.e(value)
                textView.text = value
                sltv.returnData()
            }
        })
        
        but.setOnClickListener { view ->
            sltv.show()
        }
        
    }
    
}