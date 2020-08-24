package com.xjx.helper.ui.home.activity.todo.animation

import android.animation.ObjectAnimator
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.LogUtil
import kotlinx.android.synthetic.main.activity_translation_x.*

class TranslationXActivity : CommonBaseTitleActivity() {
    
    override fun getTitleLayout(): Int {
        return R.layout.activity_translation_x
    }
    
    override fun initView() {
        super.initView()
        setTitleContent("测试平移动画")
        SwitchLoadingStatus(PlaceholderStatus.NONE)
    }
    
    override fun initData() {
        super.initData()
        
        button.setOnClickListener { v ->
            
            val ints1 = IntArray(2)
            button.getLocationOnScreen(ints1)
            val startX = (ints1[0]).toFloat()
            
            val ints2 = IntArray(2)
            button2.getLocationOnScreen(ints2)
            val endX = (ints2[0]).toFloat()
            
            startAnimation(floatArrayOf(0f, endX - startX))
            
        }
        
        button2.setOnClickListener { v ->

//            val ints1 = IntArray(2)
//            button2.getLocationOnScreen(ints1)
//            val startX = ints1[0].toFloat()
            val startX = views.translationX
            
            
            startAnimation(floatArrayOf(startX, 0f))
        }
    }
    
    fun startAnimation(arry: FloatArray) {
        
        val animation = ObjectAnimator.ofFloat(views, "translationX", arry[0], arry[1])
        LogUtil.e("array:" + arry[0] + "       " + arry[1])
        animation.duration = 1000
        animation.start()
    }
    
}