package com.xjx.helper.ui.home.activity.tod.java

import android.view.View
import com.xjx.helper.R
import com.xjx.helper.base.CommonBaseTitleActivity
import com.xjx.helper.enums.PlaceholderStatus
import com.xjx.helper.utils.LogUtil
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 线程池测试
 */
class ThreadExecutorActivity : CommonBaseTitleActivity() {

    private lateinit var singleThreadExecutor: ExecutorService
    private var index: Int = 0

    override fun getTitleLayout(): Int {
        return R.layout.activity_thread_executor
    }

    override fun initView() {
        super.initView()
        SwitchLoadingStatus(PlaceholderStatus.NONE)
        setTitleContent("线程池测试类")
    }

    override fun initListener() {
        super.initListener()
        setOnClick(R.id.btn_start)
    }

    override fun initData() {
        super.initData()
        /**
         * 使用线程池的接口去创建一个单利的线程池对象
         */
        singleThreadExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v?.id) {
            R.id.btn_start -> {
                ++index
                executeThread(index)
            }
        }
    }

    /**
     * 单一线程的处理
     */
    private fun executeThread(position: Int) {
        singleThreadExecutor.execute(Runnable {
            try {
                val threadName = Thread.currentThread().name
                LogUtil.e("执行的是：  $position  线程名字： $threadName")
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        })
    }

}