package com.xjx.helper.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntRange
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
    
    // 文字的画笔
    private val mPaintText: Paint = Paint()
    
    // 背景的高度
    private val mPaintBackgroundHeight = ConvertUtil.toDp(7f)
    
    // 背景的边距
    private val mPaintBackgroundPadding = ConvertUtil.toDp(44f)
    private var mTextContext: String = "0"
    private val mBitmapWidth = resources.getDimension(R.dimen.dp_17)
    private val mBitmapHeight = resources.getDimension(R.dimen.dp_22)
    private var mProgress: Int = 20
    private var mEndX: Float = 0f
    
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
        
        // 设置文字
        mPaintText.color = ContextCompat.getColor(getContext(), R.color.white)
        mPaintText.textSize = resources.getDimension(R.dimen.size_20sp)
        mPaintText.isAntiAlias = true
        
        if (!isInEditMode) {
            //得到AssetManager
            val assets = context.assets
            val fromAsset = Typeface.createFromAsset(assets, "DINCondensedBold.ttf")
            mPaintText.typeface = fromAsset
        }
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//
//        val width = resolveSize(View.MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec)
//        val height = resolveSize(View.MeasureSpec.getSize(heightMeasureSpec), heightMeasureSpec)
//        setMeasuredDimension(width, height)
//    }
    
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        
        canvas?.let {
            
            /***************************** 绘制背景框 ****************************************/
            
            // 求出中线的位置
            val centerLine = (measuredHeight / 2).toFloat()
            // 开始X坐标等于 左侧预留的padding的边距
            val startX: Float = 0F + mPaintBackgroundPadding
            // 开始Y坐标等于 中线 + 宽度的一半
            val startY: Float = centerLine
            // 结束的X轴坐标
            val endX = measuredWidth - mPaintBackgroundPadding;
            // 结束Y坐标等于 开始Y坐标的值
            val endY: Float = startY
            it.drawLine(startX, startY, endX, endY, mPaintBackground)
            
            /***************************** 设置进度条 ****************************************/
            // 计算出百分比的值
            if (mProgress >= 100) {
                mProgress = 100
            }
            val percentage: Float = (mProgress.toFloat() / 100)
            // 当前百分比所占据的宽度值
            val value = (percentage * (measuredWidth - mPaintBackgroundPadding))
            // 结束X坐标等于 view的总宽度减去右侧预留的padding
            if (value <= startX) {
                mEndX = startX
            } else {
                mEndX = value
            }
            
            // 设置渐变
            val shaderStartX: Float = 0f + mPaintBackgroundPadding
            val shaderEndX: Float = measuredWidth - mPaintBackgroundPadding
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
            
            // bitmap的左侧值
            it.drawLine(startX, startY, mEndX, endY, mPaintProgress)
            
            // 绘制bitmap
            val dLeft = (mEndX - (mBitmapWidth / 3)).toInt()
            val dTop = (centerLine - (mBitmapHeight / 2)).toInt()
            val dRight = (mEndX + (mBitmapWidth - (mBitmapWidth / 3))).toInt()
            val dBottom = (centerLine + (mBitmapHeight / 2)).toInt()
            
            // view裁剪的大小
            val src = Rect(0, 0, mBitmapWidth.toInt(), mBitmapHeight.toInt())
            // 目标view显示的区域
            val dst = Rect(dLeft, dTop, dRight, dBottom)
            
            canvas.drawBitmap(bitmap!!, src, dst, null)
            
            // 绘制进度文字
            val rect = Rect()
            
            mTextContext = "$mProgress%"
            mPaintText.getTextBounds(mTextContext, 0, mTextContext.length, rect)
            val width = rect.width()
            val height = rect.height()
            
            // 这里取文字宽度的4分之一，是为了让圆角看着舒服点
            canvas.drawText(mTextContext, mEndX - (width / 4), dTop.toFloat(), mPaintText)
        }
    }
    
    // 获取bitmap
    val bitmap: Bitmap? by lazy {
        // 获取进度的图片
        val icon = ContextCompat.getDrawable(context, R.mipmap.icon_c62_progress_image)
        if (icon is BitmapDrawable) {
            return@lazy icon.bitmap
        } else {
            return@lazy null
        }
    }
    
    /**
     * 设置当前的进度
     */
    public fun setProgress(@IntRange(from = 0, to = 100) progress: Int) {
        mProgress = progress
        invalidate()
    }
    
}