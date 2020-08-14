package com.xjx.helper.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.xjx.helper.R
import com.xjx.helper.utils.ConvertUtil

/**
 * 自定义进度条
 */
class ProgressView2(context: Context, attrs: AttributeSet) : View(context, attrs) {
    
    // 背景的画笔
    private val mPaintBackground: Paint = Paint()
    
    // 进度条的画笔
    private val mPaintProgress: Paint = Paint()
    
    // 背景的高度
    private val mPaintBackgroundHeight = ConvertUtil.toDp(8f)
    
    // 背景的边距
    private val mPaintBackgroundPadding = ConvertUtil.toDp(44f)
    
    init {
        // 设置背景
        mPaintBackground.color = ContextCompat.getColor(context, R.color.black_14)
        mPaintBackground.strokeWidth = mPaintBackgroundHeight
        mPaintBackground.isAntiAlias = true
        mPaintBackground.strokeCap = Paint.Cap.ROUND
        
        // 设置进度条
        mPaintProgress.strokeWidth = mPaintBackgroundHeight
        mPaintProgress.strokeCap = Paint.Cap.ROUND
        mPaintProgress.isAntiAlias = true
        
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        
        val width = resolveSize(MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec)
        val height = resolveSize(MeasureSpec.getSize(heightMeasureSpec), heightMeasureSpec)
        setMeasuredDimension(width, height)
    }
    
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            
            /**
             *                      设置背景框
             */
            // 求出中线的位置
            val centerLine = (measuredHeight / 2).toFloat()
            // 开始X坐标等于 左侧预留的padding的边距
            val startX: Float = 0F + mPaintBackgroundPadding
            // 开始Y坐标等于 中线 + 宽度的一半
            val startY: Float = centerLine
            
            // 结束X坐标等于 view的总宽度减去右侧预留的padding
            val endX: Float = measuredWidth - mPaintBackgroundPadding
            // 结束Y坐标等于 开始Y坐标的值
            val endY: Float = startY
            
            it.drawLine(startX, startY, endX, endY, mPaintBackground)
            
            /**
             *                     设置进度条
             */
            
            // 设置渐变
            val shaderStartX: Float = 0f + mPaintBackgroundPadding
            val shaderEndX: Float = measuredWidth - mPaintBackgroundPadding - 400
            val shader = LinearGradient(
                    shaderStartX,
                    centerLine,
                    shaderEndX,
                    centerLine,
                    intArrayOf(ContextCompat.getColor(context, R.color.blue_3), ContextCompat.getColor(context, R.color.blue_4)),
                    floatArrayOf(0f, 1f),
                    Shader.TileMode.CLAMP
            )
            mPaintProgress.shader = shader
            
            it.drawLine(startX, startY, endX, endY, mPaintProgress)
            
        }
        
    }
    
}