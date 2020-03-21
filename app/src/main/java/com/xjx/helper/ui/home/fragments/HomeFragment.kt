package com.xjx.helper.ui.home.fragments

import android.content.Intent
import android.view.View
import com.xjx.helper.R
import com.xjx.helper.adapter.TestAdapter
import com.xjx.helper.base.CommonBaseRefreshListFragment
import com.xjx.helper.entity.StoreActivityBean
import com.xjx.helper.http.client.*
import com.xjx.helper.ui.DownLoadManagerActivity
import com.xjx.helper.ui.home.activity.tod.DownLoadActivity
import com.xjx.helper.utils.recyeliview.RecycleUtil
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * 首页
 */
class HomeFragment : CommonBaseRefreshListFragment<StoreActivityBean>() {

    var testAdapter: TestAdapter? = null

    override fun getRefreshLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        super.initData()

        testAdapter = TestAdapter(mContext, data)
        RecycleUtil
                .getInstance(mContext, rv_list)
                .setVertical()
                .setAdapter(testAdapter)
        setAdapter(testAdapter)
    }


    override fun onRequestData() {

        val map = HashMap<String, Any>()
        map["angent_id"] = "ff808081647099c101648d5526980084"
        val stringObjectMap = setPageBody(map)

        ApiServices.test.getStoreActivity(stringObjectMap).enqueue(object : BaseResponseCallBack<BaseResponse<Page<StoreActivityBean>>>() {
            override fun onSuccess(response: BaseResponse<Page<StoreActivityBean>>) {
                switchPlaceHolderSuccess(response)
                val data = response.returnDataList.data

                setData(data)
            }

            override fun onFailured(t: ApiException) {
                switchPlaceHolderFailure(t)
                ApiException.ToastError(t)
            }
        })
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
}
