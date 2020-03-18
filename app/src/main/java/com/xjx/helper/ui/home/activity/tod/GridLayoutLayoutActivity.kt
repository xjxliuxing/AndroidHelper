package com.xjx.helper.ui.home.activity.tod

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xjx.helper.R
import com.xjx.helper.adapter.todo.GridLayoutLayoutAdapter
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.entity.todo.GridLayoutLayoutBean
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.ConvertUtil
import com.xjx.helper.utils.recyeliview.RecycleUtil
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
                .setGridLayout(4)
//                .setDivder(EvenItemDecoration(dp, 4))
                .setAdapter(adapter)

        adapter.setList(mList)
    }


    class EvenItemDecoration(val space: Int, val column: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            // 每个span分配的间隔大小
            val spanSpace = space * (column + 1) / column
            // 列索引
            val colIndex = position % column
            // 列左、右间隙
            if (colIndex == 0) {
                outRect.left = 0
            } else {
                outRect.left = space * (colIndex + 1) - spanSpace * colIndex
            }
            if (colIndex == column - 1) {
                outRect.right = 0
            } else {
                outRect.right = spanSpace * (colIndex + 1) - space * (colIndex + 1)
            }
            // 行间距
            if (position >= column) {
                outRect.top = space
            }
        }
    }
}
