package com.xjx.helper.ui.home.activity.todo.customviewui

import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import kotlinx.android.synthetic.main.activity_custom_text_view.*

class CustomTextViewActivity : CommonBaseTitleActivity() {
    
    override fun getTitleLayout(): Int {
        return R.layout.activity_custom_text_view
    }
    
    override fun initView() {
        super.initView()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("自定义textView")
        
        
        btn.setOnClickListener(View.OnClickListener {
            tccc.setText("12312123")
        })
    }
}