package com.xjx.helper.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import com.xjx.helper.R
import com.xjx.helper.utils.ConvertUtil
import com.xjx.helper.utils.LogUtil

/**
 * 进度条
 */
public class ProgressView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaintDrawableLine: Paint = Paint()

    /**
     * 进度条的高度
     */
    private val mLineHeight = ConvertUtil.toDp(8f)

    /**
     * view左右的边距
     */
    private val mPadding = ConvertUtil.toDp(20f)

    /**
     * view的中心，可以作为上边距使用
     */
    private var mTop = 0f

    /**
     * 渐变色
     */
    private val mColorBlue: Int by lazy { ContextCompat.getColor(context!!, R.color.blue_2) }
    private val mColorGreen: Int by lazy { ContextCompat.getColor(context!!, R.color.green_2) }

    /**
     * View的右侧坐标
     */
    private var mRight: Float = 0f

    /**
     * 当前的进度
     */
    private var mProgress: Float = 70f

//    constructor( @androidx.annotation.Nullable attributes: AttributeSet)  {
//        super(get,attributes)
//        initView(attributes)
//    }

    private fun initView(attributes: AttributeSet) {
        mPaintDrawableLine.color = resources.getColor(R.color.green_2)
        mPaintDrawableLine.strokeWidth = mLineHeight
        mPaintDrawableLine.style = Paint.Style.FILL
        // 设置线段连接处样式  Join.MITER（结合处为锐角）Join.Round(结合处为圆弧) Join.BEVEL(结合处为直线)
        mPaintDrawableLine.strokeCap = Paint.Cap.ROUND // 设置圆角
        mPaintDrawableLine.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (measuredHeight > 0) {
            mTop = (measuredHeight / 2).toFloat()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }

        // 设置画笔遮罩滤镜  ,传入度数和样式
//        mPaintDrawableLine.maskFilter = BlurMaskFilter(dp12.toFloat(), BlurMaskFilter.Blur.SOLID)
//        canvas?.drawRect(Rect(dp16, dp12, (measuredWidth - dp16), dp7 + dp12), mPaint1)

        //获取view右侧的坐标
        mRight = (mPadding + (((mProgress / 100)) * measuredWidth))

        // 绘制渐变
        val linearGradient = LinearGradient(mPadding, mTop, (measuredWidth - mPadding), mTop, mColorBlue, mColorGreen, Shader.TileMode.CLAMP)
        mPaintDrawableLine.shader = linearGradient

        // 绘制主View
        canvas.drawLine(mPadding, mTop, mRight, mTop, mPaintDrawableLine)

        // 绘制闪电的标记
        val bitmap = getBitmapForResource()
        if (bitmap != null && (!bitmap.isRecycled)) {
            // view的区域，最好就是view自己本身的大小，或者是指定的大小
            val src = Rect(0, 0, (bitmap.width), (bitmap.height))
            // 绘制的区域，实际上就是在屏幕上view的位置

            // drawable的高度为view中心 减去 Line一半的高度
            val drawableTop = (mTop - ((bitmap.height) / 2)).toInt()
            // 左侧的坐标
            val mLeft = mRight.toInt()
            // view在屏幕上的位置
            val dst = Rect(mLeft, drawableTop, (mLeft + (bitmap.width)), (drawableTop + (bitmap.height)))
            canvas.drawBitmap(bitmap, src, dst, mPaintDrawableLine)
        }
    }

    /**
     * 获取指定的bitmap
     */
    private fun getBitmapForResource(): Bitmap? {
        val bitmap: Bitmap? by lazy {
            val drawable = ContextCompat.getDrawable(context, R.mipmap.icon_progress_image)
            if (drawable != null && drawable is BitmapDrawable) {
                LogUtil.e("只会执行一次获取view的方法--->Bitmap")
                return@lazy drawable.bitmap
            } else {
                LogUtil.e("只会执行一次获取view的方法--->null")
                return@lazy null
            }
        }
        return bitmap
    }

    /**
     * 设置当前的进度
     */
    @Keep
    fun setProgress(@FloatRange(from = 0.0, to = 100.0) progress: Float) {
        mProgress = progress
        invalidate()
    }
}

