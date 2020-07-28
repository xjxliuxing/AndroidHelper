package com.xjx.apphelper.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by erge 2019-10-14 19:58
 */
public class DateUtils {

    public static String getDateYYMMDD(Long time) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(time);
    }

    public static String getDateYYMMDDHHMM(Long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(time);
    }

    public static String getDateYYMMDDHHMMSS(Long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(time);
    }

    public static String getDateYYMMDD(String time) {
        if (TextUtils.isEmpty(time)) return "";
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(time);
    }

    public static String getDateYYMMDDHHMM(String time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(time);
    }

    public static String getDateYYMMDDHHMMSS(String time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(time);
    }

    /**
     * @param parrent 指定格式，例如：yyyy-MM-dd HH:mm:ss
     * @return 获取当前的时间，返回String类型的字符串
     */
    public static String getCurrentTimeToString(String parrent) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(parrent);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * @param pattern 指定的匹配符
     * @param value   需要转换的字符串
     * @return 把指定的字符串通过固定格式转换成long类型的数据
     */
    public static long SimpleDateStringToLong(String pattern, String value) {

        // 1:把日期转换成毫秒，和当前的时间做对比
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);//24小时制
        long time = 0;
        try {
            time = simpleDateFormat.parse(value).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * @param dateValue 把固定格式的时间解析成一个包含年月日的数组 ，例如：2019-11-26，转换为：2019,11，26
     * @param pattern   格式化类型：例如："yyyy-MM-dd"
     * @return
     */
    public static int[] DateStringToArray(String dateValue, String pattern) {

        int[] array = new int[3];

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateValue);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            int mParseYear = c.get(Calendar.YEAR);
            int mParseMonth = c.get(Calendar.MONTH);
            int mParseDay = c.get(Calendar.DAY_OF_MONTH);

            array[0] = mParseYear;
            array[1] = (mParseMonth + 1);
            array[2] = mParseDay;

        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.e("转换日期失败！");
        }
        return array;
    }

    /**
     * @param dateValue 指定类型的字符串：例如：2019-11-23
     * @param pattern   格式化类型：例如："yyyy-MM-dd"
     * @return 把一个字符串转换成一个具体的date的日期类
     */
    public static Date DateStringToDate(String dateValue, String pattern) {
        Date date = null;
        try {
            int[] ints = DateStringToArray(dateValue, pattern);
            date = new Date(ints[0], ints[1], ints[2]);
        } catch (Exception e) {
            LogUtil.e("转换日期失败！");
        }
        return date;
    }

}
