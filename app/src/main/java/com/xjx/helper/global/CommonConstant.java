package com.xjx.helper.global;

/**
 * <p>项目中公共的常量类<p>
 * <p>作者：hp<p>
 * <p>创建时间：2019/1/14<p>
 * <p>更改时间：2019/1/14<p>
 */
public class CommonConstant {
    /**
     * 应用名字
     */
    public static final String AppName = "BuddyEnglish";
    /**
     * Sp 存储的文件名
     */
    public static final String SP_FILE_NAME = "userInfo";
    /**
     * 微信的appID
     */
    public static final String WX_APPID = "wx30616cf0594902b7";

    /**
     * 绑定的手机号
     */
    public static final String USER_BIND_PHONE_NUMBER = "user_bind_phone_number";

    /**
     * 状态栏高度
     */
    public static final String STATUS_HEIGHT = "status_height";

    /**
     * 手机号码号码的正则表达式
     */
    public static final String REGEX_PHONE_NUMBER = "^0?(13|14|15|16|17|18|19)[0-9]{9}$";
    /**
     * 身份证号的正则表达式
     */
    public static final String REGEX_ID_NUMBER = "^\\d{17}[\\d|x]|\\d{15}";
    /**
     * ---------------------------- CODE ----------------------------------------------
     */

    // dialog的show方法监听
    public static final int CODE_DIALOG_UTIL_SHOW = 1000;
    // dialog的dimiss方法监听
    public static final int CODE_DIALOG_UTIL_CLOSE = CODE_DIALOG_UTIL_SHOW + 1;
}

