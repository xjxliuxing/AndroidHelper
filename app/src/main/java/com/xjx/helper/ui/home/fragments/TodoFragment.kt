package com.xjx.helper.ui.home.fragments

import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseFragment
import com.xjx.helper.utils.DialogFragments


/**
 * 待办的fragmen
 */
class TodoFragment : CommonBaseFragment() {

    override fun getLayout(): Int {
        return R.layout.fragment_todo
    }

    override fun initData() {
        super.initData()

         DialogFragments.showDialog(childFragmentManager,R.layout.popup_newbie_guide)
    }

}
