package com.xjx.helper.widget.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter
import com.contrarywind.view.WheelView
import com.xjx.helper.R
import com.xjx.helper.utils.LogUtil
import org.jetbrains.annotations.NotNull
import java.util.*

/**
 * 自定义日历选择器
 * 这里使用view的方式，让别人去在xml中引用
 */
class CalendarChooser(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    
    private lateinit var inflater: View
    private lateinit var options1: WheelView
    private lateinit var options2: WheelView
    private lateinit var options3: WheelView
    private lateinit var options4: WheelView
    private lateinit var options5: WheelView
    private lateinit var options6: WheelView
    private val mShowArray: BooleanArray = booleanArrayOf(true, true, true, true, true, true)// 显示view的数组，默认全都可见
    private val mViewArray: ArrayList<WheelView> = arrayListOf() // WheelView集合的数组
    private var mCurrentCalendar: Calendar? = null // 当前的日历对象
    private var mEndCalendar: Calendar? = null // 结束的日历对象
    
    private var mStartYear: Int? = null
    private var mStartMonth: Int? = null
    private var mStartDay: Int? = null
    private var mStartHour: Int? = null
    private var mStartMinute: Int? = null
    private var mStartSecond: Int? = null
    
    private var mEndYear: Int? = null
    private var mEndMonth: Int? = null
    private var mEndDay: Int? = null
    private var mEndHour: Int? = null
    private var mEndMinute: Int? = null
    private var mEndSecond: Int? = null
    
    private val mOptionTimeList1: ArrayList<String> = arrayListOf()
    private val mOptionTimeList2: ArrayList<String> = arrayListOf()
    private val mOptionTimeList3: ArrayList<String> = arrayListOf()
    private val mOptionTimeList4: ArrayList<String> = arrayListOf()
    private val mOptionTimeList5: ArrayList<String> = arrayListOf()
    private val mOptionTimeList6: ArrayList<String> = arrayListOf()
    
    // 当前选中时间的具体值
    private var mCurrentYear: String = "" // 年份
    private var mCurrentMonth: String = "" // 月份
    private var mCurrentDay: String = "" // 日
    private var mCurrentHour: String = "" // 时
    private var mCurrentMinute: String = "" // 分
    private var mCurrentSecond: String = "" // 秒
    
    // 设置默认的数据
    private val mDefaultMonth = "1"
    private val mDefaultDay = "1"
    private val mDefaultHour = "00"
    private val mDefaultMinute = "00"
    private val mDefaultSecond = "00"
    
    private var mListener: SelectorListener? = null
    
    companion object {
        private const val DEFAULT_END_YEAR: Int = 5 // 设置默认增加的年份
    }
    
    constructor(context: Context) : this(context, null)
    
    init {
        initView()
    }
    
    @SuppressLint("InflateParams")
    private fun initView() {
        inflater = LayoutInflater.from(context).inflate(R.layout.custom_calendar_chooser, null, false)
        
        // 获取年月日时分秒的view
        options1 = inflater.findViewById(R.id.options1)
        options2 = inflater.findViewById(R.id.options2)
        options3 = inflater.findViewById(R.id.options3)
        options4 = inflater.findViewById(R.id.options4)
        options5 = inflater.findViewById(R.id.options5)
        options6 = inflater.findViewById(R.id.options6)
        
        // 添加集合
        mViewArray.add(options1)
        mViewArray.add(options2)
        mViewArray.add(options3)
        mViewArray.add(options4)
        mViewArray.add(options5)
        mViewArray.add(options6)
        
        // 是否循环数组
        options1.setCyclic(false)
        options2.setCyclic(false)
        options3.setCyclic(false)
        options4.setCyclic(false)
        options5.setCyclic(false)
        options6.setCyclic(false)
    }
    
    /**
     * 设置要显示的item的view，从左到右的顺序分别为，年、月、日、时、分、秒
     */
    fun setItemShow(year: Boolean, month: Boolean, day: Boolean, hour: Boolean, minute: Boolean, second: Boolean): CalendarChooser {
        mShowArray[0] = year
        mShowArray[1] = month
        mShowArray[2] = day
        mShowArray[3] = hour
        mShowArray[4] = minute
        mShowArray[5] = second
        
        return this
    }
    
    /**
     * 设置view的显示选项
     */
    private fun setShowView() {
        
        for (index in mShowArray.indices) {
            val isShow = mShowArray[index]
            val wheelView = mViewArray[index]
            if (!isShow) {
                wheelView.visibility = View.GONE
            } else {
                wheelView.visibility = View.VISIBLE
            }
        }
    }
    
    /**
     * 设置当前的日历对象
     */
    fun setCurrentCalendar(@NotNull currentCalendar: Calendar): CalendarChooser {
        this.mCurrentCalendar = currentCalendar
        return this
    }
    
    /**
     * 设置结束的日历对象
     */
    fun setEndCalendar(@NotNull endCalendar: Calendar): CalendarChooser {
        this.mEndCalendar = endCalendar
        return this
    }
    
    /**
     * 构造数据的方法，在show方法之前，必须调用
     */
    fun build(): CalendarChooser {
        
        // 设置展示那些view
        setShowView()
        
        initDate()
        return this
    }
    
    /**
     * 初始化日期的数据
     */
    private fun initDate() {
        
        // 获取开始时间的具体年月日时分秒
        if (mCurrentCalendar != null) {
            mCurrentCalendar?.let {
                mStartYear = it[Calendar.YEAR]
                mStartMonth = it[Calendar.MONTH] + 1
                mStartDay = it[Calendar.DAY_OF_MONTH]
                mStartHour = it[Calendar.HOUR_OF_DAY]
                mStartMinute = it[Calendar.MINUTE]
                mStartSecond = it[Calendar.SECOND]
                
                // 默认数据赋值
                mCurrentYear = mStartYear.toString()
                mCurrentMonth = mStartMonth.toString()
                mCurrentDay = mStartDay.toString()
                mCurrentHour = mStartHour.toString()
                mCurrentMinute = mStartMinute.toString()
                mCurrentSecond = mStartSecond.toString()
            }
        } else {
            LogUtil.e("获取当前时间的日期失败")
            return
        }
        
        // 获取结束时间
        if (mEndCalendar == null) {
            // 设置默认的结束时间，如果没有结束的时间，就默认使用开始的时间，然后给他加上默认的二十年,具体的年月日为最后一年的最后一天
            mEndCalendar = Calendar.getInstance()
            // 设置默认的数据
            mEndCalendar?.set(Calendar.YEAR, ((mStartYear!!) + (DEFAULT_END_YEAR)))
            mEndCalendar?.set(Calendar.MONTH, 11) // 从0开始，11 是最大的值
            mEndCalendar?.set(Calendar.DAY_OF_MONTH, 31)
            mEndCalendar?.set(Calendar.HOUR_OF_DAY, 23)
            mEndCalendar?.set(Calendar.MINUTE, 59)
            mEndCalendar?.set(Calendar.SECOND, 59)
        }
        
        // 获取结束的日期
        mEndYear = mEndCalendar!!.get(Calendar.YEAR)
        mEndMonth = mEndCalendar!!.get(Calendar.MONTH) + 1
        mEndDay = mEndCalendar!!.get(Calendar.DAY_OF_MONTH)
        mEndHour = mEndCalendar!!.get(Calendar.HOUR_OF_DAY)
        mEndMinute = mEndCalendar!!.get(Calendar.MINUTE)
        mEndSecond = mEndCalendar!!.get(Calendar.SECOND)
        
        // 设置默认的年份
        if (mStartYear != null && mEndYear != null) {
            // 开始时间和结束时间都有，就存入所有的年份数据
            for (index in mStartYear!!..mEndYear!!) {
                mOptionTimeList1.add((index.toString() + "年"))
            }
        }
        LogUtil.e("年份的集合：${listOf(mOptionTimeList1)}")
        
        // 设置默认的月份
        if (mShowArray[1]) {
            setMonthForYear()
            LogUtil.e("月份的集合：${listOf(mOptionTimeList2)}")
        }
        
        // 设置默认的天数
        if (mShowArray[2]) {
            setDayForMonth()
            LogUtil.e("天数的集合：${listOf(mOptionTimeList3)}")
        }
        
        // 设置默认的小时
        if (mShowArray[3]) {
            setHourForDay()
            LogUtil.e("小时的集合：${listOf(mOptionTimeList4)}")
        }
        
        // 设置默认的分钟
        if (mShowArray[4]) {
            setMinuteForHour()
            LogUtil.e("分钟的集合：${listOf(mOptionTimeList5)}")
        }
        if (mShowArray[5]) {
            // 设置默认的秒值
            setSecondForMinute()
            LogUtil.e("秒值的集合：${listOf(mOptionTimeList6)}")
        }
        
        // 添加view
        addView(inflater)
        
        initWheelView()
    }
    
    /**
     * 根据年份设置月份
     */
    private fun setMonthForYear() {
        /**
         * 业务逻辑：
         *      1：计算出当前年份剩下的月份
         *      2：计算出最后一年剩余的月份
         *      3：中间的年份都是12个月的
         */
        var start = 0
        var end = 0
        
        // 说明是第一年
        val year = mCurrentYear.replace("年", "").toInt()
        if (year == mStartYear) {
            start = mStartMonth!!
            // 此处如果还有下一年，则可以这么写，如果没有，那么就以后面月份为主
            end = if (mStartYear!!.toInt() == mEndYear!!.toInt()) {
                mEndMonth!!
            } else {
                12
            }
            
        } else if (year == mEndYear) {
            // 说明是最后一年
            start = 1
            end = mEndMonth!!
        } else {
            start = 1
            end = 12
        }
        
        LogUtil.e("start:$start end $end")
        
        mOptionTimeList2.clear()
        var value = ""
        for (index in start..end) {
            value = if (index < 10) {
                "0$index"
            } else {
                index.toString()
            }
            mOptionTimeList2.add(value + "月")
        }
        options2.adapter = ArrayWheelAdapter(mOptionTimeList2 as List<String>)
        
        // 重新设置默认的角标
        options2.currentItem = 0
    }
    
    /**
     * 根据年月去获取当前月份一共剩余几天
     */
    private fun setDayForMonth() {
        
        mOptionTimeList3.clear()
        
        var start = 0
        var end = 0  // 此处因为默认的结束日期设置的是1，所以这里要加上1
        
        val year = mCurrentYear.replace("年", "").toInt()
        val month = mCurrentMonth.replace("月", "").toInt()
        if ((year == mStartYear) && (month == mStartMonth)) {
            // 说明是第一个月份
            start = mStartDay!!
            // 如果年月都相同，说明是同一年同一月，end 使用最后的天数
            end = if ((year == mEndYear!!.toInt()) && (month == mEndMonth!!.toInt())) {
                mEndDay!!
            } else {
                getMaxDayForMonth(mStartYear!!, mStartMonth!!)
            }
            
        } else if (year == mEndYear && month == mEndMonth) {
            // 说明是最后一个月
            start = 1
            end = mEndDay!!
        } else {
            // 中间的月份
            val instance = Calendar.getInstance()
            instance.set(Calendar.YEAR, year)
            instance.set(Calendar.MONTH, month)
            val maxDayForMonth = getMaxDayForMonth(year, month)
            start = 1
            end = maxDayForMonth
        }
        
        LogUtil.e("天数：---> start:$start   end :$end")
        var value = ""
        for (index in start..end) {
            if (index < 10) {
                value = "0$index"
            } else {
                value = index.toString()
            }
            mOptionTimeList3.add(value + "日")
        }
        options3.adapter = ArrayWheelAdapter(mOptionTimeList3 as List<String>)
        // 重新设置默认的角标
        options3.currentItem = 0
    }
    
    /**
     * 根据每一天设置当前的小时
     */
    private fun setHourForDay() {
        mOptionTimeList4.clear()
        
        var start = 0
        var end = 0
        
        val year = mCurrentYear.replace("年", "").toInt()
        val month = mCurrentMonth.replace("月", "").toInt()
        val day = mCurrentDay.replace("日", "").toInt()
        
        if (year == mStartYear && month == mStartMonth && day == mStartDay) {
            // 第一个月份的小时
            start = mStartHour!!
            // 这里判断，只要不是同一年，同一月，同一天，就都是end == 23,否则就是以后面的小时为准
            end = if ((mStartYear!!.toInt() == mEndYear!!.toInt()) && (mEndMonth!!.toInt() == mStartMonth!!.toInt()) && (mEndDay!!.toInt() == mStartDay!!.toInt())) {
                mEndHour!!.toInt()
            } else {
                23
            }
        } else if (year == mEndYear && month == mEndMonth && day == mEndDay) {
            // 最后月份的天中的小时
            start = 0
            end = mEndHour!!
        } else {
            start = 0
            end = 23
        }
        
        LogUtil.e("小时 ----> start :$start  end: $end")
        var value = ""
        for (index in start..end) {
            if (index < 10) {
                value = "0$index"
            } else {
                value = index.toString()
            }
            mOptionTimeList4.add(value + "时")
        }
        options4.adapter = ArrayWheelAdapter(mOptionTimeList4 as List<String>)
        // 重新设置默认的角标
        options4.currentItem = 0
    }
    
    /**
     * 根据您每一小时设置当前一共多少分钟
     */
    private fun setMinuteForHour() {
        mOptionTimeList5.clear()
        
        var start = 0
        var end = 0
        
        val year = mCurrentYear.replace("年", "").toInt()
        val month = mCurrentMonth.replace("月", "").toInt()
        val day = mCurrentDay.replace("日", "").toInt()
        val hour = mCurrentHour.replace("时", "").toInt()
        
        if (year == mStartYear && month == mStartMonth && day == mStartDay && hour == mStartHour) {
            start = mStartMinute!!
            
            // 如果年月日时都相同，则end以结束的分钟为准
            if ((mStartYear!!.toInt() == mEndYear!!.toInt()) && (mStartMonth!!.toInt() == mEndMonth!!.toInt()) &&
                    (mStartDay!!.toInt() == mEndDay!!.toInt()) && (mStartHour!!.toInt() == mEndHour!!.toInt())) {
                end = mEndMinute!!.toInt()
            } else {
                end = 59
            }
        } else if (year == mEndYear && month == mEndMonth && day == mEndDay && hour == mEndHour) {
            start = 0
            end = mEndMinute!!
        } else {
            start = 0
            end = 59
        }
        
        LogUtil.e("小时： start:$start   end $end")
        var value = ""
        for (index in start..end) {
            if (index < 10) {
                value = "0$index"
            } else {
                value = index.toString()
            }
            mOptionTimeList5.add(value + "分")
        }
        options5.adapter = ArrayWheelAdapter(mOptionTimeList5 as List<String>)
        // 重新设置默认的角标
        options5.currentItem = 0
    }
    
    /**
     *根据您每一分钟设置当前的秒数
     */
    private fun setSecondForMinute() {
        mOptionTimeList6.clear()
        
        var start = 0
        var end = 0
        
        val year = mCurrentYear.replace("年", "").toInt()
        val month = mCurrentMonth.replace("月", "").toInt()
        val day = mCurrentDay.replace("日", "").toInt()
        val hour = mCurrentHour.replace("时", "").toInt()
        val minute = mCurrentMinute.replace("分", "").toInt()
        
        if (year == mStartYear && month == mStartMonth && day == mStartDay && hour == mStartHour && minute == mStartMinute) {
            start = mStartSecond!!
            
            if ((mStartYear!!.toInt() == mEndYear!!.toInt()) && (mStartHour!!.toInt() == mEndHour!!.toInt()) && (mStartDay!!.toInt() == mEndDay!!.toInt())
                    && (mStartHour!!.toInt() == mEndHour!!.toInt()) && (mStartMinute!!.toInt() == mEndMinute!!.toInt())) {
                end = mEndSecond!!.toInt()
            } else {
                end = 59
            }
            
        } else if (year == mEndYear && month == mEndMonth && day == mEndDay && hour == mEndHour && minute == mEndMinute) {
            start = 0
            end = mEndSecond!!
        } else {
            start = 0
            end = 59
        }
        LogUtil.e(" 秒 ：start  :$start   end:$end")
        var value = ""
        for (index in start..end) {
            if (index < 10) {
                value = "0$index"
            } else {
                value = index.toString()
            }
            mOptionTimeList6.add(value + "秒")
        }
        options6.adapter = ArrayWheelAdapter(mOptionTimeList6 as List<String>)
        // 重新设置默认的角标
        options6.currentItem = 0
    }
    
    /**
     * 初始化WheelView的选项
     */
    private fun initWheelView() {
        
        // 设置数据
        options1.adapter = ArrayWheelAdapter(mOptionTimeList1 as List<String>)
        
        // 重新设置选中的年份
        options1.setOnItemSelectedListener { index ->
            val year = mOptionTimeList1[index]
            // 重新设置月份
            val replace = year.replace("年", "").toInt()
            if (replace != mStartYear) {
                mCurrentMonth = mDefaultMonth
                mCurrentDay = mDefaultDay
                mCurrentHour = mDefaultHour
                mCurrentMinute = mDefaultMinute
                mCurrentSecond = mDefaultSecond
            } else {
                mCurrentMonth = mStartMonth.toString()
                mCurrentDay = mStartDay.toString()
                mCurrentHour = mStartHour.toString()
                mCurrentMinute = mStartMinute.toString()
                mCurrentSecond = mStartSecond.toString()
            }
            
            // 设置年份的数据
            mCurrentYear = replace.toString()
            
            // 重新设置数据
            setMonthForYear()
            setDayForMonth()
            setHourForDay()
            setMinuteForHour()
            setSecondForMinute()
            
            if (mListener != null) {
                mListener!!.onSelector(mCurrentYear, mCurrentMonth, mCurrentDay, mCurrentHour, mCurrentMinute, mCurrentSecond)
            }
        }
        
        // 重新设置选中的月份
        if (mShowArray[1]) {
            options2.setOnItemSelectedListener { index ->
                val month = mOptionTimeList2[index]
                // 设置天数的数据
                val replace = month.replace("月", "").toInt()
                if (replace != mStartMonth) {
                    mCurrentDay = mDefaultDay
                    mCurrentHour = mDefaultHour
                    mCurrentMinute = mDefaultMinute
                    mCurrentSecond = mDefaultSecond
                } else {
                    mCurrentDay = mStartDay.toString()
                    mCurrentHour = mStartHour.toString()
                    mCurrentMinute = mStartMinute.toString()
                    mCurrentSecond = mStartSecond.toString()
                }
                
                // 设置月份
                mCurrentMonth = replace.toString()
                
                // 重新设置天数
                setDayForMonth()
                setHourForDay()
                setMinuteForHour()
                setSecondForMinute()
                
                if (mListener != null) {
                    mListener!!.onSelector(mCurrentYear, mCurrentMonth, mCurrentDay, mCurrentHour, mCurrentMinute, mCurrentSecond)
                }
            }
        }
        
        if (mShowArray[2]) {
            // 重新设置选中的天数
            options3.setOnItemSelectedListener { index ->
                val day = mOptionTimeList3[index]
                // 重新设置小时的数据
                val toInt = day.replace("日", "").toInt()
                if (toInt != mStartDay) {
                    mCurrentHour = mDefaultHour
                    mCurrentMinute = mDefaultMinute
                    mCurrentSecond = mDefaultSecond
                } else {
                    mCurrentHour = mStartHour.toString()
                    mCurrentMinute = mStartMinute.toString()
                    mCurrentSecond = mStartSecond.toString()
                }
                // 设置天数的数据
                mCurrentDay = toInt.toString()
                
                // 重新设置小时
                setHourForDay()
                setMinuteForHour()
                setSecondForMinute()
                
                if (mListener != null) {
                    mListener!!.onSelector(mCurrentYear, mCurrentMonth, mCurrentDay, mCurrentHour, mCurrentMinute, mCurrentSecond)
                }
            }
        }
        
        // 重新设置选中的小时
        if (mShowArray[3]) {
            options4.setOnItemSelectedListener { index ->
                val hour = mOptionTimeList4[index]
                // 重新设置分钟
                val toInt = hour.replace("时", "").toInt()
                if (toInt != mStartMinute) {
                    mCurrentMinute = mDefaultMinute
                    mCurrentSecond = mDefaultSecond
                } else {
                    mCurrentMinute = mStartMinute.toString()
                    mCurrentSecond = mStartSecond.toString()
                }
                
                // 重新设置小时
                mCurrentHour = toInt.toString()
                
                setMinuteForHour()
                setSecondForMinute()
                
                if (mListener != null) {
                    mListener!!.onSelector(mCurrentYear, mCurrentMonth, mCurrentDay, mCurrentHour, mCurrentMinute, mCurrentSecond)
                }
            }
        }
        
        // 重新设置选中的分钟
        if (mShowArray[4]) {
            options5.setOnItemSelectedListener { index ->
                val minute = mOptionTimeList5[index]
                // 重新设置秒值
                val toInt = minute.replace("分", "").toInt()
                if (toInt != mStartSecond) {
                    mCurrentSecond = mDefaultSecond
                } else {
                    mCurrentSecond = mStartSecond.toString()
                }
                
                // 重新设置分钟
                mCurrentMinute = toInt.toString()
                
                setSecondForMinute()
                if (mListener != null) {
                    mListener!!.onSelector(mCurrentYear, mCurrentMonth, mCurrentDay, mCurrentHour, mCurrentMinute, mCurrentSecond)
                }
            }
        }
        
        // 重新设置秒值
        if (mShowArray[5]) {
            options6.setOnItemSelectedListener { index ->
                val value = mOptionTimeList6[index]
                
                // 重新设置秒值
                mCurrentSecond = value.replace("分", "")
                
                if (mListener != null) {
                    mListener!!.onSelector(mCurrentYear, mCurrentMonth, mCurrentDay, mCurrentHour, mCurrentMinute, mCurrentSecond)
                }
            }
        }
    }
    
    /**
     * 获取当前的最大值
     */
    private fun getMaxDayForMonth(year: Int, month: Int): Int {
        val calendar: Calendar = GregorianCalendar(year, month, 0)
        return calendar.getActualMaximum(Calendar.DATE)
    }
    
    interface SelectorListener {
        fun onSelector(year: String, month: String, day: String, hour: String, minute: String, second: String)
    }
    
    fun setSelectorListener(listener: SelectorListener) {
        this.mListener = listener
    }
    
    fun setCancelTitleListener(listener: OnClickListener) {
        inflater.findViewById<TextView>(R.id.tv_cancel).setOnClickListener(listener)
    }
    
    fun setSaveTitleClickListener(@NotNull saveListener: OnClickListener) {
        inflater.findViewById<TextView>(R.id.tv_save).setOnClickListener(saveListener)
    }
}