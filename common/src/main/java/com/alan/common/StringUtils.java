package com.alan.common;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mouse on 2018/10/15.
 */
public class StringUtils {

    public static boolean isEnglishChar(String s) {
        if (TextUtils.isEmpty(s)) {
            return false;
        }

        if (s.length() != 1) {
            return false;
        }
        char c = s.charAt(0);
        return isEnglishChar(c);
    }

    /**
     * ASCII码
     * A-Z 65-90 91 [ 92 \ 93 ] 94^ 95_96` a-z 97-122
     * 33-47 标点符号 0-9 48-64
     *
     * @param s
     * @return
     */
    public static boolean isEnglishChar(char s) {
        if ((s >= 65 && s <= 90) || (s >= 97 && s <= 122)) {
            return true;
        }
        return false;
    }

    public static boolean isLetterOrChinese(String str) {
        String regex = "^[a-zA-Z\u4e00-\u9fa5]{2,10}";
        return str.matches(regex);
    }

    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]{4,40}";
        return str.matches(regex);
    }

    public static int stringToInt(String num) {
        try {
            return Integer.valueOf(num);
        } catch (Exception e) {
            return 0;
        }
    }

    public static float stringToFloat(String num) {
        try {
            return Float.valueOf(num);
        } catch (Exception e) {
            return 0;
        }
    }


    public static boolean equals(String a, String b) {
        if (TextUtils.isEmpty(a) && TextUtils.isEmpty(b)) {
            return true;
        } else if (TextUtils.isEmpty(a) && !TextUtils.isEmpty(b)) {
            return false;
        } else {
            return a.equals(b);
        }
    }

    public static boolean isTrimEmpty(String string) {
        return TextUtils.isEmpty(string) || TextUtils.isEmpty(string.trim());
    }

    public static boolean isChineseChar(char c) {
        return c > 0x4e00 && c < 0x9fff;
    }


    /**
     * 校验 字符串 长度
     *
     * @param string
     * @param min
     * @param max
     * @return
     */
    public static boolean checkStringLength(String string, int min, int max) {
        if (min == 0) {
            return TextUtils.isEmpty(string) || string.length() <= max;
        }
        return !TextUtils.isEmpty(string) && string.length() >= min && string.length() <= max;
    }

    /**
     * 是否是 有效的手机号
     *
     * @param phone
     * @return
     */
    public static boolean isAvalidPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        String reg = "^1\\d{10}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 是否包含 表情符号
     *
     * @param string
     * @return
     */
    public static boolean containEmoji(String string) {
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        Pattern emoji = Pattern.compile("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\ud83e\\udd00-\\ud83e\\udfff]|[\\u2600-\\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = emoji.matcher(string);
        return m.find();
    }

    /**
     * 是否为有效的邮箱
     *
     * @param email
     * @return
     */
    public static boolean isAvalidEmail(String email) {
        String str = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 是否有效的用户名
     * 英文字母
     * 数字
     * _
     *
     * @return
     */
    public static boolean isAlvalidUserName(String name) {
        String str = "[a-zA-Z0-9_]{1,}";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static int getInt(String s, int defaultValue) {
        try {
            return Integer.valueOf(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String s, boolean defaultValue) {
        try {
            return Boolean.valueOf(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long getLong(String s, long defaultValue) {
        try {
            return Long.valueOf(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
