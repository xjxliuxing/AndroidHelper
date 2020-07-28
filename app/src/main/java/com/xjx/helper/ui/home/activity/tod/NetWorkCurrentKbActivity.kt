package com.xjx.helper.ui.home.activity.tod

import com.xjx.helper.R
import com.xjx.apphelper.base.CommonBaseTitleActivity
import com.xjx.apphelper.enums.PlaceholderStatus
import com.xjx.apphelper.utils.LogUtil
import com.xjx.apphelper.utils.NetworkCurrentKbUtil
import kotlinx.android.synthetic.main.activity_net_work_current_kb.*

class NetWorkCurrentKbActivity : CommonBaseTitleActivity() {

    private var kbUtil: NetworkCurrentKbUtil? = null

    override fun getTitleLayout(): Int {
        return R.layout.activity_net_work_current_kb
    }

    override fun initData() {
        super.initData()

        SwitchLoadingStatus(PlaceholderStatus.NONE)

        kbUtil = NetworkCurrentKbUtil.getInstance(mContext)


        button1.setOnClickListener { view ->
            kbUtil?.getCurrentNetworkSpeed { currentKb ->

                LogUtil.e("--------->:$currentKb")
                textView.text = "当前的网速是：$currentKb kb"
            }
        }

        button3.setOnClickListener { view ->
            kbUtil?.remove()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        kbUtil?.clear()
    }
}
