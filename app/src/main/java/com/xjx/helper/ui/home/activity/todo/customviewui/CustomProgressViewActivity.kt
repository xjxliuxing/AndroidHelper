package com.xjx.helper.ui.home.activity.todo.customviewui

import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.customview.ProgressView2
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.ToastUtil

/**
 *带进度条的View
 */
class CustomProgressViewActivity : CommonBaseTitleActivity() {
    
    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_progress;
    }
    
    override fun initView() {
        super.initView()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("进度条的View")
        
        val findViewById1 = findViewById<ProgressView2>(R.id.pb2)
        val ed_input = findViewById<EditText>(R.id.ed_input)
        
        findViewById<Button>(R.id.btn).setOnClickListener { v ->
            val toString = ed_input.text.toString()
            if (TextUtils.isEmpty(toString)) {
                ToastUtil.showToast("数据不能为空")
                return@setOnClickListener
            }
            findViewById1.setCharging(true)
//            findViewById1.setProgress(toString.toInt())
            findViewById1.startAnimation(toString.toInt())
        }
    }
    
    override fun onRestart() {
        super.onRestart()
        
    }
    
}