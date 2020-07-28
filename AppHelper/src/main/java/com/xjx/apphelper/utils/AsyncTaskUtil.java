package com.xjx.apphelper.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

/**
 * 异步获取数据的工具类
 * a. Params：开始异步任务执行时传入的参数类型，对应excute（）中传递的参数
 * b. Progress：异步任务执行过程中，返回下载进度值的类型
 * c. Result：异步任务执行完成后，返回的结果类型，与doInBackground()的返回值类型保持一致
 * 注：
 * a. 使用时并不是所有类型都被使用
 * b. 若无被使用，可用java.lang.Void类型代替
 * c. 若有不同业务，需额外再写1个AsyncTask的子类
 */
@SuppressLint("NewApi")
public abstract class AsyncTaskUtil<Params, Result> extends AsyncTask<Params, Long, Result> {

    // 方法1：onPreExecute（）
    // 作用：执行 线程任务前的操作
    // 注：根据需求复写
    @Override
    protected void onPreExecute() {

    }

    // 方法2：doInBackground（）
    // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
    // 注：必须复写，从而自定义线程任务
//    @Override
//    protected Result doInBackground(Params... params) {
//        // 自定义的线程任务
//
//        // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
//        publishProgress(count);
//
//    }

    // 方法3：onProgressUpdate（）
    // 作用：在主线程 显示线程任务执行的进度
    // 注：根据需求复写
    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
    }

    // 方法4：onPostExecute（）
    // 作用：接收线程任务执行结果、将执行结果显示到UI组件
    // 注：必须复写，从而自定义UI操作
    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        // UI操作

    }

    // 方法5：onCancelled()
    // 作用：将异步任务设置为：取消状态
    @Override
    protected void onCancelled() {

    }
}
