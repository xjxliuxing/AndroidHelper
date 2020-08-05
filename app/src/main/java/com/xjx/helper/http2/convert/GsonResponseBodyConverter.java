package com.xjx.helper.http2.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.xjx.helper.utils.LogUtil;
import com.xjx.helper.http2.BaseResponse;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by erge 2019-11-08 10:20
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String json = value.string();
            LogUtil.d("result = " + json);
            JSONObject jsonObject = new JSONObject(json);
            int returnCode = jsonObject.optInt("returnCode");
            LogUtil.d("result-code = " + returnCode);
            if (returnCode != BaseResponse.REQUEST_SUCCESS) {
                BaseResponse result = new BaseResponse();
                String returnMsg = jsonObject.optString("returnMsg");
                result.setReturnCode(returnCode);
                result.setReturnMsg(returnMsg);
                return (T) result;
            }
            return adapter.fromJson(json);
        } catch (Exception e) {
            LogUtil.d("result-error = " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            LogUtil.d("result-close");
            value.close();
        }
    }
}

