package com.xjx.helper.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * 跳转系统界面使用
 * Created by erge 2019-10-12 18:31
 */
public class SystemIntentUtils {

    /**
     * 拨打电话
     *
     * @param phone 手机号
     */
    public static void callPhone(Context context, String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToast("号码缺失，请手动输入");
//            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 发送短信
     *
     * @param to      给谁发
     * @param smsBody 消息内容
     */
    public static void sendSms(Context context, String to, String smsBody) {
        Uri smsToUri = Uri.parse(String.format("smsto:%s", to));
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        context.startActivity(intent);
    }

}
