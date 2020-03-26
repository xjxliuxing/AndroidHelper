package com.xjx.helper.ui.home.activity.tod

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.DialogUtil
import com.xjx.helper.utils.LogUtil
import kotlinx.android.synthetic.main.activity_dialog.*
import java.util.*


class DialogActivity : CommonBaseTitleActivity() {

    private var dialogUtil: DialogUtil? = null
    private var mClHead: ConstraintLayout? = null
    private var mRlFoot: RelativeLayout? = null

    private var mHeightHead: Int = 0
    private var mHeightFoot: Int = 0
    var scrollView: NestedScrollView? = null
    private var pvTime: TimePickerView? = null

    override fun getTitleLayout(): Int {
        return R.layout.activity_dialog
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun initData() {
        super.initData()

        SwitchLoadingStatus(PlaceholderStatus.NONE)

        dialogUtil = DialogUtil
                .getInstance(mContext, false)
                .setContentView(R.layout.dialog_bind_car_add_task)
                .setClose(R.id.tv_close)
                .setWindowAnimations(R.style.BottomDialog_Animation)
                .setGravity(Gravity.BOTTOM)
                .setBindViewData { view ->
                    scrollView = view.findViewById<NestedScrollView>(R.id.sl_content)

                    mClHead = view.findViewById(R.id.cl_head)
                    mClHead?.viewTreeObserver?.addOnPreDrawListener(
                            object : ViewTreeObserver.OnPreDrawListener {
                                override fun onPreDraw(): Boolean {
                                    mClHead?.viewTreeObserver?.removeOnPreDrawListener(this)
                                    mHeightHead = mClHead?.height!! // 获取高度

                                    // 控制view的高度
                                    if (scrollView != null) {
                                        val layoutParams = scrollView?.layoutParams
                                        if (layoutParams != null) {
                                            layoutParams.height = mHeightHead
                                        }
                                        scrollView!!.layoutParams = layoutParams
                                    }
                                    LogUtil.e("height---1:$mHeightHead")
                                    return true
                                }
                            })


                    mRlFoot = view.findViewById(R.id.rl_foot)


                    //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
                    //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11

                    //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
                    //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
                    val selectedDate = Calendar.getInstance()
                    val startDate = Calendar.getInstance()
                    startDate[2013, 0] = 23
                    val endDate = Calendar.getInstance()
                    endDate[2019, 11] = 28
                    //时间选择器
                    //时间选择器
                    pvTime = TimePickerBuilder(mContext, OnTimeSelectListener { date, v -> //选中事件回调
                        // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                        /*btn_Time.setText(getTime(date));*/
                        val btn = v as Button
                    }).setLayoutRes(R.layout.pickerview_custom_time) { }
                            .setType(booleanArrayOf(true, true, true, false, false, false))
                            .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                            .setDividerColor(Color.DKGRAY)
                            .setContentTextSize(20)
                            .setDate(selectedDate)
                            .setRangDate(startDate, selectedDate)
                            .setDecorView(mRlFoot) //非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                            //                .setOutSideColor(0x00000000)
                            .setOutSideCancelable(false)
                            .build()

                    pvTime?.setKeyBackCancelable(false) //系统返回键监听屏蔽掉
                    pvTime?.show(mRlFoot, false) //弹出时间选择器，传递参数过去，回调的时候则可以绑定此view

                    mRlFoot?.viewTreeObserver?.addOnPreDrawListener(
                            object : ViewTreeObserver.OnPreDrawListener {
                                override fun onPreDraw(): Boolean {
                                    mRlFoot?.viewTreeObserver?.removeOnPreDrawListener(this)
                                    mHeightFoot = mRlFoot?.height!! // 获取高度

                                    LogUtil.e("height---2:$mHeightFoot")
                                    return true
                                }
                            })

                    view.findViewById<Button>(R.id.btn).setOnClickListener { view ->


                        if (scrollView != null) {
                            val layoutParams = scrollView?.layoutParams
                            if (layoutParams != null) {
                                layoutParams.height = mHeightHead
                            }
                            scrollView!!.layoutParams = layoutParams
                        }

                        scrollView?.fullScroll(ScrollView.FOCUS_UP);

                    }
                    scrollView?.setOnTouchListener(object : View.OnTouchListener {
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                            if (event?.action == MotionEvent.ACTION_MOVE) {
                                return true
                            }
                            return false
                        }
                    })

                    mRlFoot?.setOnTouchListener(object :View.OnTouchListener{
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                            if (event?.action == MotionEvent.ACTION_MOVE) {
                                return true
                            }
                            return false
                        }

                    })
                }
                .setOnclickListener(R.id.ll_start_time) { view ->


                    val layoutParams = scrollView?.layoutParams
                    if (layoutParams != null) {
                        layoutParams.height = this.mHeightFoot
                        scrollView!!.layoutParams = layoutParams
                    }

                    scrollView?.post {
                        scrollView?.fullScroll(ScrollView.FOCUS_DOWN);
                    }


                }.setOnclickListener(R.id.ll_end_time) { view ->
                    // 滑动到顶部
                    scrollView?.fullScroll(ScrollView.FOCUS_UP);

                    if (scrollView != null) {
                        val layoutParams = scrollView?.layoutParams
                        if (layoutParams != null) {
                            layoutParams.height = mHeightHead
                        }
                        scrollView!!.layoutParams = layoutParams
                    }

                }

        button.setOnClickListener { view ->
            dialogUtil?.show()
        }


    }

}

