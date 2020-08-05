package com.xjx.helper.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @作者 徐腾飞
 * @创建时间 2019/3/3  12:34
 * @更新者 XJX
 * @更新时间 2019/3/3  12:34
 * @描述 校验工具
 */
public class VerifyUtil {

    public static boolean verify(Context context, LinkedHashMap<TextView, Object> map) {
        boolean isHasEmpty = false;
        // 根据键值对对象去遍历
        for (Map.Entry<TextView, Object> m : map.entrySet()) {
            // 获取对象的数据
            String trim = m.getKey().getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                // 获取封装的提示消息
                String value = (String) m.getValue();
                ToastUtil.showToast(value);
                isHasEmpty = false;
                break;
            }
        }
        return isHasEmpty;
    }

    /**
     * @param textView TextView 的对象
     * @param message  给出的提示信息
     * @return 如果为空则返回 true，如果不为空就返回 false
     */
    public static boolean verify( TextView textView, String message) {
        boolean isHasEmpty = false;
        if (TextUtils.isEmpty(textView.getText().toString().trim())) {
            if (message != null) {
                ToastUtil.showToast(message);
                isHasEmpty = true;
            }
        } else if (TextUtils.equals(textView.getText().toString().trim(), "-1")) {
            if (message != null) {
                ToastUtil.showToast(message);
                isHasEmpty = true;
            }
        }
        return isHasEmpty;
    }

    /**
     * @param regex 验证规则
     * @param value 要验证的字符串
     * @return 返回一个字符串，如果符合规则就返回true，否则就返回flase
     */
    public static boolean Regex(String regex, String value) {
        boolean isRegex = false;
        // 编译正则表达式
//        Pattern pattern = Pattern.compile(regex);
        // 忽略大小写的写法
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        // 字符串是否与正则表达式相匹配
        isRegex = matcher.matches();

        return isRegex;
    }

    /**
     * 限制EditText小数点的输入的位数，前提是限制 输入类型为 android:inputType="numberDecimal"
     */
    public static class DecimalDigitsInputFilter implements InputFilter {

        private final int decimalDigits;

        /**
         * Constructor.
         *
         * @param decimalDigits maximum decimal digits
         */
        public DecimalDigitsInputFilter(int decimalDigits) {
            this.decimalDigits = decimalDigits;
        }

        @Override
        public CharSequence filter(CharSequence source,
                                   int start,
                                   int end,
                                   Spanned dest,
                                   int dstart,
                                   int dend) {
            int dotPos = -1;
            int len = dest.length();
            for (int i = 0; i < len; i++) {
                char c = dest.charAt(i);
                if (c == '.' || c == ',') {
                    dotPos = i;
                    break;
                }
            }
            if (dotPos >= 0) {

                // protects against many dots
                if (source.equals(".") || source.equals(",")) {
                    return "";
                }
                // if the DividerGridItemDecoration is entered before the dot
                if (dend <= dotPos) {
                    return null;
                }
                if (len - dotPos > decimalDigits) {
                    return "";
                }
            }
            return null;
        }
    }

}
