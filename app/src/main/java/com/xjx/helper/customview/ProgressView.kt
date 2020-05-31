package com.xjx.helper.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.xjx.helper.R
import com.xjx.helper.utils.ConvertUtil

/**
 * 进度条
 */
class ProgressView constructor(val content: Context) : View(content) {

    private val mPaint1: Paint = Paint()
    val dp7: Int = ConvertUtil.toDp(10f).toInt()
    val dp16: Int = ConvertUtil.toDp(16f).toInt()

    constructor(content: Context, @androidx.annotation.Nullable attributes: AttributeSet) : this(content) {
        initView(attributes)
    }

    private fun initView(attributes: AttributeSet) {
        mPaint1.color = resources.getColor(R.color.green_2)
        mPaint1.strokeWidth = ConvertUtil.toDp(200f)
        mPaint1.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 绘制渐变
        val linearGradient2 = LinearGradient(0f, 0f, measuredWidth.toFloat(), 0f,
                intArrayOf(resources.getColor(R.color.green_2), resources.getColor(R.color.blue_2)),
                floatArrayOf(0.5f, 1f), Shader.TileMode.CLAMP
        )
        mPaint1.shader = linearGradient2
        canvas?.drawRect(Rect(0, 0, measuredWidth, dp7), mPaint1)

        // 绘制模糊

        // 绘制图片

    }

}