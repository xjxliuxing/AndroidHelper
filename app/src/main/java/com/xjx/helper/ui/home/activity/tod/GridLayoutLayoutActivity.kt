package com.xjx.helper.ui.home.activity.tod

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xjx.helper.R
import com.xjx.helper.adapter.todo.GridLayoutLayoutAdapter
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.entity.todo.GridLayoutLayoutBean
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.ConvertUtil
import com.xjx.helper.utils.recyeliview.RecycleUtil
import com.xjx.helper.utils.recyeliview.RecycleViewGridLayoutDivider
import java.util.*

class GridLayoutLayoutActivity : CommonBaseTitleActivity() {

    private var mRvList: RecyclerView? = null

    private val mList = ArrayList<GridLayoutLayoutBean>()

    override fun getTitleLayout(): Int {
        return R.layout.activity_gridlayout
    }

    override fun initData() {
        super.initData()

        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("车主手册")

        mRvList = findViewById(R.id.rv_list)
        for (i in 0..11) {
            val bean = GridLayoutLayoutBean()
            bean.url = "http://thirdwx.qlogo.cn/mmopen/ajNVdqHZLLATnroK8v8mN0o0yIkBEcIU2umnMfqIZMW3HwtFX8aL87r1RGGbDyQ6L7CoFsM496ibrxgpnzDpSsicSplfkS9ypluMicOMBAvVcc/132"
            mList.add(bean)
        }

        val adapter = GridLayoutLayoutAdapter(mContext)

        val toDp = ConvertUtil.toDp(10f)
        val dp = toDp.toInt()

        RecycleUtil
                .getInstance(mContext, mRvList)
                .setVertical()
                .setDataHeight()
                .setGridLayout(4, LinearLayoutManager.VERTICAL)
//                .setDivder(EvenItemDecoration(dp, 3))
//                .setDivder(DividerGridItemDecoration(mContext))
                .setDivder(RecycleViewGridLayoutDivider( dp, dp))
                .setAdapter(adapter)

        adapter.setList(mList)
    }


    class EvenItemDecoration(val space: Int, val column: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)


        }
    }
}
