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
public class DateUtils2 {
    private static DateUtils2 dateUtils;
    private static final String tag = "DateUtils";

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

    /***
     *
     * @return 获取唯一的对象
     */
    public static DateUtils2 getInstance() {
        if (dateUtils == null) {
            dateUtils = new DateUtils2();
        }
        return dateUtils;
    }

    /**
     * @return 获取当前的年、月、日、时（24小时）、分、秒
     */
    public int[] getCurrentTime() {
        try {
            int[] times = new int[6];

            Calendar calendar = Calendar.getInstance();

            //当前年
            int year = calendar.get(Calendar.YEAR);
            //当前月
            int month = (calendar.get(Calendar.MONTH)) + 1;
            //当前月的第几天：即当前日
            int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
            //当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            //当前分
            int minute = calendar.get(Calendar.MINUTE);
            //当前秒
            int second = calendar.get(Calendar.SECOND);

            times[0] = year;
            times[1] = month;
            times[2] = day_of_month;
            times[3] = hour;
            times[4] = minute;
            times[5] = second;

            return times;
        } catch (Exception e) {
            LogUtil.e(tag + "--->getCurrentTime");
            return null;
        }
    }

    /**
     * @param calendar calendar对象
     * @param pattern  固定的pattern，例如： yyyy-MM-dd HH:mm:ss
     * @return 根据传入的 Calendar对象和固定的规则，获取当前的年月日时分秒，例如：2019-09-03 23：34：09
     */
    public String getStringForCalendar(Calendar calendar, String pattern) {
        Date time = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(time);
        return dateString;
    }

    /**
     * @param time    02:02  或者 02-02 这两种格式，必须是两位数值的，一般都是小时和分钟，不能胡乱传递
     * @param regular 具体的格式 例如：02:02 中的 "："、  或者 02-02 的 "-"
     * @return 把特殊的字符串转换成一个long类型的具体数据，用来区分是今天还是明天，
     */
    public long getLongTimeForString(String time, String regular) {
        if (TextUtils.isEmpty(time)) {
            return 0;
        }
        long value = 0;

        try {
            String[] split = null;
            String replace = time.replace(" ", "");
            if (replace.contains(regular)) {
                split = time.split(regular);
            }

            if (split != null) {
                String sp1 = split[0];
                String sp2 = split[1];

                // 解析数字
                int value1 = Integer.parseInt(sp1);
                int value2 = Integer.parseInt(sp2);
                int[] currentTime = getCurrentTime();
                if (currentTime != null) {

                    Calendar calendar = Calendar.getInstance();

                    int hours = currentTime[3];
                    int minute = currentTime[4];

                    // 设置分钟 和 小时
                    calendar.set(Calendar.MINUTE, value1);
                    calendar.set(Calendar.MINUTE, value2);

                    LogUtil.e(tag + "--->hours:" + hours + "--->minute:" + minute + "--->value1:" + value1 + "--->value2:" + value2);

                    if (value1 < hours) {
                        // 日期天数增加1
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    } else if (value1 == hours) {
                        if (value2 < minute) {
                            // 日期天数增加1
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                        }
                    }

                    String stringForCalendar = getStringForCalendar(calendar, "yyyy-MM-dd HH:mm:ss");
                    LogUtil.e("当前的日期为：" + stringForCalendar);

                    long timeInMillis = calendar.getTimeInMillis();

                    LogUtil.e("timeInMillis:" + timeInMillis);

                    value = timeInMillis;
                    return value;
                }
            }
        } catch (Exception e) {
            LogUtil.e("getLongTimeForString--->数据转换失败：" + e.getMessage());
        }
        return value;
    }

}
