package com.xjx.helper.ui.home.fragments

import com.xjx.apphelper.utils.recyeliview.RecycleUtil
import com.xjx.helper.R
import com.xjx.helper.adapter.TestAdapter
import com.xjx.apphelper.base.CommonBaseRefreshListFragment
import com.xjx.helper.entity.StoreActivityBean
import com.xjx.helper.http.client.ApiException
import com.xjx.helper.http.client.ApiServices
import com.xjx.apphelper.http.BaseResponse
import com.xjx.apphelper.http.Page
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call

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

    override fun getHttp(): Call<BaseResponse<Page<StoreActivityBean>>> {
        val map = HashMap<String, Any>()
        map["angent_id"] = "ff808081647099c101648d5526980084"
        val stringObjectMap = setPageBody(map)
        return ApiServices.test.getStoreActivity(stringObjectMap)
    }
}
