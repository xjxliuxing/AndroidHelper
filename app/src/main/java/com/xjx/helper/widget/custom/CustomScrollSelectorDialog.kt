package com.xjx.helper.widget.custom

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter
import com.contrarywind.listener.OnItemSelectedListener
import com.contrarywind.view.WheelView
import com.xjx.helper.R
import com.xjx.helper.utils.LogUtil
import com.xjx.helper.utils.ScreenUtils
import java.util.*

/**
 * 自定义滑动选择器的dialog
 */
class CustomScrollSelectorDialog(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {
    
    private lateinit var mContext: Activity
    private lateinit var mContentLayout: View // 主布局
    private lateinit var mBottomLayout: View // 底部的布局
    private lateinit var mClickIds: IntArray // 点击选择的id数组
    private lateinit var mTextIds: IntArray // 显示内容的id数组
    private var mPosition = 0 // 当前点击选择view的角标
    private val mScreenHeight = ScreenUtils.getInstance().screenHeight
    private var isHideBottomLayout = false
    
    private val optionTimeList1: ArrayList<String> = ArrayList<String>()
    private val optionTimeList2: ArrayList<String> = ArrayList<String>()
    private var position1: String = "00"
    private var position2: String = "00"
    private var isShowing = false // view是否在展示
    
    init {
        // 60分钟
        for (j in 0..59) {
            if (j < 10) {
                optionTimeList2.add("0$j")
            } else {
                optionTimeList2.add(j.toString())
            }
        }
        
        // 24小时
        for (i in 0..23) {
            if (i < 10) {
                optionTimeList1.add("0$i")
            } else {
                optionTimeList1.add(i.toString())
            }
        }
    }
    
    public fun setCustomLayout(context: Activity, contentLayout: View, bottomLayout: View) {
        this.mContext = context
        this.mContentLayout = contentLayout
        this.mBottomLayout = bottomLayout
        // 添加view
        addView(mContentLayout)
        addView(mBottomLayout)
        
        // 重新设置view的布局
        setLayoutParams(mBottomLayout)
        setLayoutParams(mContentLayout)
        
        // 便利数组，设置点击事件
        if ((mClickIds.isNotEmpty()) && (mTextIds.isNotEmpty()) && (mClickIds.size == mTextIds.size)) {
            for (index in mClickIds.indices) {
                val clickId = mClickIds[index]
                val textId = mTextIds[index]
                
                val clickView = mContentLayout.findViewById<View>(clickId)
                val textView = mContentLayout.findViewById<View>(textId)
                
                // 设置tag
                clickView.tag = index
                textView.tag = index
                
                clickView.setOnClickListener {
                    this.mPosition = index
                    scrollBottom()
                }
            }
        }
        
        // 取消的按钮
        mContentLayout.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
            mContentLayout.post {
                val height = mContentLayout.height.toFloat()
                startAnimation(mContentLayout, 0f, height)
            }
            // 重新设置标记
            isHideBottomLayout = false
            isShowing = false
        }
        
        initTimeData()
    }
    
    public fun setIds(clickIds: IntArray, textIds: IntArray) {
        this.mClickIds = clickIds
        this.mTextIds = textIds
    }
    
    /**
     * 重新设置view的布局属性
     */
    private fun setLayoutParams(view: View) {
        val layoutParams = view.layoutParams as LayoutParams
        layoutParams.addRule(ALIGN_PARENT_BOTTOM)
        layoutParams.width = LayoutParams.MATCH_PARENT
        layoutParams.height = LayoutParams.WRAP_CONTENT
        view.layoutParams = layoutParams
        
        // 默认移动到最底部看不到的位置
        view.post {
            val height = view.height.toFloat()
            startAnimation(view, 0f, height, 1)
        }
    }
    
    /**
     * 向上滑动
     */
    private fun scrollTo() {
        startAnimation(mBottomLayout, 0f, mBottomLayout.height.toFloat())
        startAnimation(mContentLayout, mContentLayout.height.toFloat(), 0f)
    }
    
    /**
     * 向下滑动
     */
    private fun scrollBottom() {
        // 设置标记，重新执行底部的动画
        isHideBottomLayout = true
        // 内容布局动画过程由 ==> 当前所在的位置移动到 底部不可见的位置(自身view的高度)
        startAnimation(mContentLayout, 0f, mContentLayout.height.toFloat())
        // 底部的布局动画过程 ==> 当前的位置，移动到自身的高度位置
        startAnimation(mBottomLayout, mBottomLayout.translationY, 0f)
    }
    
    @SuppressLint("SetTextI18n")
    private fun initTimeData() {
        
        val wheelView1: WheelView = mBottomLayout.findViewById(R.id.options1)
        val wheelView2: WheelView = mBottomLayout.findViewById(R.id.options2)
        
        wheelView1.setCyclic(false)
        wheelView2.setCyclic(false)
        
        wheelView1.adapter = ArrayWheelAdapter<String>(optionTimeList1 as List<String>)
        wheelView2.adapter = ArrayWheelAdapter<String>(optionTimeList2 as List<String>)
        
        wheelView1.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                // 获取列表1中的数据
                position1 = optionTimeList1[index]
                // 重置列表2中的数据
                wheelView2.currentItem = 0
            }
        })
        
        // 获取列表2中的数据
        wheelView2.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(index: Int) {
                position2 = optionTimeList2[index]
            }
        })
        
        // 取消的按钮
        mBottomLayout.findViewById<View>(R.id.tv_cancle).setOnClickListener {
            scrollTo()
        }
        // 存储的按钮
        mBottomLayout.findViewById<View>(R.id.tv_save).setOnClickListener {
            
            // 设置数据
            val timeValue = "$position1:$position2"
            
            // 字段赋值
            for (item in mTextIds.indices) {
                val id = mTextIds[item]
                val contentView = mContentLayout.findViewById<TextView>(id)
                if (contentView.tag == this.mPosition) {
                    contentView.text = timeValue
                    break
                }
            }
            
            // todo   时间做对比
            
            scrollTo()
        }
    }
    
    /**
     * 展示布局
     */
    
    /**
     * 展示布局
     */
    public fun show() {
        if (!isShowing) {
            // 打开view
            
            // 底部的布局
            if (isHideBottomLayout) {
                startAnimation(mBottomLayout, 0f, mBottomLayout.height.toFloat(), 1)
            }
            // 中心的布局，从底部的位置，移动到自身停留的位置，做由下到上的动画
            startAnimation(mContentLayout, mContentLayout.height.toFloat(), 0f)
            
            isShowing = true
        } else {
            // 关闭view
            
            mContentLayout.post {
                if (mContentLayout.translationY == 0f) {
                    startAnimation(mContentLayout, 0f, mContentLayout.height.toFloat())
                }
            }
            
            mBottomLayout.post {
                if (mBottomLayout.translationY == 0f) {
                    startAnimation(mBottomLayout, 0f, mBottomLayout.height.toFloat())
                }
            }
            
            // 重新设置标记
            isHideBottomLayout = false
            isShowing = false
        }
    }
    
    /**
     * 开启动画
     * @param view      执行动画的view
     * @param startY    开始的位置
     * @param endY      结束的位置
     * @param time      动画的额时长，如果不写，默认就是500毫秒
     */
    
    /**
     * 开启动画
     * @param view      执行动画的view
     * @param startY    开始的位置
     * @param endY      结束的位置
     * @param time      动画的额时长，如果不写，默认就是500毫秒
     */
    private fun startAnimation(view: View, startY: Float, endY: Float, vararg time: Long) {
        val animation = ObjectAnimator.ofFloat(view, "translationY", startY, endY)
        if (time.isEmpty()) {
            animation.duration = 500
        } else {
            animation.duration = time[0]
        }
        animation.start()
        LogUtil.e("执行动画开始的位置：" + startY + "   执行动画结束的位置：" + endY)
    }
    
}