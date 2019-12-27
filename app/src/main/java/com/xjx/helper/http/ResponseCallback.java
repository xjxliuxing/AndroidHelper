//package com.xjx.helper.http;
//
//import com.xjx.helper.utils.LogUtil;
//import com.xjx.helper.http.HttpClient;
//
//import java.io.IOException;
//import java.net.SocketException;
//import java.net.SocketTimeoutException;
//import java.net.UnknownHostException;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * 对网络返回的结果进行统一处理
// * 注意：
// * 如果网络返回的数据是可以用BaseResponse来接收的，则会对结果进行统一处理，只要在onSuccess回调中拿到结果，
// * 则一定是后台返回了正常的值，只需要对值进行处理即可。
// * 如果返回的数据不是用BaseResponse来接收的，则需要在接口中自己去判断http的code以及后台的state各种值，判断来
// * code和state的值之后才能拿到正确的返回值处理
// * Created by erge 2019/9/18 10:29
// */
//public abstract class ResponseCallback<T> implements Callback<T>, IResponseCallback<T> {
//
//    @Override
//    public void onResponse(Call<T> call, Response<T> response) {
//        System.out.println("request_success = " + response.toString());
//        if (response.isSuccessful()) { // http返回2xx code
//            // 无论成功还是是失败，都需要调用这个方法
//            if (HttpClient.mRefreshControllerInterface != null) {
//                HttpClient.mRefreshControllerInterface.RefreshComplete();
//            }
//
//            T body = response.body();
//            if (body instanceof BaseResponse) { // 如果是走基类BaseResponse，则统一处理结果
//                BaseResponse baseResponse = (BaseResponse) body;
//                if (baseResponse.getReturnCode() == BaseResponse.REQUEST_SUCCESS) {
//                    onSuccess(body);
//                } else {
//                    onFailure(new ApiException(baseResponse.getReturnMsg(), baseResponse.getReturnStatus() + ""));
//                }
//            } else { // 如果不走基类BaseResponse，则直接返回结果
//                onSuccess(body);
//            }
//        } else { // http返回非2xx code
//            // 无论成功还是是失败，都需要调用这个方法
//            if (HttpClient.mRefreshControllerInterface != null) {
//                HttpClient.mRefreshControllerInterface.RefreshComplete();
//                LogUtil.e("---->" + "ResponseCallback---onFailure");
//            }
//            String errorMessage = response.message();
//            if (response.errorBody() != null) {
//                try {
//                    errorMessage = response.errorBody().string();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            onFailure(new ApiException(errorMessage, String.valueOf(response.code())));
//        }
//    }
//
//    @Override
//    public void onFailure(Call<T> call, Throwable t) {
//        System.out.println("request_error = " + t.getMessage());
//        if (!NetUtils.checkNetwork(CommonApp.getApplication())) {
//            onFailure(new ApiException(ApiException.NET_UNAVAILABLE));
//        } else {
//            if (t instanceof SocketException || t instanceof UnknownHostException) {
//                onFailure(new ApiException(ApiException.NET_ERROR)); // 链接错误
//            } else if (t instanceof SocketTimeoutException) {
//                onFailure(new ApiException(ApiException.TIME_OUT)); // 超时
//            } else if (t instanceof IOException && "Canceled".equals(t.getMessage())) {
//                onFailure(new ApiException(ApiException.USER_CANCELED)); // 用户主动取消
//            } else {
//                onFailure(new ApiException(ApiException.UNKNOWN_ERROR + "：" + t.getMessage())); // 未知错误
//            }
//        }
//    }
//
//}
