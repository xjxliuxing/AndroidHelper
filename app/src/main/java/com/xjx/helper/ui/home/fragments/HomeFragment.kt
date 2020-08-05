package com.xjx.helper.ui.home.fragments

import com.xjx.helper.R
import com.xjx.helper.adapter.TestAdapter
import com.xjx.helper.base.CommonBaseRefreshListFragment
import com.xjx.helper.entity.StoreActivityBean
import com.xjx.helper.http.client.ApiServices
import com.xjx.helper.http2.ApiException
import com.xjx.helper.http2.BaseResponse
import com.xjx.helper.http2.Page
import com.xjx.helper.utils.recyeliview.RecycleUtil
import kotlinx.android.synthetic.main.activity_custom_recycle_view.*

/**
 * 首页
 */
class HomeFragment : CommonBaseRefreshListFragment<StoreActivityBean>() {

    override fun onHttpListSuccess(list: MutableList<Any?>?) {

    }

    override fun onHttpListFailure(e: ApiException?) {

    }

    override fun getRefreshLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        super.initData()

        val testAdapter = TestAdapter(mContext)
        RecycleUtil
                .getInstance(mContext, rv_list)
                .setVertical()
                .setAdapter(testAdapter)

        setAdapter(testAdapter)
    }

    override fun getHttp(): retrofit2.Call<BaseResponse<Page<StoreActivityBean>>> {
        val map = HashMap<String, Any>()
        map["angent_id"] = "ff808081647099c101648d5526980084"
        val stringObjectMap = setPageBody(map)
        return ApiServices.test.getStoreActivity(stringObjectMap)
    }

}
