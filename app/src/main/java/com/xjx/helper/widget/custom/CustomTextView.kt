package com.xjx.helper.widget.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.xjx.helper.R

class CustomTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    
    private var mPaintText: Paint = Paint()
    private var mText: String = ""
    private var mMeasureSpecWidth: Int = 0
    private var mMeasureSpecHeight: Int = 0
    
    init {
        // 获取属性
        @SuppressLint("CustomViewStyleable")
        val array = context!!.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
        val color = array.getColor(R.styleable.CustomTextView_ctv_textColor, ContextCompat.getColor(getContext(), R.color.black_4))
        val dimension = array.getDimension(R.styleable.CustomTextView_ctv_textSize, 16f)
        val font = array.getString(R.styleable.CustomTextView_ctv_font)
        mText = array.getString(R.styleable.CustomTextView_ctv_text)
        
        mPaintText.color = color
        mPaintText.textSize = dimension
        mPaintText.isAntiAlias = true
        
        // 设置默认的宽高
        if (!TextUtils.isEmpty(mText)) {
            val rect = Rect()
            mPaintText.getTextBounds(mText, 0, mText.length, rect)
            mMeasureSpecWidth = rect.width()
            mMeasureSpecHeight = rect.height()
        }
        
        if (!isInEditMode) {
            if (!TextUtils.isEmpty(font)) {
                //得到AssetManager
                val assets = context.assets
                val fromAsset = Typeface.createFromAsset(assets, font)
                mPaintText.typeface = fromAsset
            }
        }
        
        // 释放对象
        array.recycle()
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        
        setMeasuredDimension(mMeasureSpecWidth, mMeasureSpecHeight)
    }
    
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!TextUtils.isEmpty(mText)) {
            canvas?.drawText(mText, 0, mText.length, 0f, mMeasureSpecHeight.toFloat(), mPaintText)
        }
    }
    
    fun setText(text: String) {
        if (!TextUtils.isEmpty(text)) {
            mText = text
            val rect = Rect()
            mPaintText.getTextBounds(mText, 0, mText!!.length, rect)
            mMeasureSpecWidth = rect.width()
            mMeasureSpecHeight = rect.height()
            requestLayout()
        }
    }
    
}