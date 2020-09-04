package com.xjx.helper.widget.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.xjx.helper.utils.LogUtil

class TestCustomView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    
    // 顶部滑动控件
    private var mTopLayout: ViewGroup? = null
    
    // 底部滑动控件
    private var mBottomLayout: ViewGroup? = null
    
    // 顶部固定控件
    private var mFixedLayout: View? = null
    
    private val mTopLayoutTop = 0
    private var mBottomLayoutTop = 0
    private var mBottomLayoutHeight = 0
    
    // 顶部控件的高度
    private var mTopLayoutHeight = 0
    private var mTopLayoutWidth = 0
    
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        LogUtil.e("----------> onLayout")
    
    
        mTopLayout!!.layout(0, 0, mTopLayoutWidth, mTopLayoutHeight)
        mBottomLayout!!.layout(0, mTopLayoutHeight, mBottomLayoutTop, mTopLayoutHeight + mBottomLayoutHeight)
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    
        LogUtil.e("---------->   onMeasure")
    
        mTopLayoutWidth = mTopLayout!!.measuredWidth
        mTopLayoutHeight = mTopLayout!!.measuredHeight
        mBottomLayoutTop = mBottomLayout!!.measuredWidth
        mBottomLayoutHeight = mBottomLayout!!.measuredHeight
    }
    
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        LogUtil.e("---------->   onDraw")
    
    }
    
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        LogUtil.e("---------->   onSizeChanged")
    
    }
    
    override fun onFinishInflate() {
        super.onFinishInflate()
        
        LogUtil.e("---------->   onFinishInflate")
        
        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i) as ViewGroup
            val tag = child.tag as String
            if ("fixed" == tag) {
                mFixedLayout = child
            } else if ("top" == tag) {
                mTopLayout = child
            } else if ("bottom" == tag) {
                mBottomLayout = child
            }
        }
    }
    
}