package com.xjx.helper.ui.home.fragments

import android.content.Intent
import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseFragment
import com.xjx.helper.ui.DownLoadActivity
import com.xjx.helper.ui.DownLoadManagerActivity
import java.util.*


/**
 * 首页
 */
class HomeFragment : CommonBaseFragment() {

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun onRequestData() {

        val map = HashMap<String, Any>()
        map["angent_id"] = "ff808081647099c101648d5526980084"

//        val stringObjectMap = setPageBody(map)
//        LogUtil.e("map:$stringObjectMap")
//
//        ApiServices.test.getStoreActivity(stringObjectMap).enqueue(object : BaseResponseCallBack<BaseResponse<Page<StoreActivityBean>>>() {
//            override fun onSuccess(response: BaseResponse<Page<StoreActivityBean>>) {
//                switchPlaceHolderSuccess(response)
//                val data = response.returnDataList.data
//                setData(data)
//                //                testAdapter.setList(getData());
//            }
//
//            override fun onFailured(t: ApiException) {
//                switchPlaceHolderFailure(t)
//                ApiException.onFiled(t)
//            }
//        })
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
