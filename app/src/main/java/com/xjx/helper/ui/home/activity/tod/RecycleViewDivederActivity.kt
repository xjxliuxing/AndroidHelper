package com.xjx.helper.ui.home.activity.tod

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.xjx.helper.R
import com.xjx.helper.adapter.RecycleviewDividerAdapter
import com.xjx.apphelper.base.CommonBaseTitleActivity
import com.xjx.apphelper.enums.PlaceholderStatus
import com.xjx.apphelper.utils.ConvertUtil
import com.xjx.apphelper.utils.recyeliview.RecycleUtil
import com.xjx.apphelper.utils.recyeliview.RecycleViewBottomDivider
import kotlinx.android.synthetic.main.activity_recycle_view_diveder.*

/**
 * @作者 徐腾飞
 * @创建时间 2020/3/4  11:28
 * @更新者 HongJing
 * @更新时间 2020/3/4  11:28
 * @描述 recycleview的分割线
 */
class RecycleViewDivederActivity : CommonBaseTitleActivity() {

    val mList = arrayListOf<String>()
    var dividerAdapter: RecycleviewDividerAdapter? = null
    var divider: RecycleViewBottomDivider? = null;

    override fun getTitleLayout(): Int {
        return R.layout.activity_recycle_view_diveder
    }

    override fun initListener() {
        super.initListener()
        btn_vertical.setOnClickListener(this)
        btn_horizontal.setOnClickListener(this)
    }

    override fun initData() {
        super.initData()
        SwitchLoadingStatus(PlaceholderStatus.NONE)


        rv_list.post {
            run {
                for (index in 1..3) {
                    mList.add("" + index)
                }
            }
        }
        dividerAdapter = RecycleviewDividerAdapter(mContext)
        dividerAdapter?.setList(mList)

        val dividerHeight = ConvertUtil.toDp(1f).toInt()
        divider = RecycleViewBottomDivider(mContext, LinearLayoutManager.VERTICAL, R.drawable.shape_divider, dividerHeight, 0)
        val left = ConvertUtil.toDp(17f).toInt()
        divider?.setMargin(dividerHeight, left, 0, left, 0)

//        divider?.setMargin(40, 0, 80, 0, 20)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.btn_vertical -> {

                divider?.let {
                    rv_list.removeItemDecoration(it)
                    it.setOrientation(LinearLayoutManager.VERTICAL)
                    rv_list.addItemDecoration(it)
                }

                RecycleUtil.getInstance(mContext, rv_list)
                        .setVertical()
                        .setDataHeight()
                        .setAdapter(dividerAdapter)
            }

            R.id.btn_horizontal -> {

                divider?.let {
                    rv_list.removeItemDecoration(it)
                    it.setOrientation(LinearLayoutManager.HORIZONTAL)
                    rv_list.addItemDecoration(it)
                }

                RecycleUtil.getInstance(mContext, rv_list)
                        .setHorizontal()
                        .setDataHeight()
                        .setAdapter(dividerAdapter)
            }
        }
    }

}
