package com.xjx.helper.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.xjx.helper.R
import com.xjx.helper.utils.ConvertUtil
import com.xjx.helper.utils.DeviceUtil
import com.xjx.helper.utils.LogUtil

/**
 * 进度条
 */
class ProgressView constructor(val content: Context) : View(content) {

    private val mPaint1: Paint = Paint()
    val dp7: Int = ConvertUtil.toDp(10f).toInt()
    val dp12: Int = ConvertUtil.toDp(12f).toInt()
    val dp16: Int = ConvertUtil.toDp(16f).toInt()
    val dp17: Int = ConvertUtil.toDp(17f).toInt()
    val dp22: Int = ConvertUtil.toDp(22f).toInt()

    private var mBitmap: Bitmap? = null

    constructor(content: Context, @androidx.annotation.Nullable attributes: AttributeSet) : this(content) {
        initView(attributes)
    }

    private fun initView(attributes: AttributeSet) {
        mPaint1.color = resources.getColor(R.color.green_2)
        mPaint1.strokeWidth = ConvertUtil.toDp(200f)
        mPaint1.style = Paint.Style.FILL

        val deviceUtil = DeviceUtil()
        deviceUtil.getDeviceId(context)

        val saveDeviceId = deviceUtil.saveDeviceId
        LogUtil.e("saveDeviceId:$saveDeviceId")
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

        // 设置画笔遮罩滤镜  ,传入度数和样式
        mPaint1.maskFilter = BlurMaskFilter(dp12.toFloat(), BlurMaskFilter.Blur.SOLID)

//        canvas?.drawRect(Rect(dp16, dp12, (measuredWidth - dp16), dp7 + dp12), mPaint1)

        // 绘制图片
        if ((mBitmap == null) || (mBitmap!!.isRecycled)) {
            mBitmap = getBitmapForResource();
        }

        if (mBitmap != null) {
            canvas?.drawBitmap(mBitmap!!, Rect(0, 0, mBitmap!!.width, mBitmap!!.height), Rect(0, 0, mBitmap!!.width, mBitmap!!.height), null)
        }
    }

    private fun getBitmapForResource(): Bitmap? {
        val bitmap1: Bitmap? by lazy {
            val drawable = ContextCompat.getDrawable(context, R.mipmap.icon_progress_image)
            if (drawable != null && drawable is BitmapDrawable) {
                return@lazy drawable.bitmap
            } else {
                return@lazy null
            }
        }
        return bitmap1;
    }
}