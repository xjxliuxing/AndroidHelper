package com.xjx.helper.ui.home.fragments

import android.content.Intent
import android.view.View
import com.xjx.helper.R
import com.xjx.helper.adapter.TestAdapter
import com.xjx.helper.base.CommonBaseRefreshListFragment
import com.xjx.helper.entity.StoreActivityBean
import com.xjx.helper.http.client.ApiException
import com.xjx.helper.http.client.ApiServices
import com.xjx.helper.http.client.BaseResponse
import com.xjx.helper.http.client.Page
import com.xjx.helper.ui.DownLoadManagerActivity
import com.xjx.helper.ui.home.activity.tod.DownLoadActivity
import com.xjx.helper.utils.recyeliview.RecycleUtil
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call


/**
 * 首页
 */
class HomeFragment : CommonBaseRefreshListFragment<StoreActivityBean>() {

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

    override fun onHttpSuccess(list: MutableList<Any?>?) {

    }

    override fun onHttpFailure(e: ApiException?) {

    }


    override fun onClick(v: View?) {
        super.onClick(v)

        val intent = Intent()
        when (v?.id) {
            R.id.tv_test -> intent.setClass(mContext, DownLoadActivity::class.java)

            R.id.tv_test2 -> intent.setClass(mContext, DownLoadManagerActivity::class.java)
        }
        startActivity(intent)
    }

    override fun getHttp(): Call<BaseResponse<Page<StoreActivityBean>>> {
        val map = HashMap<String, Any>()
        map["angent_id"] = "ff808081647099c101648d5526980084"
        val stringObjectMap = setPageBody(map)
        return ApiServices.test.getStoreActivity(stringObjectMap)
    }
}
