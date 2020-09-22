package com.xjx.helper.widget.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.xjx.helper.R
import com.xjx.helper.utils.ConvertUtil
import com.xjx.helper.utils.CustomViewUtil
import com.xjx.helper.utils.LogUtil
import com.xjx.helper.utils.ToastUtil

/**
 * 触摸解锁的效果
 */
class CustomTouchUnlockView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    
    lateinit var bitmap: Bitmap
    private val mBottomValue: Float = ConvertUtil.toDp(100f)
    private var mBitmapWidth = 0;
    private var mBitmapHeight = 0;
    
    private var mLeft = 0
    private var mRight = 0
    private var mTop = 0
    private var mBottom = 0
    private val waitTime: Long = 1000 // 长按的等待时间
    private val waitCode: Int = 199716 // 长按的code
    
    private val mPaintCenterFuzzy = Paint() // 中心模糊的按钮
    var centerX: Float = 0f  // view的X轴中心
    var centerY: Float = 0f  // view的Y轴中心
    var radius: Float = 0f   // 绘制的半径
    var oldMax: Float = 0f
    
    // 绘制中心扩散的园
    private val mPaintSpread = Paint()
    private var maxRadius = 0f // 最大的半径值
    private val mRadiusPadding = ConvertUtil.toDp(30f) // 边距最大的值
    
    // 圆圈的集合，看似是半径，其实也是用来控制view个数的数组
    private val mRadiusList: ArrayList<Int> = arrayListOf()
    
    // 透明的数组
    private val mAlphas: ArrayList<Int> = arrayListOf()
    var isWave = false      // 控制是否轮询重绘
    var isRunning = false    // 是否在扩散中
    val intervalWidth = ConvertUtil.toDp(20f) // 每个view间隔的宽度
    
    init {
        context?.let {
            // 绘制bitmap
            bitmap = CustomViewUtil.getBitmapDefault(ContextCompat.getDrawable(it, R.mipmap.ic_launcher_round))
            mBitmapWidth = bitmap.width
            mBitmapHeight = bitmap.height
            
            // 绘制中心的模糊圆形
//            mPaintCenterFuzzy.color = ContextCompat.getColor(context, R.color.white)
//            mPaintCenterFuzzy.isDither = true
//            mPaintCenterFuzzy.isAntiAlias = true
            
            // 扩散圆的画笔
            mPaintSpread.color = ContextCompat.getColor(context, R.color.white)
            mPaintSpread.isDither = true
            mPaintSpread.isAntiAlias = true
            mPaintSpread.style = Paint.Style.STROKE
            mPaintSpread.strokeWidth = 30f
            
            // 添加默认的数据
            mRadiusList.add(0)
            mAlphas.add(255)
        }
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
                resolveSize(View.MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec),
                resolveSize(View.MeasureSpec.getSize(heightMeasureSpec), heightMeasureSpec)
        )
    }
    
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        
        if (bitmap.isRecycled) {
            bitmap = CustomViewUtil.getBitmapDefault(ContextCompat.getDrawable(context, R.mipmap.ic_launcher_round))
            mBitmapWidth = bitmap.getScaledWidth(2)
            mBitmapHeight = bitmap.getScaledWidth(2)
        }
        
        mLeft = (measuredWidth - mBitmapWidth) / 2
        mRight = mLeft + mBitmapWidth
        mTop = (measuredHeight - mBitmapHeight - mBottomValue).toInt()
        mBottom = (measuredHeight - mBottomValue).toInt()
        
        // 中心的模糊效果
        centerX = (mLeft + (mBitmapWidth / 2)).toFloat() // 圆形X轴的中心位置
        centerY = (mTop + (mBitmapHeight / 2)).toFloat() // 圆形Y轴的中心位置
        radius = Math.max(mBitmapWidth / 2, mBitmapHeight / 2).toFloat()   // 圆形最大的半径
        oldMax = radius
        setLayerType(LAYER_TYPE_SOFTWARE, mPaintCenterFuzzy)
        
        // 最大的半径
        maxRadius = (measuredHeight / 2) - mRadiusPadding
        
    }
    
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        
        if (bitmap.isRecycled) {
            bitmap = CustomViewUtil.getBitmapDefault(ContextCompat.getDrawable(context, R.mipmap.ic_launcher_round))
            mBitmapWidth = bitmap.getScaledWidth(2)
            mBitmapHeight = bitmap.getScaledWidth(2)
        }
        
        canvas?.let { c ->
            // 绘制中心的图片
            val srcRect = Rect(0, 0, mBitmapWidth, mBitmapHeight)
            val desRect = Rect(mLeft, mTop, mRight, mBottom)
            c.drawBitmap(bitmap, srcRect, desRect, null)
            
            // 绘制中心模糊
            // c.drawCircle(centerX, centerY, max, mPaintCenterFuzzy)
            
            for (index in 0 until mAlphas.size) {
                val alphas = mAlphas[index]
                mPaintSpread.alpha = alphas
                
                // 获取存储半径的值
                val width = mRadiusList[index]
                // 开始绘制圆形
                c.drawCircle(centerX, centerY, radius + width, mPaintSpread)
                
                // 每次自增1，用来增加动态增加view的宽度
                if (width < maxRadius) {
                    mRadiusList[index] = (width + 5)
                } else {
                    mRadiusList[index] = 0
                }
                if (alphas > 0) {
                    mAlphas[index] = if (alphas - 5 > 0) alphas - 5 else 1
                } else {
                    mAlphas[index] = 255
                }
            }
            
            // 动态增加view
            if (mRadiusList.size <= 10) {
                if (mRadiusList[mRadiusList.size - 1] > intervalWidth) {
                    mRadiusList.add(0)
                    mAlphas.add(255)
                }
            } else {
                mRadiusList.remove(0)
                mAlphas.remove(0)
            }
            
            //延迟更新，达到扩散视觉差效果
            if (isWave) {
//                postInvalidateDelayed(20)
                invalidate()
            }
        }
    }
    
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                // 按下和移动的动作
                MotionEvent.ACTION_DOWN -> {
                    val x = it.x
                    val y = it.y
                    if ((x >= mLeft) && (x <= mRight) && (y >= mTop) && (y <= mBottom)) {
                        // 在这里需要去触动水波纹效果
                        LogUtil.e("开始发送消息 ...")
                        // 发送一个延迟的消息
                        mHandler.sendEmptyMessageDelayed(waitCode, waitTime)
                        return true
                    } else {
                        return false
                    }
                }
                // 抬起的动作
                MotionEvent.ACTION_UP -> {
                    mHandler.removeMessages(waitCode)
                    mHandler.removeCallbacksAndMessages(null)
                    LogUtil.e("移除了消息的发送")
//                    radius = oldMax
                    invalidate()
                    return false
                }
                else -> {
                    return false
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    
    fun startView(mIsWave: Boolean) {
        this.isWave = mIsWave
        mHandler.removeMessages(waitCode)
        mHandler.removeCallbacksAndMessages(null)
        
        invalidate()
    }
    
    fun endView() {
        mHandler.removeMessages(waitCode)
        mHandler.removeCallbacksAndMessages(null)
        this.isWave = false
        invalidate()
        isRunning = false
    }
    
    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            msg?.let {
                if (it.what == waitCode) {
                    ToastUtil.showToast("开始了长按的功能！")
//                    LogUtil.e("我正在长按view ----> ")
                    
                    if (radius <= maxRadius) {
                        ++radius
                        invalidate()
//                        sendEmptyMessage(waitCode)
                    }
                }
            }
        }
    }
    
}