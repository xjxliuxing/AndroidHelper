package com.xjx.helper.ui.home.activity.todo.customviewui

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus

/**
 * 自定义拖拽的界面
 */
class DragDropActivity : CommonBaseTitleActivity() {
    
    override fun getTitleLayout(): Int {
        return R.layout.activity_drag_drop
    }
    
    override fun initView() {
        super.initView()
        setTitleContent("自定义拖拽界面")
        SwitchLoadingStatus(PlaceholderStatus.NONE)
    }
}