package com.xjx.helper.ui.home.activity.tod

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
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
    private lateinit var mClHead: ConstraintLayout
    private lateinit var mRlFoot: RelativeLayout

    private var mHeightHead: Int = 0
    private var mHeightFoot: Int = 0
    //    var scrollView: NestedScrollView? = null
    var scrollView: FrameLayout? = null
    private var pvTime: TimePickerView? = null
    lateinit var ll_content: LinearLayout


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
                    // 父布局的对象
                    ll_content = view.findViewById(R.id.ll_content)

                    scrollView = view.findViewById(R.id.sl_content)
                    mClHead = view.findViewById(R.id.cl_head)

                    // 获取上侧高度和下侧的高度
                    mClHead?.viewTreeObserver?.addOnPreDrawListener(
                            object : ViewTreeObserver.OnPreDrawListener {
                                override fun onPreDraw(): Boolean {
                                    mClHead?.viewTreeObserver?.removeOnPreDrawListener(this)
                                    mHeightHead = mClHead?.height!! // 获取高度

                                    // 控制view的高度
                                    if (ll_content != null) {
                                        val layoutParams = ll_content?.layoutParams
                                        if (layoutParams != null) {
                                            layoutParams.height = mHeightHead
                                        }
                                        ll_content!!.layoutParams = layoutParams
                                    }
                                    LogUtil.e("height---1:$mHeightHead")
                                    return true
                                }
                            })

                    mRlFoot = view.findViewById(R.id.rl_foot)
                    mRlFoot.viewTreeObserver?.addOnPreDrawListener(
                            object : ViewTreeObserver.OnPreDrawListener {
                                override fun onPreDraw(): Boolean {
                                    mRlFoot?.viewTreeObserver?.removeOnPreDrawListener(this)
                                    mHeightFoot = mRlFoot?.height!! // 获取高度

                                    LogUtil.e("height---2:$mHeightFoot")
                                    return true
                                }
                            })

                    // 添加时间选择器
                    addTimerSelector(mRlFoot)


                    // 下侧布局的按钮的事件
                    view.findViewById<Button>(R.id.btn).setOnClickListener { view ->
                        if (ll_content != null) {
                            val layoutParams = ll_content?.layoutParams
                            if (layoutParams != null) {
                                layoutParams.height = mHeightHead
                            }
                            ll_content!!.layoutParams = layoutParams
                        }
//                        scrollView?.fullScroll(ScrollView.FOCUS_UP);
                    }
                }
                .setOnclickListener(R.id.ll_start_time) { view ->
                    // 开始时间的选择1


//                    val objectAnimator = ObjectAnimator.ofFloat(mClHead, "translationY", 0.0f, -(mHeightHead.toFloat())) //沿着x轴平移
//                    objectAnimator.duration = 2000
//                    objectAnimator.start()

                    val value = mHeightFoot - mHeightHead

                    val animator1 = ObjectAnimator.ofFloat(mRlFoot, "translationY", mHeightHead.toFloat(), 0.0f)
                    animator1.duration = 4000
                    animator1.start()

                    val aa = AnimatorSet()
//                    aa.playTogether(objectAnimator, animator1)
                    aa.playTogether(animator1)
//                    aa.duration = 3000
                    aa.start()

//                    ToastUtil.showToast("开始事件")
//                    val layoutParams = ll_content.layoutParams
//                    if (layoutParams != null) {
//                        layoutParams.height = this.mHeightFoot
//                        ll_content.layoutParams = layoutParams
//                    }

                    scrollView?.post {
                        //                        scrollView?.fullScroll(ScrollView.FOCUS_DOWN);
                    }


                }.setOnclickListener(R.id.ll_end_time) { view ->
                    // 结束时间的选择
                    // 滑动到顶部
//                    scrollView?.fullScroll(ScrollView.FOCUS_UP);

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

    private fun addTimerSelector(mRlFoot: RelativeLayout) {


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
        pvTime = TimePickerBuilder(mContext, OnTimeSelectListener { date, v ->
            //选中事件回调
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


    }

}

