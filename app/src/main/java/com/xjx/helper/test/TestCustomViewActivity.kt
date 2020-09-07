package com.xjx.helper.test

import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import kotlinx.android.synthetic.main.activity_test_custom_view.*

class TestCustomViewActivity : CommonBaseTitleActivity() {
    
    override fun getTitleLayout(): Int {
        return R.layout.activity_test_custom_view
    }
    
    override fun initView() {
        super.initView()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("测试自定义view的执行顺序")
        
        
        btn.setOnClickListener {
            
            ssssssssw.visibility = if (ssssssssw.visibility == View.GONE) {
                View.VISIBLE
            } else {
                View.GONE
            }
            sssss.requestLayout()
        }
    }
}