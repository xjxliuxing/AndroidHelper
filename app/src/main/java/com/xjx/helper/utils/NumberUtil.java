package com.xjx.helper.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2019/6/12.
 * 数据的工具类
 */

public class NumberUtil {

    /**
     * @param d      指定的值
     * @param digits 保留的位数
     * @return 把数据转换成 保留指定位数的数据，使用四舍五入
     */
    public static String formatDouble(double d, int digits) {
        NumberFormat nf = NumberFormat.getNumberInstance();

        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);

        return nf.format(d);
    }

    /**
     * @param millisInFuture
     * @return 使用四舍五入的形式把long类型的数据转换成int类型
     */
    public static int NumberForLong(long millisInFuture) {
        int round = Math.round(millisInFuture / 1000);

        return round;
    }

    /**
     * @param array
     * @return 根据传入进来的数组，随机改变position位置，生成一个新的数组
     */
    public static int[] RandomSortToInt(int[] array) {

        if (array == null) {
            return null;
        } else {
            StringBuffer buffer = new StringBuffer();
            for (int s : array) {
                buffer.append(s + "，");
            }
            LogUtil.e("原来：array:" + buffer.toString());

            Random random = new Random();
            for (int i = 0; i < array.length; i++) {
                // 随机产生一个 0 - n（不包含n）的随机数
                int p = random.nextInt(i + 1);

                int tmp = array[i];
                array[i] = array[p];
                array[p] = tmp;
            }

            StringBuffer buffer1 = new StringBuffer();
            for (int s : array) {
                buffer1.append(s + "，");
            }
            LogUtil.e("后来：array:" + buffer1.toString());

            return array;
        }
    }

    /**
     * @param list
     * @return 产生一个0 - 集合长度的随机数,不包含集合长度 的角标
     */
    public static int RandomForListToIndex(List list) {
        if (list == null || list.size() <= 0) {
            return -1;
        } else {
            int size = list.size();

            int index = (int) (Math.random() * (size));

            LogUtil.e("list --->size:" + list.size() + " 随机数：" + index);

            return index;
        }
    }

    /**
     * @param list
     * @return 获取String类型结合种从0 到 集合长度（不包含集合长度）的一个随机数
     */
    public static String RandomForListToString(List<String> list) {
        if (list == null || list.size() <= 0) {
            return null;
        } else {
            int size = list.size();

            int index = (int) (Math.random() * (size));

            LogUtil.e("list --->size:" + list.size() + " 随机数：" + index);

            return list.get(index);
        }
    }

    /**
     * @param array
     * @return 随机改变传入数组的位置，产生一个新的随机数组
     */
    public static String[] RandomSortToString(String[] array) {

        if (array == null) {
            return null;
        } else {
            StringBuffer buffer = new StringBuffer();
            for (String s : array) {
                buffer.append(s + "，");
            }
            LogUtil.e("原来：array:" + buffer.toString());

            Random random = new Random();
            for (int i = 0; i < array.length; i++) {
                // 随机产生一个 0 - n（不包含n）的随机数
                int p = random.nextInt(i + 1);

                String tmp = array[i];
                array[i] = array[p];
                array[p] = tmp;
            }

            StringBuffer buffer1 = new StringBuffer();
            for (String s : array) {
                buffer1.append(s + "，");
            }
            LogUtil.e("后来：array:" + buffer1.toString());

            return array;
        }
    }

    /**
     * @return 用来处理小数点的显示，如果是3.00则为3
     */
    public static String DoubleDecimal(String value) {
        if (TextUtils.isEmpty(value)) {
            return "0";
        } else {

            BigDecimal bigDecimal = new BigDecimal(value);
            BigDecimal decimal = bigDecimal.stripTrailingZeros();
            String plainString = decimal.toPlainString();

            // 为了数据的纯洁性，不会出现：“38.0”类似的数据
            return plainString;
        }
    }
}