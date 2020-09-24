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
    
    private val mPaintBackgroundText = Paint()// 背景上的文字
    
    private var mDrawable: Drawable? = null
    private var mDrawableHeight: Float = 0f
    
    private var mBackgroundColor: Int = 0
    private var mBackgroundColorHeight: Float = 0f
    private var mSelectorBackgroundColor: Int = 0
    private var bitmap: Bitmap? = null
    private var mMaxScrollValue: Int = 0 // 最大的滑动值
    private val mRadius = ConvertUtil.toDp(8f)
    private val mPaddingRight = ConvertUtil.toDp(18f)
    private val mTextContent = "滑动至底部即可开始"
    private var mLeft: Int = 0
    private var dxValue: Float = 0f
    
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
        
        
        bitmap = CustomViewUtil.getBitmapScaleWidth(mDrawable, mDrawableHeight)
        
        // 设置背景的数据
        mPaintBackground.isAntiAlias = true
        mPaintBackground.isDither = true
        mPaintBackground.color = mBackgroundColor
        mPaintBackground.strokeWidth = mBackgroundColorHeight
        
        // 背景文字
        mPaintBackgroundText.isAntiAlias = true
        mPaintBackgroundText.isDither = true
        mPaintBackgroundText.color = context?.let { ContextCompat.getColor(it, R.color.gray_4) }!!
        mPaintBackgroundText.textSize = ConvertUtil.toSp(12f)
        
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
        bitmap?.let {
            setMeasuredDimension(resolveSize(MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec), it.height)
            
            if (!it.isRecycled) {
                mMaxScrollValue = (measuredWidth - bitmap!!.width)
            }
        }
        
    }
    
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        
        canvas?.let {
            
            // 绘制背景色
            it.drawRoundRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), mRadius, mRadius, mPaintBackground)
            
            // 绘制背景的文字
            val textArray = CustomViewUtil.getTextSize(mPaintBackgroundText, mTextContent)
            
            val lineX = measuredWidth - mPaddingRight - textArray[0]
            val lineY = measuredHeight / 2 + CustomViewUtil.getBaseline(mPaintBackgroundText)
            it.drawText(mTextContent, lineX, lineY, mPaintBackgroundText)
            
            // 绘制bitmap
            bitmap?.let { b ->
                if (b.isRecycled) {
                    bitmap = CustomViewUtil.getBitmapDefault(mDrawable)
                }
                
                // 绘制滑动过的颜色
                it.drawRoundRect(0f, 0f, (b.width + paddingLeft + mLeft).toFloat(), measuredHeight.toFloat(), mRadius, mRadius, mPaintSelectorBackground)
                
                val rectSrc = Rect(0, 0, b.width, b.height)
                val top = (measuredHeight - b.height) / 2
                val desRect = Rect((paddingLeft + mLeft), top, (b.width + paddingLeft + mLeft), top + b.height)
                it.drawBitmap(b, rectSrc, desRect, null)
                
            }
        }
    }
    
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        
        LogUtil.e("measuredWidth:$measuredWidth")
        
        var startX = 0f
        var startY = 0f
        
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
                    dxValue = endX - startX
                    if (dxValue <= 0) {
                        dxValue = 0f
                    }
                    if (dxValue >= mMaxScrollValue) {
                        dxValue = mMaxScrollValue.toFloat()
                    }
        
                    mLeft = dxValue.toInt()
                    invalidate()
        
                    LogUtil.e("dx:$dxValue")
                    return (endY - startY) == 0f
                }
    
                MotionEvent.ACTION_UP -> {
                    LogUtil.e("抬起 1")
                    startX = 0f
                    startY = 0f
        
                    LogUtil.e("dx:$dxValue  value:$mMaxScrollValue")
                    if (dxValue < mMaxScrollValue) {
                        if (mListener != null) {
                            mListener?.onChangeValue()
                        }
            
                        mLeft = 0
                        dxValue = 0f
                        invalidate()
            
                    } else {
                        if (mListener != null) {
                            mListener?.onScrollMaxValue()
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    
    interface ScrollChangeListener {
        fun onScrollMaxValue()
        fun onChangeValue()
    }
    
    private var mListener: ScrollChangeListener? = null
    public fun setChangeListener(listener: ScrollChangeListener) {
        this.mListener = listener
    }
}