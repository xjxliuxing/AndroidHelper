package com.xjx.helper.ui

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus

/**
 *  @作者           徐腾飞
 *  @创建时间       2020/1/15  19:34
 *  @更新者         HongJing
 *  @更新时间       2020/1/15  19:34
 *  @描述           下载管理叶页面
 */
class DownLoadManagerActivity : CommonBaseTitleActivity() {

    override fun getTitleLayout(): Int {
        return R.layout.activity_down_load_manager;
    }

    override fun getTitleContent(): String {
        return "下载管理区"
    }

    override fun initView() {
        super.initView()
        LoadingStatus(PlaceholderStatus.NONE)
    }

}
