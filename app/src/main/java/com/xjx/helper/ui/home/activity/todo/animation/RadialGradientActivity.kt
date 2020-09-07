package com.xjx.helper.ui.home.activity.todo.animation

import android.widget.Button
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import kotlinx.android.synthetic.main.activity_radial_gradient.*

class RadialGradientActivity : CommonBaseTitleActivity() {
    
    override fun getTitleLayout(): Int {
        return R.layout.activity_radial_gradient
    }
    
    override fun initView() {
        super.initView()
        setTitleContent("自定义放射动画效果")
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        
        findViewById<Button>(R.id.btn).setOnClickListener {
            cst.startAnim()
        }
    }
}