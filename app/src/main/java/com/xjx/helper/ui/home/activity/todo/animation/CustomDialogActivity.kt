package com.xjx.helper.ui.home.activity.todo.animation

import android.view.LayoutInflater
import android.view.View
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import kotlinx.android.synthetic.main.activity_custom_dialog.*
import java.util.*

class CustomDialogActivity : CommonBaseTitleActivity() {
    
    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_dialog
    }
    
    private var pvCustomOptions: OptionsPickerView<Any>? = null // 联动选择器
    private lateinit var position1: String
    private lateinit var position2: String
    private val optionTimeList1: ArrayList<String> = ArrayList<String>()
    private val optionTimeList2: ArrayList<ArrayList<String>> = ArrayList<ArrayList<String>>()
    private val mListDate: ArrayList<String> = ArrayList<String>()
    private var mBottomLayoutRes: Int = 0
    
    init {
        // 60分钟
        for (j in 0..59) {
            if (j < 10) {
                mListDate.add("0$j")
            } else {
                mListDate.add(j.toString())
            }
        }
        
        // 24小时
        for (i in 0..23) {
            if (i < 10) {
                optionTimeList1.add("0$i")
            } else {
                optionTimeList1.add(i.toString())
            }
            
            optionTimeList2.add(mListDate)
        }
    }
    
    override fun initData() {
        super.initData()
        
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("自定义Dialog的动画")
        
        val mDialogLayout = LayoutInflater.from(mContext).inflate(R.layout.item_c62xp_charging, null, false)
        val mTimeLayout = LayoutInflater.from(mContext).inflate(R.layout.pickerview_custom_control_air, null, false)
        
        
        sltv.setIds(intArrayOf(R.id.rl_start_time, R.id.rl_end_time), intArrayOf(R.id.tv_selector_start_time, R.id.tv_selector_end_time))
        sltv.setCustomLayout(mContext, mDialogLayout, mTimeLayout)
        
        
        but.setOnClickListener { view ->
            
            sltv.show()
        }
    
        
    }
    
}