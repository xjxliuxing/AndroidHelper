package com.xjx.helper.ui.home.activity.todo.widget

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.LogUtil
import com.xjx.helper.widget.custom.CalendarChooser
import kotlinx.android.synthetic.main.activity_custom_calendar_chooser.*
import java.util.*

/**
 * 自定义日历选择器
 */
class CustomCalendarChooserActivity : CommonBaseTitleActivity() {
    
    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_calendar_chooser
    }
    
    override fun initView() {
        super.initView()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("自定义日历选择器")
    }
    
    override fun initData() {
        super.initData()
        val instance = Calendar.getInstance()
        val instance2 = Calendar.getInstance()
        instance.add(Calendar.DATE, 1)
        
        chooser.setCurrentCalendar(instance)
        instance2.add(Calendar.DATE, 5)
        
        chooser.setItemShow(year = true, month = true, day = true, hour = true, minute = true, second = true)
//        chooser.setEndCalendar(instance2)
        
        chooser.setSelectorListener(object : CalendarChooser.SelectorListener {
            override fun onSelector(year: String, month: String, day: String, hour: String, minute: String, second: String) {
                LogUtil.e("year:$year  month:$month   day:$day   hour:$hour   minute:$minute  second:$second")
            }
        })
        chooser.build()
    }
}