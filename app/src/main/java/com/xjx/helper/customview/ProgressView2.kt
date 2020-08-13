package com.xjx.helper.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.xjx.helper.utils.ConvertUtil

/**
 * 自定义进度条
 */
class ProgressView2(context: Context, attrs: AttributeSet) : View(context, attrs) {
    
    private var mMeasureHeight: Float = ConvertUtil.toDp(33f)
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        
        setMeasuredDimension(resolveSize(MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec), mMeasureHeight.toInt())
    }
    
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        
    }
    
}