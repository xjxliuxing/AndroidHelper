package com.xjx.helper.ui.home.activity.todo.customviewui

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import kotlinx.android.synthetic.main.activity_touch_unlock.*

class TouchUnlockActivity : CommonBaseTitleActivity() {
    
    override fun getTitleLayout(): Int {
        return R.layout.activity_touch_unlock
    }
    
    override fun initView() {
        super.initView()
        setTitleContent("自定义触摸解锁效果")
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        
        
        
        start.setOnClickListener {
            cv.startView(true)
            
//            waaaa.startAnim()
        }
        end.setOnClickListener {
            cv.endView()
        }
    }
    
}