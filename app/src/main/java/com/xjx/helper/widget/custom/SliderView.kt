package com.xjx.helper.widget.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.xjx.helper.R
import com.xjx.helper.utils.ConvertUtil
import com.xjx.helper.utils.CustomViewUtil
import com.xjx.helper.utils.LogUtil

/**
 * 自定义滑块 ,这里使用seekBar的控件
 */
class SliderView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    
    private val mPaintBackground = Paint() // 背景色
    private val mPaintSelectorBackground = Paint() // 滑动过的颜色
    
    private var mDrawable: Drawable? = null
    private var mDrawableHeight: Float = 0f
    
    private var mBackgroundColor: Int = 0
    private var mBackgroundColorHeight: Float = 0f
    private var mSelectorBackgroundColor: Int = 0
    private var bitmap: Bitmap? = null
    private var mMaxScrollValue: Int = 0 // 最大的滑动值
    
    init {
        val toDp5 = ConvertUtil.toDp(5f)
        
        // 获取属性
        val array = context?.obtainStyledAttributes(attrs, R.styleable.SliderView)
        array?.let {
            // 滑块的图片
            mDrawable = it.getDrawable(R.styleable.SliderView_hk_drawable)
            mDrawableHeight = it.getDimension(R.styleable.SliderView_hk_drawable_Height, toDp5)
            // 背景的颜色
            mBackgroundColor = it.getColor(R.styleable.SliderView_hk_background, ContextCompat.getColor(context, R.color.white_1))
            mBackgroundColorHeight = it.getDimension(R.styleable.SliderView_hk_background_Height, toDp5)
            // 划过的颜色
            mSelectorBackgroundColor = it.getColor(R.styleable.SliderView_hk_selector_background, ContextCompat.getColor(context, R.color.white_1))
        }
        
        
        bitmap = CustomViewUtil.getBitmapDefault(mDrawable)
        
        // 设置背景的数据
        mPaintBackground.isAntiAlias = true
        mPaintBackground.isDither = true
        mPaintBackground.color = mBackgroundColor
        mPaintBackground.strokeWidth = mBackgroundColorHeight
        
        // 设置背景划过的颜色
        mPaintSelectorBackground.isAntiAlias = true
        mPaintSelectorBackground.isDither = true
        mPaintSelectorBackground.color = mSelectorBackgroundColor
        bitmap?.let {
            mPaintSelectorBackground.strokeWidth = it.height.toFloat()
        }
        // 释放对象
        array?.recycle()
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        
        setMeasuredDimension(
                resolveSize(MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec),
//                resolveSize(MeasureSpec.getSize(heightMeasureSpec), heightMeasureSpec)
                400
        )
        if (bitmap != null || (!bitmap!!.isRecycled)) {
            mMaxScrollValue = (measuredWidth - bitmap!!.width).toInt()
        }
    }
    
    private var mLeft: Int = 0
    
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        
        canvas?.let {
            
            // 绘制背景色
            val backgroundTop = (measuredHeight) / 2.toFloat()
            it.drawLine(0f, backgroundTop, measuredWidth.toFloat(), backgroundTop, mPaintBackground)
            
            // 绘制滑动过的颜色
            it.drawLine(0f, backgroundTop, mLeft.toFloat(), backgroundTop, mPaintSelectorBackground)
            
            // 绘制bitmap
            bitmap?.let { b ->
                if (b.isRecycled) {
                    bitmap = CustomViewUtil.getBitmapDefault(mDrawable)
                }
                val rectSrc = Rect(0, 0, b.width, b.height)
                val top = (measuredHeight - b.height) / 2
                val desRect = Rect((paddingLeft + mLeft), top, (b.width + paddingLeft + mLeft), top + b.height)
                it.drawBitmap(b, rectSrc, desRect, null)
            }
        }
    }
    
    var dx: Float = 0f
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        
        LogUtil.e("measuredWidth:" + measuredWidth)
        
        var startX: Float = 0f
        var startY: Float = 0f
        
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    LogUtil.e("开始 1")
                    startX = it.x
                    startY = it.y
                    return true
                }
    
                MotionEvent.ACTION_MOVE -> {
                    LogUtil.e("移动 1")
                    val endX = it.x
                    val endY = it.y
                    dx = endX - startX
                    if (dx <= 0) {
                        dx = 0f
                    }
                    if (dx >= mMaxScrollValue) {
                        dx = mMaxScrollValue.toFloat()
                    }
        
                    mLeft = dx.toInt()
                    invalidate()
        
                    LogUtil.e("dx:$dx")
                    return (endY - startY) == 0f
                }
    
                MotionEvent.ACTION_UP -> {
                    LogUtil.e("抬起 1")
                    startX = 0f
                    startY = 0f
        
                    LogUtil.e("dx:" + dx + "  value:" + mMaxScrollValue)
                    if (dx < mMaxScrollValue) {
                        mLeft = 0
                        dx = 0f
                        invalidate()
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    
    /**
     * 计算比例
     */
    fun calculatePercentage() {
    }
    
}