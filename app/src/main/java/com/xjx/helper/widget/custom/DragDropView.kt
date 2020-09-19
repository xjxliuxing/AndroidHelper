package com.xjx.helper.widget.custom

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Scroller
import com.xjx.helper.utils.LogUtil

/**
 * 自定义拖拽的界面
 * 目标：打造一个可以让具有上中下三个view的布局，让fix布局固定头顶，top布局固定中间，bottom布局固定底部
 */
class DragDropView(context: Context?, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    
    private val TAG: String = " ---> ScrollView <---"
    private lateinit var mHeadLayout: ViewGroup
    private lateinit var mTopLayout: ViewGroup
    private lateinit var mBottomLayout: ViewGroup
    
    private var mHeadHeight: Int = 0
    private var mTopHeight: Int = 0
    private var mBottomHeight: Int = 0
    
    private var startY: Float = 0f
    private var endY: Float = 0f
    private var mScroller: Scroller = Scroller(context)
    
    override fun onFinishInflate() {
        super.onFinishInflate()
        LogUtil.e("$TAG  view布局结束，开始查找view的tag")
        // 获取布局中的view
        for (index in 0..childCount) {
            val childAt = getChildAt(index)
            childAt?.let {
                childAt as ViewGroup
                val tag = childAt.getTag() as String
                if (TextUtils.equals(tag, "head")) {
                    mHeadLayout = childAt
                } else if (TextUtils.equals(tag, "top")) {
                    mTopLayout = childAt
                } else if (TextUtils.equals(tag, "bottom")) {
                    mBottomLayout = childAt
                }
            }
        }
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHeadHeight = mHeadLayout.measuredHeight
        mTopHeight = mTopLayout.measuredHeight
        mBottomHeight = mBottomLayout.measuredHeight
        
        LogUtil.e(TAG + "mHeadHeight :" + mHeadHeight + "  mTopHeight: " + mTopHeight + "  mBottomHeight:" + mBottomHeight)
        LogUtil.e(TAG + "width: :" + mTopLayout.measuredWidth + " height: " + (mTopHeight + mBottomHeight))
        
        setMeasuredDimension(mTopLayout.measuredWidth, (mTopHeight + mBottomHeight))
    }
    
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mHeadLayout.layout(0, 0, mHeadLayout.measuredWidth, mHeadHeight)
        mTopLayout.layout(0, mHeadHeight, mTopLayout.measuredWidth, mTopHeight)
        mBottomLayout.layout(0, mTopHeight, mBottomLayout.measuredWidth, mTopHeight + mBottomHeight)
    }
    
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                LogUtil.e(TAG + "ACTION_DOWN: :")
                startY = event.y
                return true
            }
    
            MotionEvent.ACTION_MOVE -> {
                LogUtil.e(TAG + "ACTION_MOVE: :")
                endY = event.y
                val fl = (endY - startY).toInt()
//                mTopLayout.layout(0, mHeadHeight + (fl), mTopLayout.measuredWidth, mTopHeight - fl)
                LogUtil.e(TAG + "ACTION_MOVE: :startY:" + startY + "  endY:" + endY + "  fl :" + fl)
        
        
//                mScroller.startScroll(0, -fl, 0, -fl);
        
//                invalidate();
                return true
            }
    
            MotionEvent.ACTION_UP -> {
                LogUtil.e(TAG + "ACTION_UP: :")
                endY = event.y
                return true
            }
        }
        return false
//        return super.onTouchEvent(event)
    }
    
    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }
    
}