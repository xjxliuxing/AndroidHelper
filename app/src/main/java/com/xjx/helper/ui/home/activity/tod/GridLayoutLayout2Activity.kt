package com.xjx.helper.ui.home.activity.tod

import com.xjx.helper.R
import com.xjx.helper.adapter.todo.GridViewAdapter
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.entity.todo.GridLayoutLayoutBean
import com.xjx.helper.enums.PlaceholderStatus
import kotlinx.android.synthetic.main.activity_grid_layout_layout2.*
import java.util.*

class GridLayoutLayout2Activity : CommonBaseTitleActivity() {

    private val mList = ArrayList<GridLayoutLayoutBean>()

    override fun getTitleLayout(): Int {
        return R.layout.activity_grid_layout_layout2
    }

    override fun initData() {
        super.initData()

        SwitchLoadingStatus(PlaceholderStatus.NONE)

        for (i in 0..13) {
            val bean = GridLayoutLayoutBean()
            bean.url = "http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLATnroK8v8mN0o0yIkBEcIU2umnMfqIZMW3HwtFX8aL87r1RGGbDyQ6L7CoFsM496ibrxgpnzDpSsicSplfkS9ypluMicOMBAvVcc/132"
            mList.add(bean)
        }
        val adapter = GridViewAdapter(mContext, mList)
        gv_list.adapter = adapter
    }

}
